package ui;

import controller.ErrorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The error manager.
 */
public class ErrorManager {

    /**
     * Send error.
     *
     * @param error the error
     * @throws Exception the exception
     */
    public static void sendError(String error) throws Exception {
        new ErrorManager().start(new Stage(), error);
    }

    /**
     * The constant content.
     */
    public static String content;

    /**
     * Start.
     *
     * @param stage the stage
     * @param ct    the ct
     * @throws Exception the exception
     */
    public void start(Stage stage, String ct) throws Exception {
        content = ct;
        FXMLLoader loader = new FXMLLoader();
        Scene errorScene = new Scene(loader.load(getClass().getResource("/fxmlFile/errorFrame.fxml")));
        stage.setScene(errorScene);
        stage.setHeight(200);
        stage.setWidth(300);
        stage.setTitle("发生异常");
        stage.setResizable(false);
        Image icon = new Image("/icon.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
