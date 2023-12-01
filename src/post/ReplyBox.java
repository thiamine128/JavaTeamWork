package post;
import controller.FrameEnum;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import ui.TestEventHandler;
import ui.UIManager;
import ui.UINetwork;

import java.util.UUID;

public class ReplyBox extends TextFlow {

    private String thisUsername;
    public UUID commentID, replyID;

    private static ColorAdjust effectMaker(double brightness){
        ColorAdjust color_adjust = new ColorAdjust();
        color_adjust.setHue(0.7);
        color_adjust.setBrightness(brightness);
        color_adjust.setContrast(0.5);
        color_adjust.setSaturation(0.5);
        return color_adjust;
    }

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
                            TestEventHandler.commentID = commentID;
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

    public ReplyBox(UUID commentID, UUID replyID, String username, String content){ //普通回复
        this.commentID = commentID;
        this.replyID = replyID;
        thisUsername = username;
        this.getChildren().addAll(textMaker("$", false));
        this.getChildren().addAll(setUsernameFunc(textMaker(username, true)));
        this.getChildren().addAll(setCommentReplyFunc(textMaker("："+content, false)));
        this.setMaxWidth(913);
    }

    public ReplyBox(UUID commentID, UUID replyID, String username, String repliedName, String content){ //二级回复
        this.commentID = commentID;
        this.replyID = replyID;
        thisUsername = username;
        this.getChildren().addAll(textMaker("$", false));
        this.getChildren().addAll(setUsernameFunc(textMaker(username, true)));
        this.getChildren().addAll(textMaker(" 回复 ", false));
        this.getChildren().addAll(setUsernameFunc(textMaker(repliedName, true)));
        this.getChildren().addAll(setCommentReplyFunc(textMaker("："+content, false)));
        this.setMaxWidth(913);
    }

}