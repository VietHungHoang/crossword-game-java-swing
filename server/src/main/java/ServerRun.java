import controller.ServerController;
import views.ServerView;


public class ServerRun {
    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        ServerController serverController = new ServerController(serverView);
        
    }
}
