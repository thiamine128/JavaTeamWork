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
import post.Dislikes;
import post.Likes;
import ui.UIAnimation;
import ui.UIManager;
import ui.UINetwork;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * The post view controller, corresponds to postViewFrame.fxml.
 */
public class PostViewController implements Initializable {

    /**
     * The post view scroll pane.
     */
//postViewFrame.fxml 具体帖子浏览器
    public ScrollPane postViewScrollPane;
    /**
     * The main box.
     */
    public VBox mainVBox;
    /**
     * The post view cancel.
     */
    public ImageView postViewCancel;
    /**
     * The post content.
     */
    public WebView postContent;
    /**
     * The author protrait.
     */
    public ImageView authorProtrait;
    /**
     * The author text.
     */
    public Text authorText, /**
     * The title.
     */
    title;
    /**
     * The comment pane.
     */
    public AnchorPane commentPane, /**
     * The main pane.
     */
    mainPane;
    /**
     * The like button.
     */
    public ImageView likeButton, /**
     * The comment button.
     */
    commentButton, /**
     * The comment cancel button.
     */
    commentCancel, /**
     * The comment confirm button.
     */
    commentConfirm;
    /**
     * The comment editor.
     */
    public HTMLEditor commentEditor;
    /**
     * The reply field.
     */
    public TextField replyField;
    /**
     * The reply pane.
     */
    public AnchorPane replyPane;
    /**
     * The reply confirm button.
     */
    public ImageView replyConfirm, /**
     * The reply cancel button.
     */
    replyCancel;
    /**
     * The post id.
     */
    public UUID postID;
    /**
     * The image box.
     */
    public VBox imageBox;

    /**
     * Add image of the post.
     *
     * @param url the image url
     */
    public void addImage(URL url){
        ImageView imageView = new ImageView(new Image(url.toString(), true));
        imageView.setFitHeight(606);
        imageView.setFitWidth(916);
        imageBox.setMinHeight(imageBox.getMinHeight()+606);
        imageBox.getChildren().addAll(imageView);
    }

    /**
     * Reset image box.
     */
    public void resetImageBox(){
        imageBox.setMinHeight(1);
        imageBox.getChildren().clear();
    }

    private boolean isLiked = false;

    /**
     * Set white heart.
     *
     * @param key the number of likes
     */
    public void setWhiteHeart(int key){
        isLiked = false;
        likeButton.setImage(new Image(getClass().getResourceAsStream("/postViewFrameImage/hearts.png")));
        UIManager.postController.updateLikes(postID, key);
    }

    /**
     * Set red heart.
     *
     * @param key the number of likes
     */
    public void setRedHeart(int key){
        isLiked = true;
        likeButton.setImage(new Image(getClass().getResourceAsStream("/postViewFrameImage/redHearts.png")));
        UIManager.postController.updateLikes(postID, key);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.postViewController = this;

        postContent.setMouseTransparent(true);

        commentPane.setOpacity(0.0);
        commentPane.setMouseTransparent(true);
        replyPane.setOpacity(0.0);
        replyPane.setMouseTransparent(true);

        likeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                likeButton.setOpacity(0.7);
            }
        });
        likeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                likeButton.setOpacity(1.0);
            }
        });
        likeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //点赞？
                if (isLiked){
                    setWhiteHeart(-1);
                    UIAnimation.vectorMove(likeButton, 0, -5, 100, event -> {
                        UIAnimation.vectorMove(likeButton, 0, 5, 100, null);
                    });
                    Dislikes newdislikes = new Dislikes();
                    mainPane.getChildren().add(newdislikes);
                    UIAnimation.setBlackMask(newdislikes, null, 50, 0.0, 0.8);
                    UIAnimation.vectorMove(newdislikes, 0, -8, 200, event -> {
                        UIAnimation.fadeAnimation(newdislikes, event1 -> {
                            mainPane.getChildren().removeAll(newdislikes);
                        }, false, 200);
                    });
                    System.out.println("post dislikes: "+postID);
                    UINetwork.setPostDislikes(postID);
                }else{
                    setRedHeart(1);
                    UIAnimation.vectorMove(likeButton, 0, -5, 50, event -> {
                        UIAnimation.vectorMove(likeButton, 0, 5, 50, null);
                    });
                    Likes newlikes = new Likes();
                    mainPane.getChildren().addAll(newlikes);
                    UIAnimation.setBlackMask(newlikes, null, 50, 0.0, 0.8);
                    UIAnimation.vectorMove(newlikes, 0, -8, 200, event -> {
                        UIAnimation.fadeAnimation(newlikes, event1 -> {
                            mainPane.getChildren().removeAll(newlikes);
                        }, false, 200);
                    });
                    System.out.println("post likes: "+postID);
                    UINetwork.setPostLikes(postID);
                }
                likeButton.setMouseTransparent(true);
                UIAnimation.timer(5000, event -> likeButton.setMouseTransparent(false));
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
