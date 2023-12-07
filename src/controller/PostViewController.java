package controller;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import ui.UIAnimation;
import ui.UIManager;
import ui.UINetwork;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class PostViewController implements Initializable {

    //postViewFrame.fxml 具体帖子浏览器
    public ScrollPane postViewScrollPane;
    public VBox mainVBox;
    public ImageView postViewCancel;
    public WebView postContent;
    public ImageView authorProtrait;
    public Text authorText, title;
    public AnchorPane commentPane;
    public ImageView likeButton, commentButton, commentCancel, commentConfirm;
    public HTMLEditor commentEditor;
    public TextField replyField;
    public AnchorPane replyPane;
    public ImageView replyConfirm, replyCancel;
    public UUID postID;
    public VBox imageBox;

    public void addImage(URL url){
        ImageView imageView = new ImageView(new Image(url.toString(), true));
        imageView.setFitHeight(606);
        imageView.setFitWidth(916);
        imageBox.setMinHeight(imageBox.getMinHeight()+606);
        imageBox.getChildren().addAll(imageView);
    }

    public void resetImageBox(){
        imageBox.setMinHeight(1);
        imageBox.getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.postViewController = this;

        postContent.setMouseTransparent(false);

        commentPane.setOpacity(0.0);
        commentPane.setMouseTransparent(true);
        replyPane.setOpacity(0.0);
        replyPane.setMouseTransparent(true);

        likeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(likeButton, 0, 360);
            }
        });
        likeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //点赞？
                likeButton.setImage(new Image("./resources/postViewFrameImage/redHearts.png"));
                UIAnimation.setRotateAnimation(likeButton, 0, 360);
            }
        });

        commentButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(commentButton, 0, 360);
            }
        });
        commentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setBlackMask(commentPane, event -> {
                    commentPane.setMouseTransparent(false);
                }, 600);
            }
        });

        commentCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.fadeAnimation(commentPane, null, false, 600);
                commentPane.setMouseTransparent(true);
            }
        });

        commentConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (commentEditor.getHtmlText().length() > 0){
                    UINetwork.addComment(postID, commentEditor.getHtmlText());
                    UIAnimation.fadeAnimation(commentPane, null, false, 600);
                    commentPane.setMouseTransparent(true);
                }
            }
        });

    }
}
