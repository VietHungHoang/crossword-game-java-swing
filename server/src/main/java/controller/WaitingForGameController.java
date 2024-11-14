package controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.KeywordDAO;
import dao.PlayerDAO;
import dao.UserDAO;
import dao.impl.IKeywordDAO;
import models.*;
import utils.RandomString;
import utils.StatusPlayer;
import utils.StreamData;
import views.ServerView;

public class WaitingForGameController {
    private ServerView view;
    private Player playerLogin;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;
    private Thread waitingThread;
    private volatile boolean waiting;
    private IKeywordDAO keywordDAO;

    public WaitingForGameController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.keywordDAO = new KeywordDAO(conn);
        this.socketHandlers = socketHandlers;
    }

    /**
     * Xử lý quá trình tìm phòng cho người chơi.
     * -Khi người chơi chọn đấu rank:
     * - Kiểm tra xem hệ thống có phòng rank nào còn trống không.
     * - Nếu có thì join vào
     * - Không có thì tạo phòng mới
     */
    public void handleFindingRoom() {
        System.out.println(ServerController.rooms.size());
        if (ServerController.rooms.isEmpty()){
            createWaitingRoom();
            return;
        }
            
        else {
            for (Room room : ServerController.rooms) {
                if (room.getPlayers().size() == 1 && room.isRanking()) {
                    joinWaitingRoom(room.getId());
                    return;
                }
            }
        }
        createWaitingRoom();
    }

    /**
     * Tạo một phòng chờ mới và thêm người chơi hiện tại vào phòng đó.
     */
    public void createWaitingRoom() {
        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.DIGITS);
        String randomId = randomString.nextString();
        List<Player> playersInRoom = new ArrayList<>();
        playersInRoom.add(socketHandlers.getLoginController().getPlayerLogin());
        Room room = new Room(
            randomId,
            new Date(),
            socketHandlers
                .getLoginController()
                .getPlayerLogin(),
            playersInRoom,
            "1/2",
            true);
        System.out.println("da tao phong" + room.toString());
        socketHandlers.getLoginController().getPlayerLogin().setStatus(StatusPlayer.FINDING_GAME.value);
        socketHandlers.getListPlayerController().updateListPlayer(); // Cập nhật trạng thái của người chơi với tất cả
                                                                     // người chơi kaác
        socketHandlers.getInviteRoomController().updateListFriend(); // Cập nhật trạng thái người chơi với bạn bè.
        // TODO: UPDATE STATUS FOR LISt FRIEND
        System.out.println("Người dùng " + socketHandlers.getLoginController().getPlayerLogin().getPlayerName()
                + " đang tìm trận");
        ServerController.rooms.add(room);
    }

    /**
     * Tham gia người chơi vào một phòng đã tồn tại.
     */
    public void joinWaitingRoom(String roomId) {
        waiting = false;
        Room room = ServerController.rooms.stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElse(null);
        System.out.println("Da join vao phong" + room.toString());
        if (room == null) {
            System.out.println("Phòng không tồn tại.");
            return;
        }

        Player newPlayer = socketHandlers.getLoginController().getPlayerLogin();
        room.getPlayers().add(newPlayer);
        newPlayer.setStatus(StatusPlayer.IN_ROOM.value);
        room.setStatus("2/2");
        System.out.println("Người chơi mới đã tham gia phòng: " + newPlayer.getPlayerName());
        monitorRoomStatus(roomId);
    }

    /**
     * Lắng nghe trạng thái của phòng và chỉ gửi thông báo khi cả hai người chơi sẵn
     * sàng.
     */
    private void monitorRoomStatus(String roomId) {
        // new Thread(() -> {
        Room room = ServerController.rooms.stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElse(null);

        if (room == null) {
            System.out.println("Phòng không tồn tại.");
            return;
        }

        boolean messageSent = false; // Cờ xác nhận gửi thông báo
        while (!messageSent) {
            if (room.getPlayers().size() == 2 && room.getStatus().equals("2/2")) {
                String message = StreamData.Message.WAITING_FOR_GAME.name() + ";find-game-success";
                ObjectWrapper objectWrapper = new ObjectWrapper(message, room);

                // Gửi thông báo cho tất cả người chơi trong phòng
                for (SocketHandlers socketHandler : ServerController.socketHandlers) {
                    if (socketHandler.getLoginController().getPlayerLogin().equals(room.getPlayers().get(0)) ||
                            socketHandler.getLoginController().getPlayerLogin().equals(room.getPlayers().get(1))) {
                        socketHandler.send(objectWrapper);
                        System.out.println("Đã gửi thông báo phòng đầy cho người chơi: "
                                + socketHandler.getLoginController().getPlayerLogin().getPlayerName());
                    }
                }
                messageSent = true; // Đánh dấu là đã gửi thông báo
            }

            // try {
            // Thread.sleep(1000);
            // } catch (InterruptedException e) {
            // Thread.currentThread().interrupt();
            // System.out.println("Luồng kiểm tra trạng thái phòng bị gián đoạn.");
            // break;
            // }
        }
        // }).start();
    }

    /**
     * Bắt đầu trò chơi khi cả hai người chơi đều sẵn sàng.
     */
    public void handlePlayerReady() {
        Player player = socketHandlers.getLoginController().getPlayerLogin();
        Room room = ServerController.rooms.stream()
                .filter(r -> r.getPlayers().contains(player))
                .findFirst()
                .orElse(null);

        if (room != null && room.getPlayers().size() == 2) {
            room.setPlayerReady(player);
            System.out.println("Người chơi " + player.getPlayerName() + " đã sẵn sàng.");
            if (room.areBothPlayersReady()) {
                System.out.println("Cả hai người chơi đều đã sẵn sàng, bắt đầu trò chơi.");
                room.setStatus("Đang chơi");
                System.out.println("Dang tao phong cho ca 2 nguoi choi trong phong");
                Game game = new Game(room);
                Random random = new Random();
                socketHandlers.getLoginController().getPlayerLogin().setStatus(StatusPlayer.IN_GAME.value);
                Long x = random.nextInt(this.keywordDAO.countAll()) * 1L;
                game.setKeyword( keywordDAO.findById(x));
                System.out.println("Da tao phong cho ca 2 nguoi choi trong phong");
                ServerController.games.add(game);
                System.out.println("Da them phong vao danh sach game");
                ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.START_GAME.name(), new Game(game));
                System.out.println("Game object Wrapper" + game.toString());
                List<SocketHandlers> socketHandlersList = ServerController.socketHandlers;
                for (SocketHandlers socketHandler : socketHandlersList) {
                    if (socketHandler.getLoginController().getPlayerLogin().equals(room.getPlayers().get(0))
                            || socketHandler.getLoginController().getPlayerLogin().equals(room.getPlayers().get(1))) {
                        socketHandler.getLoginController().getPlayerLogin().setStatus(StatusPlayer.IN_GAME.value);
                        socketHandler.send(objectWrapper);

                    }
                }
                socketHandlers.getListPlayerController().updateListPlayer();
                socketHandlers.getInviteRoomController().updateListFriend();
            } else {
                System.out.println("Chờ đối thủ sẵn sàng...");
            }
        } else {
            System.out.println("Không tìm thấy phòng cho người chơi: " + player.getPlayerName());
        }
    }

    /**
     * Xử lý khi người chơi hủy tìm trận.
     */
    public void handleCancelWaiting() {
        Player player = socketHandlers.getLoginController().getPlayerLogin();
        Room room = ServerController.rooms.stream()
                .filter(r -> r.getPlayers().contains(player))
                .findFirst()
                .orElse(null);

        if (room != null) {
            room.getPlayers().remove(player);
            System.out.println("Người chơi " + player.getPlayerName() + " đã hủy tìm trận và rời khỏi phòng.");
            socketHandlers.getLoginController().getPlayerLogin().setStatus(StatusPlayer.ONLINE.value);
            socketHandlers.getListPlayerController().updateListPlayer();
            socketHandlers.getInviteRoomController().updateListFriend();
            if (room.getPlayers().isEmpty()) {
                ServerController.rooms.remove(room);
                System.out.println("Phòng " + room.getId() + " đã bị xóa vì không còn người chơi nào.");
            } else {
                room.setStatus("1/2");
                ObjectWrapper updateWrapper = new ObjectWrapper(StreamData.Message.UPDATE_ROOM_STATUS.name(), room);
                for (Player p : room.getPlayers()) {
                    socketHandlers.sendToPlayer(p, updateWrapper);
                }
            }
        } else {
            System.out.println("Không tìm thấy phòng cho người chơi: " + player.getPlayerName());
        }
    }
}
