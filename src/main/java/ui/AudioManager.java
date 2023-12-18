package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;

/**
 * The type Audio manager.
 */
public class AudioManager {

    private static void oneShot(String musicFile) {
        Media sound = new Media(AudioManager.class.getResource(musicFile).toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.setCycleCount(1);
        player.play();
    }

    private static void oneShot(String musicFile, double volume){
        Media sound = new Media(AudioManager.class.getResource(musicFile).toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.setCycleCount(1);
        player.setVolume(volume);
        player.play();
    }

    /**
     * Set button audio.
     *
     * @param i the
     */
    public static void setButtonAudio(int i){
        switch (i){
            case 1: oneShot("/audio/button1.wav"); break;
            case 2: oneShot("/audio/button2.mp3"); break;
            case 3: oneShot("/audio/button3.wav"); break;
            case 4: oneShot("/audio/button4.mp3"); break;
        }
    }

    /**
     * Star audio.
     */
    public static void starAudio(){
        oneShot("/audio/star.wav", 0.5);
    }

    /**
     * Hit audio.
     */
    public static void hitAudio(){
        oneShot("/audio/hit.wav");
    }

    /**
     * Win audio.
     *
     * @param e the e
     */
    public static void winAudio(endOfGame e){
        switch (e){
            case WIN: oneShot("/audio/win.wav"); break;
            case LOSE: oneShot("/audio/gameover.wav"); break;
        }
    }

    private static MediaPlayer mainPlayer;

    /**
     * Set bg music.
     *
     * @param i the
     */
    public static void setBGMusic(int i){
        if (mainPlayer != null) {
            mainPlayer.setVolume(0.8);
            UIAnimation.timer(200, event -> {
                mainPlayer.setVolume(0.6);
                UIAnimation.timer(200, event1 -> {
                    mainPlayer.setVolume(0.4);
                    UIAnimation.timer(200, event2->{
                       mainPlayer.setVolume(0.2);
                       UIAnimation.timer(200, event3 -> mainPlayer.stop());
                    });
                });
            });
        }
        UIAnimation.timer(1500, event -> {
            String musicPath = "/audio/bg1.mp3";
            if (i == 2) musicPath = "/audio/bg2.mp3";
            Media music = new Media(AudioManager.class.getResource(musicPath).toExternalForm());
            mainPlayer = new MediaPlayer(music);
            mainPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mainPlayer.setVolume(1.0);
            mainPlayer.play();
        });
    }

    /**
     * Cancel music.
     */
    public static void cancelMusic(){
        if (mainPlayer != null) {
            mainPlayer.setVolume(0.8);
            UIAnimation.timer(200, event -> {
                mainPlayer.setVolume(0.6);
                UIAnimation.timer(200, event1 -> {
                    mainPlayer.setVolume(0.4);
                    UIAnimation.timer(200, event2->{
                        mainPlayer.setVolume(0.2);
                        UIAnimation.timer(200, event3 -> {
                            mainPlayer.stop();
                            mainPlayer = null;
                        });
                    });
                });
            });
        }
    }

}

/**
 * The enum End of game.
 */
enum endOfGame{
    /**
     * Win end of game.
     */
    WIN,
    /**
     * Lose end of game.
     */
    LOSE;
}
