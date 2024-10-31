package controller.socket;

import java.io.ObjectInputStream;

import controller.*;
import models.ObjectWrapper;
import models.Room;
import utils.StreamData;
import views.GameForm;
import views.LoginForm;
import views.RankingForm;
import views.WaitingForGameForm;
public class ReceiveMessages extends Thread{
    private ObjectInputStream ois;
    private LoginController loginController;
    private SignUpController signUpController;
    private WaitingForGameController waitingForGameController;
    private RankingController rankingController;
    private GameController gameController;
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
                System.out.println(message);   
                switch (message) {
                    case LOGIN:
                        this.loginController = new LoginController(new LoginForm());
                        this.loginController.loginHandler(objectWrapper);
                        break;
                    case SIGNUP:
                        this.signUpController= new SignUpController();
                        this.signUpController.signUpHandler(objectWrapper.getStatus());
                        break;
                    case LOGOUT:
                        this.loginController.logOut();
                        break;
                    case CREATE_ROOM:
                    //TODO : Khoi tao phong custom de co the moi nguoi choi
                        break;
                    case WAITING_FOR_GAME:
                        // Đảm bảo rằng WaitingForGameController đã được khởi tạo trước đó và chỉ gọi phương thức xử lý.
                        if (this.waitingForGameController != null) {
  
                            this.waitingForGameController.waitingForGameHandler(objectWrapper);
                        } else {
                            // Trong trường hợp chưa có controller, khởi tạo mới.
     
                            this.waitingForGameController = new WaitingForGameController(new WaitingForGameForm());
                            this.waitingForGameController.waitingForGameHandler(objectWrapper);
                        }
                        break;
                    case START_GAME:
                        System.out.println("Mo game ne");
                        Room room = (Room)objectWrapper.getObject();
                        this.waitingForGameController.closeConfirmationForm();
                        //TODO: Chỗ này fix lại là phải lấy lại là khởi tạo GAME phải từ Object Game trả về nên nếu trả về ROOM thì ko đúng
                        //TODO: Tức là chỗ này phải gọi lên server để lấy lại Object Game
                        this.gameController = new GameController(new GameForm(room.getPlayers().get(0).getPlayerName(), room.getPlayers().get(1).getPlayerName(), "KEYWORD", 0, 0));
                        // this.gameController.startGameHandler(objectWrapper);
                        break;
                    case RANKING:
                        this.rankingController = new RankingController(new RankingForm());
                        this.rankingController.rankingHandler(objectWrapper.getObject());
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

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
