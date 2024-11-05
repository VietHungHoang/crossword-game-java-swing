package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import models.ObjectWrapper;
import utils.StreamData;
import views.ConfirmationForm;
import views.WaitingForGameForm;

public class WaitingForGameController {
    private WaitingForGameForm waitingForGameForm;
    private ConfirmationForm confirmationForm;
    private Timer waitingTimer;
    private long elapsedTime;

    // Khởi tạo bộ điều khiển với form chờ
    public WaitingForGameController(WaitingForGameForm waitingForGameForm) {
        this.waitingForGameForm = waitingForGameForm;
        this.waitingForGameForm.addActionListener(new WaitingForGameListener());
        startWaitingTimer();
    }

    // Bắt đầu bộ đếm thời gian chờ
    private void startWaitingTimer() {
        waitingTimer = new Timer();
        elapsedTime = 0;
        waitingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime++;
                long minutes = elapsedTime / 60;
                long seconds = elapsedTime % 60;
                String time = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
                waitingForGameForm.updateWaitingTime(time);
            }
        }, 0, 1000);
    }

    // Dừng bộ đếm thời gian chờ
    private void stopWaitingTimer() {
        if (waitingTimer != null) {
            waitingTimer.cancel();
        }
    }

    // Xử lý thông báo từ server khi tìm thấy đối thủ
    public void waitingForGameHandler(ObjectWrapper objectWrapper) {
        String msg = objectWrapper.getStatus();
        if (msg.equals("find-game-success")) {
            System.out.println("Đã tìm thấy đối thủ thành công");
            stopWaitingTimer();
//            closeWaitingForm();
            ClientController.closeFrame(ClientController.FrameName.WAITING_FOR_GAME);
            ClientController.openFrame(ClientController.FrameName.CONFIRM);
            // Hiển thị màn hình xác nhận
//            System.out.println("Mo form xac nhan tim tran");
//            confirmationForm = new ConfirmationForm();
//            confirmationForm.addActionListener(new ConfirmationListener());
//            confirmationForm.showConfirmationMessage("Đã tìm thấy đối thủ. Bạn có muốn bắt đầu trò chơi không?");
//            System.out.println("Da mo form tim tran");
        }
    }

    // Phương thức để đóng form chờ
    private void closeWaitingForm() {
        if (waitingForGameForm != null) { 
            ClientController.closeFrame(ClientController.FrameName.WAITING_FOR_GAME);
            waitingForGameForm.dispose();
            System.out.println("Đã đóng form dem thoi gian  chờ");
        }
    }

    // Lớp xử lý sự kiện cho form chờ
    class WaitingForGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == waitingForGameForm.getBtnCancel()) {
                stopWaitingTimer();
                cancelWaiting();
            }
        }

        private void cancelWaiting() {
            try {
                ClientController.getSocketHandler()
                        .getSendMessages()
                        .send(StreamData.Message.CANCEL_WAITING, null);
                System.out.println("Đã gửi thông báo hủy tìm trận");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            closeWaitingForm();
            ClientController.openFrame(ClientController.FrameName.HOME);
        }
    }

    // Lớp xử lý sự kiện cho form xác nhận
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
    public void closeConfirmationForm(){
        if (confirmationForm != null) {
            confirmationForm.dispose();
        }
    }

    public static void main(String[] args) {
        new ConfirmationForm();
    }
}
