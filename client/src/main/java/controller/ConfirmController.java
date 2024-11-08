package controller;

import models.Game;
import models.ObjectWrapper;
import utils.StreamData;
import views.ConfirmationForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConfirmController {
    private ConfirmationForm confirmationForm;
    public ConfirmController(ConfirmationForm confirmationForm) {
        this.confirmationForm = confirmationForm;
        confirmationForm.addActionListener(new ConfirmationListener());
        confirmationForm.showConfirmationMessage("Đã tìm thấy đối thủ. Bạn có muốn bắt đầu trò chơi không?");
    }


    public void handleStartGame(Game game){
        ClientController.getListGame().add(game);
        ClientController.closeFrame(ClientController.FrameName.CONFIRM);
        ClientController.openFrame(ClientController.FrameName.GAME);
    }

   

    class ConfirmationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == confirmationForm.getBtnConfirm()) {
                confirmationForm.getBtnConfirm().setEnabled(false);
                sendReadyToPlay();
            }
        }

        private void sendReadyToPlay() {
            System.out.println("Gui thong bao Ready ne");
            ObjectWrapper readyWrapper = new ObjectWrapper(StreamData.Message.PLAYER_READY.name(), null);
            try {
                ClientController.getSocketHandler()
                        .getSendMessages()
                        .send(StreamData.Message.PLAYER_READY, null);
                System.out.println("Đã gửi thông báo player ready");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
