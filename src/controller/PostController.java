package controller;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import post.PostBox;
import ui.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PostController implements Initializable {

    //postFrame.fxml 贴吧浏览器
    public ImageView postFrameMask;
    public ImageView postFrameIni;
    public ImageView postCancel, frButton, nxtButton;
    public VBox postMainVbox;
    public ScrollPane postScroll;
    public Text pageNum;
    private int thisPage = 1, pageTotalNum = 1, pageSize0 = 10;
    public void setTotalNum(int num){
        pageTotalNum = num;
    }

    private void fetchPosts(int pageNumber, int pageSize){
        UINetwork.fetchAllPost(pageNumber, pageSize);
        pageNum.setText((pageNumber+1)+"");
    }

    public void setPostFrameIni(){
        postFrameIni.setMouseTransparent(true);
        UIFunction.iniPostFrame();
        fetchPosts(0, pageSize0);
        thisPage = 1;
        UIAnimation.fadeAnimation(postFrameMask, null,false, 2000);
    }

    private void setButtonChange(ImageView b){
        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.setOpacity(0.68);
            }
        });
        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.setOpacity(0.8);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        postFrameMask.setVisible(true);
        UIManager.postController = this;
        postScroll.setPannable(false);

        setButtonChange(nxtButton);
        setButtonChange(frButton);

        frButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (thisPage > 1){
                    thisPage--;
                    fetchPosts(thisPage-1, pageSize0);
                }
            }
        });

        nxtButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (thisPage < pageTotalNum){
                    thisPage++;
                    fetchPosts(thisPage-1, pageSize0);
                }
            }
        });

    }
}
