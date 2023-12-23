package post;
import controller.FrameEnum;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import ui.AppClientEventHandler;
import ui.UIManager;
import ui.UINetwork;

import java.util.UUID;

/**
 * a class showing replies.
 */
public class ReplyBox extends TextFlow {

    private String thisUsername;
    private CommentBox commentBox;
    /**
     * The comment id.
     */
    public UUID commentID, /**
     * The reply id.
     */
    replyID;

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
     * @param text0 the text of reply.
     * @return the object that is a Text.
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
                UINetwork.fetchProfile(thisUsername);
                try {
                    UIManager.instance.toPersonFrame(FrameEnum.PostViewFrame, false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return text0;
    }

    private Text setCommentReplyFunc(Text text0){ //点击评论：进行回复
        text0.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text0.setEffect(effectMaker(0.6));
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

                //点击评论进行回复（二级回复）
                UIManager.postViewController.replyPane.setOpacity(0.8);
                UIManager.postViewController.replyPane.setMouseTransparent(false);
                UIManager.postViewController.replyField.setText("");
                UIManager.postViewController.replyField.setPromptText("回复"+thisUsername);
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
                                    UIManager.postViewController.replyField.getText(), thisUsername);

                            UIManager.postViewController.replyPane.setOpacity(0.0);
                            UIManager.postViewController.replyPane.setMouseTransparent(true);
                        }
                    }
                });

            }
        });
        return text0;
    }

    /**
     * Text maker.
     *
     * @param content     the content.
     * @param boldControl decide whether to be bold.
     * @return the object that is a Text.
     */
    public Text textMaker(String content, boolean boldControl){
        Text result = new Text(content);
        Font font;
        if (boldControl) {
            font = Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 18);
            result.setFont(font);
            result.setFill(Color.rgb(0, 186, 33));
        }else if (content.equals("$")){
            result.setFont(new Font(18));
            result.setFill(Color.rgb(255, 106, 0));
        }else {
            result.setFont(new Font(18));
            result.setFill(Color.rgb(0, 0, 125));
        }
        return result;
    }

    private Text cancelMaker(){
        Text result = new Text("    "+"REMOVE"+"  ");
        result.setFont(new Font(18));
        result.setFill(Color.rgb(222, 28, 28));
        result.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UINetwork.removeReply(replyID);
                commentBox.removeReply(replyID);
            }
        });
        result.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(222, 128, 100));
            }
        });
        result.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(222, 28, 28));
            }
        });
        return result;
    }

    /**
     * Instantiates a new Reply box.
     *
     * @param commentID  the comment id.
     * @param replyID    the reply id.
     * @param username   the username.
     * @param content    the content.
     * @param commentBox the comment box.
     */
    public ReplyBox(UUID commentID, UUID replyID, String username, String content, CommentBox commentBox){ //普通回复
        this.commentBox = commentBox;
        this.commentID = commentID;
        this.replyID = replyID;
        thisUsername = username;
        this.getChildren().addAll(textMaker("$", false));
        this.getChildren().addAll(setUsernameFunc(textMaker(username, true)));
        this.getChildren().addAll(setCommentReplyFunc(textMaker("："+content, false)));
        this.setMaxWidth(913);
        if (UIManager.mainController.frameUsername.getText().equals(username) || UIManager.mainController.getUserPower())
            this.getChildren().addAll(cancelMaker());
    }

    /**
     * Instantiates a new Reply box.
     *
     * @param commentID   the comment id.
     * @param replyID     the reply id.
     * @param username    the username.
     * @param repliedName the replied name.
     * @param content     the content.
     * @param commentBox  the comment box.
     */
    public ReplyBox(UUID commentID, UUID replyID, String username, String repliedName, String content, CommentBox commentBox){ //二级回复
        this.commentBox = commentBox;
        this.commentID = commentID;
        this.replyID = replyID;
        thisUsername = username;
        this.getChildren().addAll(textMaker("$", false));
        this.getChildren().addAll(setUsernameFunc(textMaker(username, true)));
        this.getChildren().addAll(textMaker(" 回复 ", false));
        this.getChildren().addAll(setUsernameFunc(textMaker(repliedName, true)));
        this.getChildren().addAll(setCommentReplyFunc(textMaker("："+content, false)));
        this.setMaxWidth(913);
        if (UIManager.mainController.frameUsername.getText().equals(username) || UIManager.mainController.getUserPower())
            this.getChildren().addAll(cancelMaker());
    }

}