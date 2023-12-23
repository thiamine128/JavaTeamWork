package controller;/*
 * @Description
 * @author
 * @date 2023/12/19 18:38
 */

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.ErrorManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The error controller, corresponds to errorFrame.fxml.
 */
public class ErrorController implements Initializable {

    /**
     * The content of error.
     */
    public Text errorContent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        errorContent.setText(ErrorManager.content);
    }
}
