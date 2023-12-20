package post;

import controller.FrameEnum;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.web.*;
import ui.AppClientEventHandler;
import ui.UIManager;
import ui.UINetwork;

import java.net.MalformedURLException;
import java.util.UUID;


/**
 * The main comment.
 */
public class MainComment extends HBox{

    private VBox protraitBox;
    private Text commentAuthor;
    private ImageView authorImage;
    private WebView content;
    private UUID commentID;

    /**
     * Instantiates a new Main comment.
     *
     * @param commentID the comment id
     * @param username  the username
     * @param content   the content
     * @param imagepath the imagepath
     */
    public MainComment(UUID commentID, String username, String content, String imagepath){
        this.commentID = commentID;
        this.content = new WebView();

        if (content.contains("contenteditable=\"true\""))
            content = content.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
        else content = " <body contentEditable=\"false\">" + content;
        this.content.getEngine().loadContent(content);

        commentAuthor = new Text(username);
        try {
            authorImage = new ImageView(new Image(UINetwork.getPortraitUrl(username).toString()));
        } catch (MalformedURLException e) {

        }

        commentAuthor.setWrappingWidth(180);
        commentAuthor.setTextAlignment(TextAlignment.CENTER);
        Font font = Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20);
        commentAuthor.setFont(font);
        commentAuthor.setFill(Color.rgb(11, 106, 190));
        setUsernameFunc(commentAuthor);

        authorImage.setFitHeight(180);
        authorImage.setFitWidth(180);
        authorImage.setPreserveRatio(false);

        this.content.setPrefWidth(710);
        this.content.setPrefHeight(215);
        setWebViewFunc(this.content);

        protraitBox = new VBox();
        protraitBox.setPrefHeight(200);
        protraitBox.setPrefWidth(100);

        protraitBox.getChildren().addAll(authorImage);
        protraitBox.getChildren().addAll(commentAuthor);
        this.getChildren().addAll(protraitBox);
        this.getChildren().addAll(this.content);
        this.setPrefWidth(917);
        this.setPrefHeight(214);



    }

    private void setWebViewFunc(WebView webview){

        webview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //点击进行回复（一级回复）
                UIManager.postViewController.replyPane.setOpacity(0.8);
                UIManager.postViewController.replyPane.setMouseTransparent(false);
                UIManager.postViewController.replyField.setText("");
                UIManager.postViewController.replyField.setPromptText("回复"+commentAuthor.getText());
                UIManager.postViewController.replyCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        UIManager.postViewController.replyPane.setOpacity(0.0);
                        UIManager.postViewController.replyPane.setMouseTransparent(true);
                    }
                });
                UIManager.postViewController.replyConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (UIManager.postViewController.replyField.getText().length() > 0){
                            AppClientEventHandler.commentID = commentID;
                            UINetwork.addCommentReply(commentID,
                                    UIManager.postViewController.replyField.getText(), null);
                            UIManager.postViewController.replyPane.setOpacity(0.0);
                            UIManager.postViewController.replyPane.setMouseTransparent(true);
                        }
                    }
                });

            }
        });
        webview.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                webview.setOpacity(0.6);
            }
        });
        webview.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                webview.setOpacity(1.0);
            }
        });
    }

    private static ColorAdjust effectMaker(double brightness){
        ColorAdjust color_adjust = new ColorAdjust();
        color_adjust.setHue(0.7);
        color_adjust.setBrightness(brightness);
        color_adjust.setContrast(0.5);
        color_adjust.setSaturation(0.5);
        return color_adjust;
    }

    /**
     * Set username func text.
     *
     * @param text0 the text 0
     * @return the text
     */
    public Text setUsernameFunc(Text text0){ //点击用户名：获取用户信息
        text0.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text0.setEffect(effectMaker(0.4));
            }
        });
        text0.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text0.setEffect(null);
            }
        });
        text0.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                //点击用户名获取信息
                UINetwork.fetchProfile(commentAuthor.getText());
                try {
                    UIManager.instance.toPersonFrame(FrameEnum.PostViewFrame, false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return text0;
    }

}
