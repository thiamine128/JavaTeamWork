package ui;/*
 * @Description
 * @author
 * @date 2023/12/19 19:27
 */

import controller.ErrorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ErrorManager {

    public static void sendError(String error) throws Exception {
        new ErrorManager().start(new Stage(), error);
    }

    public static String content;
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
