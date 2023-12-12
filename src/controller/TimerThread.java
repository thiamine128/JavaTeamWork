package controller;

import ui.UIManager;

public class TimerThread implements Runnable{

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                UIManager.puzzleController.timer++;
                int intt = Math.toIntExact(UIManager.puzzleController.timer);
                String timerText = String.format("%02d:%02d", intt/60, intt%60);
                UIManager.puzzleController.timerText.setText(timerText);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
