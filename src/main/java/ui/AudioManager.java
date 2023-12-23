package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;

/**
 * The audio manager: manage music and sound effect.
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
     * Set audio(only once) when user press some buttons.
     *
     * @param i the choose of sound effect during using button.
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
     * Sound effect of stars cooperating animations in main frame.
     */
    public static void starAudio(){
        oneShot("/audio/star.wav", 0.5);
    }

    /**
     * Hit audio: a special sound effect of buttons.
     */
    public static void hitAudio(){
        oneShot("/audio/hit.wav");
    }

    /**
     * sound audio of win or lose.
     *
     * @param e the choice of win or lose.
     */
    public static void winAudio(endOfGame e){
        switch (e){
            case WIN: oneShot("/audio/win.wav"); break;
            case LOSE: oneShot("/audio/gameover.wav"); break;
        }
    }

    private static MediaPlayer mainPlayer;

    /**
     * Set background music.
     *
     * @param i choose music.
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
     * Mute background music.
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
 * The enum end of game: reflect win or lose.
 */
enum endOfGame{
    /**
     * Win end of game: 0 in fact.
     */
    WIN,
    /**
     * Lose end of game: 1 in fact.
     */
    LOSE;
}
