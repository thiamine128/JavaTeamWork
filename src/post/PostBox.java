package post;

import controller.FrameEnum;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import ui.UIManager;
import ui.UINetwork;

import java.net.URL;
import java.util.UUID;

public class PostBox extends VBox{

    private UUID postID;
    private String authorName;
    private int likes;
    private HBox imageBox;

    public UUID getPostID(){
        return postID;
    }

    private Text titleMaker(String title){
        Text result = new Text("  "+title);
        Font font = Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 40);
        result.setFont(font);
        result.setWrappingWidth(850);

        result.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(150, 150, 150));
            }
        });
        result.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(0, 0, 0));
            }
        });
        PostBox thisBox = this;
        result.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //进入帖子
                UIManager.postController.setPostBox(thisBox);
                UINetwork.checkLikes(postID);
                UINetwork.fetchPost(postID);
            }
        });

        return result;
    }

    private Text authorMaker(String author){
        Text result = new Text("    "+author);
        Font font = Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20);
        result.setFont(font);
        result.setFill(Color.rgb(0, 128, 255));

        result.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(0, 188, 55));
            }
        });
        result.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(0, 128, 255));
            }
        });
        result.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                //点击用户获取信息
                UINetwork.fetchProfile(authorName);
                try {
                    UIManager.instance.toPersonFrame(FrameEnum.PostFrame, false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        return result;
    }

    private Text provinceTimeTextMaker(String province, String postTime){
        Text result = new Text("   "+province+"  "+postTime);
        result.setFont(new Font(20));
        result.setFill(Color.rgb(128, 128, 128));
        return result;
    }

    private Text likesTextMaker(){
        Text result = new Text("  "+likes+" likes");
        Font font = Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20);
        result.setFont(font);
        result.setFill(Color.rgb(13, 51, 255));
        likesText = result;
        return result;
    }
    private Text likesText;
    public void setlikes(int key){
        if (key > 0) likes++;
        else if (key < 0) likes--;
        likesText.setText("  "+likes+" likes");
    }

    private Text cancelMaker(){
        Text result = new Text("    "+"REMOVE"+"  ");
        result.setFont(new Font(20));
        result.setFill(Color.rgb(222, 28, 28));
        result.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UINetwork.removePost(postID);
                for (Node p : UIManager.postController.postMainVbox.getChildren()){
                    if (p instanceof PostBox){
                        if(((PostBox) p).getPostID().equals(postID)){
                            UIManager.postController.postMainVbox.getChildren().removeAll(p);
                            break;
                        }
                    }
                }
            }
        });
        result.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                result.setFill(Color.rgb(222, 58, 58));
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

    public PostBox(UUID id, String title, String author, String province, String postTime, int likes){
        this.likes = likes;
        this.postID = id;
        this.authorName = author;
        this.setMinWidth(900);
        this.setMinHeight(280);
        this.setMaxWidth(900);
        this.setMinHeight(80);
        this.setPrefHeight(81);
        this.getChildren().addAll(titleMaker(title));
        TextFlow flow = new TextFlow();
        flow.getChildren().addAll(authorMaker(author));
        flow.getChildren().addAll(likesTextMaker());
        flow.getChildren().addAll(provinceTimeTextMaker(LanguageTool.englishToChinese.get(province), postTime));
        if (author.equals(UIManager.mainController.frameUsername.getText()))
            flow.getChildren().addAll(cancelMaker());
        this.getChildren().addAll(flow);
        imageBox = new HBox();
        imageBox.setMaxWidth(900);
        imageBox.setMinWidth(900);
        imageBox.setMinHeight(1);
        imageBox.setMaxHeight(97);
        imageBox.setPrefHeight(1);
        this.getChildren().addAll(imageBox);
    }

    public void addImage(URL url){
        imageBox.getChildren().addAll(new Separator(Orientation.VERTICAL));
        ImageView imageView = new ImageView(new Image(url.toString(), true));
        imageView.setFitHeight(199);
        imageView.setFitWidth(299);
        imageBox.getChildren().addAll(imageView);
        this.setPrefHeight(280);
    }


    public UUID getUUID(){
        return this.postID;
    }

}
