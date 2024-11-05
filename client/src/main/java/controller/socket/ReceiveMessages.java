package controller.socket;

import java.io.ObjectInputStream;
import java.util.List;

import controller.*;
import models.Game;
import models.ObjectWrapper;
import models.PlayerStatus;
import models.Room;
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

    private EndGameController endGameController;

    // private EndGameController endGameController;

    private InviteRoomController inviteRoomController;

    private ConfirmController confirmController;
    public ReceiveMessages(ObjectInputStream ois) {
        this.ois = ois;
    }

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
//                        System.out.println("Mo game ne");
//                        Game game = (Game) objectWrapper.getObject();
////                        this.waitingForGameControllper.closeConfirmationForm();
////                        // TODO: Chỗ này fix lại là phải lấy lại là khởi tạo GAME hải từ Object Game
//                        // trả về nên nếu trả về ROOM thì ko đúng
//                        // TODO: Tức là chỗ này phải gọi lên server để lấy lại Object Game
//                        this.gameController = new GameController(new GameForm(game.player1.getPlayerName(),
//                                game.player2.getPlayerName(), "KEYWORD", 0, 0));
                        // this.gameController.startGameHandler(objectWrapper);
                        this.confirmController = new ConfirmController(new ConfirmationForm());
                        this.confirmController.handleStartGame((Game) objectWrapper.getObject());
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

                        System.out.println("Nhan invite room tu server: " + objectWrapper.getObject());
                        this.inviteRoomController = new InviteRoomController(new InviteRoomForm((Room)objectWrapper.getObject()));
                        this.inviteRoomController.inviteRoomHandler(objectWrapper);
                        break;
                    case GET_LIST_FRIEND:
                        if(this.inviteRoomController != null){
                            System.out.println("Nhan list friend tu server: " + objectWrapper.getObject());
                            this.inviteRoomController.getListFriendHandler(objectWrapper);
                        }
                        else{
                          return;
                        }
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
