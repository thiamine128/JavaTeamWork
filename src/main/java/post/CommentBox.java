package post;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import ui.UIManager;
import ui.UINetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The comment box showing comments.
 */
public class CommentBox extends VBox{

    private CommentBox instance;
    /**
     * The id of this comment.
     */
    public UUID commentID;
    private MainComment mainComment;
    private List<ReplyBox> replyList = new ArrayList<>();
    private Text timeScale;
    private VBox vBox;
    private String author;

    private TextFlow makeDown(){
        TextFlow result = new TextFlow();
        result.setTextAlignment(TextAlignment.RIGHT);
        result.getChildren().addAll(this.timeScale);
        if (UIManager.mainController.frameUsername.getText().equals(author) || UIManager.mainController.getUserPower())
            result.getChildren().addAll(cancelMaker());
        return result;
    }

    private Text cancelMaker(){
        Text result = new Text("    "+"REMOVE"+"  ");
        result.setFont(new Font(16));
        result.setFill(Color.rgb(222, 28, 28));
        result.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UINetwork.removeComment(commentID);
                UIManager.postViewController.mainVBox.getChildren().removeAll(instance);
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
     * Instantiates a new Comment box.
     *
     * @param id        the id of this comment.
     * @param username  the username.
     * @param content   the content.
     * @param imagepath image path.
     * @param timeScale the time of upload.
     * @param pos       position.
     */
    public CommentBox(UUID id, String username, String content, String imagepath, String timeScale, String pos){
        instance = this;
        author = username;
        commentID = id;
        this.setPrefHeight(201);
        this.setPrefWidth(917);
        mainComment = new MainComment(id, username, content, imagepath);
        this.getChildren().addAll(mainComment);

        this.timeScale = new Text( timeScale + "    " + "发布于 " + pos + "        ");
        this.timeScale.setFont(new Font(16));
        this.timeScale.setWrappingWidth(913);
        this.timeScale.setFill(Color.rgb(100, 100, 100));
        this.timeScale.setTextAlignment(TextAlignment.RIGHT);

        this.getChildren().addAll(makeDown());
    }

    /**
     * Add reply.
     *
     * @param replyID  the id of this reply.
     * @param username the username.
     * @param content  the content.
     */
    public void addReply(UUID replyID, String username, String content){ //普通回复
        ReplyBox newbox = new ReplyBox(commentID, replyID, username, content, this);
        replyList.add(newbox);
        this.getChildren().addAll(newbox);
    }

    /**
     * Add reply.
     *
     * @param replyID     the reply id.
     * @param username    the username.
     * @param repliedName the replied name.
     * @param content     the content.
     */
    public void addReply(UUID replyID, String username, String repliedName, String content){ //二级回复
        ReplyBox newbox = new ReplyBox(commentID, replyID, username, repliedName, content, this);
        replyList.add(newbox);
        this.getChildren().addAll(newbox);
    }

    /**
     * Remove reply.
     *
     * @param replyID the reply id.
     */
    public void removeReply(UUID replyID){
        ReplyBox box = null;
        for (ReplyBox replyBox : replyList){
            if (replyBox.replyID == replyID) box = replyBox;
        }
        if (box != null) {
            this.getChildren().removeAll(box);
            replyList.remove(box);
        }
    }

}
