package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import controller.socket.HomeController;
import controller.socket.SocketHandlers;
import models.Game;
import models.Player;
import models.PlayerStatus;
import models.Room;
import views.*;
import views.EndGameForm;

public class ClientController {

    public static SocketHandlers socketHandlers;
    private static String severAddress = "localhost";
    private static int port = 12345;
    private static LoginForm loginForm;
    private static SignUpForm signUpForm;
    private static HomeForm homeForm;
    private static InviteRoomForm inviteRoomForm;
    private static WaitingForGameForm waitingForGameForm;
    private static GameForm gameForm;
    private static LoginController loginController;
    private static HomeController homeController;
    private static InviteRoomController inviteRoomController;
    private static SignUpController signUpController;
    private static WaitingForGameController waitingForGameController;
    private static GameController gameController;
    private static RankingController rankingController;
    private static RankingForm rankingForm;
    private static Room room;
    public static List<PlayerStatus> players = new ArrayList<>();



    
    public static void setRoom(Room room) {
        ClientController.room = room;
    }

    public static ListPlayerForm getListPlayerForm() {
        return listPlayerForm;
    }

    private  static ListPlayerForm listPlayerForm;
    private static ListPlayerController listPlayerController;
    public static RankingController getRankingController() {
        return rankingController;
    }

    private static EndGameForm endGameForm;

    private static EndGameController endGameController;

    private static ConfirmationForm confirmationForm;

    private static Player currentPlayer;

    public static ArrayList<Game> getListGame() {
        return listGame;
    }

    private static ConfirmController confirmController;

    private static ArrayList<Game> listGame = new ArrayList<>();

    private  static MatchHistoryForm matchHistoryForm;
    private  static MatchHistoryController matchHistoryController;
    public enum FrameName{
        LOGIN,
        SIGNUP,
        HOME,
        INVITE_ROOM,
        WAITING_FOR_GAME,
        GAME,
        RANKING,
        LIST_PLAYER,
        WIN_GAME,
        LOST_GAME,
        CONFIRM,
        MATCH_HISTORY
    }

    public ClientController(){
        socketHandlers = new SocketHandlers();
        if(socketHandlers.connect(severAddress, port)){
            System.out.println("Connect server successfully!");
            openFrame(FrameName.LOGIN);

        }
        else {
            System.out.println("Connect server error!");
            JOptionPane.showMessageDialog(null, "Connect server error!");
        }
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        ClientController.currentPlayer = currentPlayer;
    }

    public static void openFrame(FrameName frameName){
        switch (frameName) {
            case LOGIN:
                loginForm = new LoginForm();
                loginController = new LoginController(loginForm);
                loginForm.setVisible(true);
                break;
            case HOME:
                homeForm = new HomeForm();
                homeController = new HomeController(homeForm);
                homeForm.setVisible(true);
                break;
            case INVITE_ROOM:
                inviteRoomForm = new InviteRoomForm(room);
                inviteRoomController = new InviteRoomController(inviteRoomForm);
                inviteRoomForm.setVisible(true);
                break;
            case SIGNUP:
                signUpForm = new SignUpForm();
                signUpController = new SignUpController(signUpForm);
                signUpForm.setVisible(true);
                break;
            case WAITING_FOR_GAME:
                waitingForGameForm = new WaitingForGameForm();
                waitingForGameController = new WaitingForGameController(waitingForGameForm);
                waitingForGameForm.setVisible(true);
                break;
            case GAME:
                for(Game x : listGame){
                    Player currentPlayer = getCurrentPlayer();
                    Player remainingPlayer = null;
                    if(Objects.equals(x.getPlayer1().getId(), currentPlayer.getId()))
                        remainingPlayer = x.getPlayer2();
                    else if(Objects.equals(x.getPlayer2().getId(), currentPlayer.getId()))
                        remainingPlayer = x.getPlayer1();
                    if(remainingPlayer != null)
                        System.out.println("Da tim thay nguoi choi de tao game");
                    gameForm = new GameForm(
                            currentPlayer.getPlayerName(),
                            remainingPlayer.getPlayerName(),
                            x.getKeyword().getValue(),
                            currentPlayer.getTotalPoint(),
                            remainingPlayer.getTotalPoint()
                            );
                    gameController = new GameController(gameForm);
                    gameForm.setVisible(true);
                    break;
                    }

                break;
            case RANKING:
                rankingForm = new RankingForm();
                rankingController = new RankingController(rankingForm);
                rankingForm.setVisible(true);
                break;
            case LIST_PLAYER:
                listPlayerForm= new ListPlayerForm();
                listPlayerController = new ListPlayerController(listPlayerForm);
                listPlayerForm.setVisible(true);
                break;
            case WIN_GAME:
                endGameForm = new EndGameForm("Chiến thắng", null);
                endGameController = new EndGameController(endGameForm);
                endGameForm.setVisible(true);
                break;
            case LOST_GAME:
                endGameForm = new EndGameForm("Thua cuộc", null);
                endGameController = new EndGameController(endGameForm);
                endGameForm.setVisible(true);
                break;
            case CONFIRM:
                confirmationForm = new ConfirmationForm();
                confirmController = new ConfirmController(confirmationForm);
                confirmationForm.setVisible(true);
                break;
            case MATCH_HISTORY:
                matchHistoryForm = new MatchHistoryForm();
                matchHistoryController = new MatchHistoryController(matchHistoryForm);
                matchHistoryForm.setVisible(true);
                break;
            default:
                break;
        }
    }

    public static void closeFrame(FrameName frameName) {
        switch (frameName) {
            case LOGIN:
                loginForm.dispose();
                break;

            case HOME:
                homeForm.dispose();
                System.out.println(gameForm);
                break;

            case INVITE_ROOM:
                inviteRoomForm.dispose();
                break;

            case SIGNUP:
                signUpForm.dispose();
                break;

            case WAITING_FOR_GAME:
                System.out.println("dispose waitingForGameForm");
                waitingForGameForm.dispose();
                System.out.println(waitingForGameForm);
                break;

            case GAME:
                gameForm.dispose();
                break;

            case RANKING:
                if(socketHandlers.getReceiveMessages().getRankingController()!=null && socketHandlers.getReceiveMessages().getRankingController().getRankingForm()!=null) {
                    socketHandlers.getReceiveMessages().getRankingController().getRankingForm().dispose();
                }
                else if(rankingForm!=null){
                    rankingForm.dispose();
                }
                break;
            case LIST_PLAYER:
                if(socketHandlers.getReceiveMessages().getListPlayerController()!=null && socketHandlers.getReceiveMessages().getListPlayerController().getListPlayerForm()!=null) {
                    socketHandlers.getReceiveMessages().getListPlayerController().getListPlayerForm().dispose();
                }
                else if(listPlayerForm!=null) {
                    listPlayerForm.dispose();
                }
                break;
            case WIN_GAME:
                endGameForm.dispose();
                break;
            case LOST_GAME:
                endGameForm.dispose();
                break;
            case CONFIRM:
                if(confirmationForm != null)
                    confirmationForm.dispose();
                break;
            case MATCH_HISTORY:
                if(matchHistoryForm!=null)
                    matchHistoryForm.dispose();
                break;
            default:
                break;
        }
    }

    public static HomeForm getHomeForm() {
        return homeForm;
    }

    public static RankingForm getRankingForm() {
        return rankingForm;
    }

    public static MatchHistoryForm getMatchHistoryForm() {
        return matchHistoryForm;
    }

    public static SocketHandlers getSocketHandler() {
        return socketHandlers;
    }

    public static MatchHistoryController getMatchHistoryController() {
        return matchHistoryController;
    }
}
