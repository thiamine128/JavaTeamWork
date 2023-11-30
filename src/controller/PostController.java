package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import post.PostBox;
import ui.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PostController implements Initializable {

    //postFrame.fxml 贴吧浏览器
    public ImageView postFrameMask;
    public ImageView postFrameIni;
    public ImageView postCancel;
    public VBox postMainVbox;
    public ScrollPane postScroll;
    public void setPostFrameIni(){
        postFrameIni.setMouseTransparent(true);
        UIFunction.iniPostFrame();
        UINetwork.fetchAllPost();
        UIAnimation.fadeAnimation(postFrameMask, null,false, 2000);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.postController = this;
        postScroll.setPannable(false);
    }
}
