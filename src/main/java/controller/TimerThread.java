package controller;

import ui.UIFunction;
import ui.UIManager;

/**
 * The timer thread.
 */
public class TimerThread implements Runnable{

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                if (!UIFunction.checkWin()){
                    UIManager.puzzleController.timer++;
                    int intt = Math.toIntExact(UIManager.puzzleController.timer);
                    String timerText = String.format("%02d:%02d", intt/60, intt%60);
                    UIManager.puzzleController.timerText.setText(timerText);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}