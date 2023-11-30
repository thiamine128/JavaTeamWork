package post;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentBox extends VBox{

    public UUID commentID;
    private MainComment mainComment;
    private List<ReplyBox> replyList = new ArrayList<>();
    private Text timeScale;

    public CommentBox(UUID id, String username, String content, String imagepath, String timeScale, String pos){
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
        this.getChildren().addAll(this.timeScale);
    }

    public void addReply(UUID replyID, String username, String content){ //普通回复
        ReplyBox newbox = new ReplyBox(commentID, replyID, username, content);
        replyList.add(newbox);
        this.getChildren().addAll(newbox);
    }

    public void addReply(UUID replyID, String username, String repliedName, String content){ //二级回复
        ReplyBox newbox = new ReplyBox(commentID, replyID, username, repliedName, content);
        replyList.add(newbox);
        this.getChildren().addAll(newbox);
    }

}
