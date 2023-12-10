package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioManager {

    private static void oneShot(String musicFile){
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.setCycleCount(1);
        player.play();
    }

    public static void setButtonAudio(int i){
        switch (i){
            case 1: oneShot("src/audio/button1.wav"); break;
            case 2: oneShot("src/audio/button2.mp3"); break;
            case 3: oneShot("src/audio/button3.wav"); break;
            case 4: oneShot("src/audio/button4.mp3"); break;
        }
    }

    public static void winAudio(endOfGame e){
        switch (e){
            case WIN: oneShot("src/audio/win.wav"); break;
            case LOSE: oneShot("src/audio/gameover.wav"); break;
        }
    }

    private static MediaPlayer mainPlayer;

    public static void setBGMusic(int i){
        if (mainPlayer != null) {
            mainPlayer.setVolume(0.8);
            UIAnimation.timer(500, event -> {
                mainPlayer.setVolume(0.4);
                UIAnimation.timer(500, event1 -> mainPlayer.stop());
            });
        }
        UIAnimation.timer(1500, event -> {
            String musicPath = "src/audio/bg1.mp3";
            if (i == 2) musicPath = "src/audio/bg2.mp3";
            Media music = new Media(new File(musicPath).toURI().toString());
            mainPlayer = new MediaPlayer(music);
            mainPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mainPlayer.setVolume(1.0);
            mainPlayer.play();
        });

    }

}

enum endOfGame{
    WIN,
    LOSE;
}
