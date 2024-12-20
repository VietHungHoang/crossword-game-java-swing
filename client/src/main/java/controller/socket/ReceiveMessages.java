package controller.socket;

import java.io.ObjectInputStream;
import java.util.List;

import controller.*;
import models.*;
import utils.StreamData;
import views.*;

public class ReceiveMessages extends Thread {
    private ObjectInputStream ois;
    private LoginController loginController;
    private SignUpController signUpController;
    private WaitingForGameController waitingForGameController;
    private RankingController rankingController;
    private GameController gameController;
    private ListPlayerController listPlayerController;
    private HomeController homeController;
    private MatchHistoryController matchHistoryController;

    private EndGameController endGameController;

    // private EndGameController endGameController;

    private InviteRoomController inviteRoomController;

    private ConfirmController confirmController;
    public ReceiveMessages(ObjectInputStream ois) {
        this.ois = ois;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        while (true) {
            try {
                ObjectWrapper objectWrapper = new ObjectWrapper();
                objectWrapper = (ObjectWrapper) ois.readObject();
                String received = objectWrapper.getIdentifier();
                StreamData.Message message = StreamData.getMessageFromData(received);
                System.out.println("Khong co si dsfsd" + message);
                switch (message) {
                    case LOGIN:
                        this.loginController = new LoginController(new LoginForm());
                        this.loginController.loginHandler(objectWrapper);
                        break;
                    case SIGNUP:
                        this.signUpController = new SignUpController();
                        this.signUpController.signUpHandler(objectWrapper.getStatus());
                        break;
                    case LOGOUT:
                        this.loginController.logOut();
                        break;
                    case CREATE_ROOM:
                        // TODO : Khoi tao phong custom de co the moi nguoi choi
                        break;
                    case WAITING_FOR_GAME:
                            this.waitingForGameController = new WaitingForGameController(new WaitingForGameForm());
                            this.waitingForGameController.waitingForGameHandler(objectWrapper);
                        break;
                    case START_GAME:
                        this.confirmController = new ConfirmController(new ConfirmationForm());
                        this.confirmController.handleStartGame((Game) objectWrapper.getObject());
                        break;
                    case START_GAME_FRIEND:
                        if(this.inviteRoomController != null){
                            System.out.println("Nhan list friend tu server: " + objectWrapper.getObject());
                            this.inviteRoomController.handleStartGameWithFriend((Game) objectWrapper.getObject());
                        }
                        break;
                    case RANKING:
                        this.rankingController = new RankingController(new RankingForm());
                        this.rankingController.rankingHandler(objectWrapper.getObject());
                        break;
                    case LIST_PLAYER:
                        this.listPlayerController = new ListPlayerController(new ListPlayerForm());
                        ClientController.players = (List<PlayerStatus>) objectWrapper.getObject();
                        this.listPlayerController.updatePlayerList(ClientController.players);
                        break;
                    case ACCEPT_INVITE_FRIEND:
                        if (this.listPlayerController == null) {
                            this.listPlayerController = new ListPlayerController();
                        }
                        this.listPlayerController.acceptFriend(objectWrapper);
                        break;
                    case SEND_INVITE_FRIEND:
                        if (this.listPlayerController == null) {
                            this.listPlayerController = new ListPlayerController();
                        }
                        this.listPlayerController.receiveInviteFriend(objectWrapper);
                        break;
                    case UPDATE_LIST_PLAYER:
                        ClientController.players = (List<PlayerStatus>) objectWrapper.getObject();
                        if (this.listPlayerController != null
                                && this.listPlayerController.getListPlayerForm() != null) {
                            this.listPlayerController.updatePlayerList(ClientController.players);
                        }
                        break;
                    case WIN_GAME:
                         this.gameController = new GameController(new GameForm());
                        this.gameController.handleEndGame("Win");
                        break;
                    case LOST_GAME:
                        this.gameController = new GameController(new GameForm());
                        this.gameController.handleEndGame("Lost");
                        break;
                        case INVITE_ROOM:
                        // Khi nhan duoc loi moi phong tu server khong can goi toi OPENFRAME vi da setVisible(true) trong InviteRoomController
                            System.out.println("Khoi tao Invite Room tu Room get tu tren server: " + objectWrapper.getObject());
                            this.inviteRoomController = new InviteRoomController(new InviteRoomForm((Room)objectWrapper.getObject()));
                            break;
                        case GET_LIST_FRIEND:
                            if(this.inviteRoomController != null){
                                System.out.println("Nhan list friend tu server: " + objectWrapper.getObject());
                                this.inviteRoomController.getListFriendHandler((List<PlayerFriend>)objectWrapper.getObject());
                            }
                            else{
                              return;
                            }
                            break;
                        case UPDATE_LIST_FRIEND:
                            System.out.println("Nhan update list friend tu server: " + objectWrapper.getObject());
                            if(this.inviteRoomController != null){
                                this.inviteRoomController.getListFriendHandler((List<PlayerFriend>)objectWrapper.getObject());
                            }
                            break;
                        case RECEIVE_INVITE_ROOM:
                          Room invitedRoom = (Room)objectWrapper.getObject();
                          System.out.println("Nhan invite room tu server: " + invitedRoom.getId());
                          int option = javax.swing.JOptionPane.showConfirmDialog(
                            null,
                            "Bạn nhận được lời mời từ " + invitedRoom.getPlayers().get(0).getPlayerName() + " để vào chơi game vui vẻ",
                            "Lời mời vào phòng",
                            javax.swing.JOptionPane.YES_NO_OPTION
                          );
                        
                          if (option == javax.swing.JOptionPane.YES_OPTION) {
                            // Gửi phản hồi đồng ý tham gia phòng về server
                            ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.ACCEPT_INVITE_ROOM, invitedRoom.getId());
                          } else {
                            // Gửi phản hồi từ chối tham gia phòng về server
                              
                          }
                          break;
                        case ACCEPT_INVITE_ROOM: 
                          System.out.println("Nhan accept invite room tu server: " + objectWrapper.getObject());
                          ClientController.closeFrame(ClientController.FrameName.HOME);
                          // Luôn tạo mới controller và form khi accept invite
                          this.inviteRoomController = new InviteRoomController(new InviteRoomForm((Room)objectWrapper.getObject()));
                          break;
                        case UPDATE_INVITE_ROOM:
                            this.inviteRoomController.updateInviteRoomHandler((Room)objectWrapper.getObject());
                            break;
                    case DRAW_GAME:
                        this.gameController = new GameController();
                        this.gameController.handleDrawGame();
                          break;
                    case LEAVE_INVITE_ROOM:
                        System.out.println("Nhan leave room tu server: " + objectWrapper.getObject());
                        if (this.inviteRoomController != null ) {
                            this.inviteRoomController.leaveInviteRoomHandler();
                        }
                        else {
                          System.out.println();
                        }
                        break;
                    case MATCH_HISTORY:
                        ClientController.getMatchHistoryController().updateMatchHistory((List<MatchHistory>) objectWrapper.getObject());
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public RankingController getRankingController() {
        return rankingController;
    }

    public ListPlayerController getListPlayerController() {
        return listPlayerController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
