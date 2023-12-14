package controller;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import post.LanguageTool;
import post.PostBox;
import ui.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class PostController implements Initializable {

    //postFrame.fxml 贴吧浏览器
    public ImageView postFrameMask;
    public ImageView postFrameIni;
    public ImageView postCancel, frButton, nxtButton, searchButton;
    public VBox postMainVbox;
    public ScrollPane postScroll;
    public Text pageNum;
    public TextField provinceSearch;
    private int thisPage = 1, pageTotalNum = 1, pageSize0 = 10;
    public void setTotalNum(int num){
        pageTotalNum = num;
    }
    private PostBox thisPostBox;
    public void setPostBox(PostBox box){
        thisPostBox = box;
    }
    public void updateLikes(UUID postID, int key){
        if (thisPostBox != null) thisPostBox.setlikes(key);
    }

    private void fetchPosts(int pageNumber, int pageSize, String name){
        if (name.length() == 0) UINetwork.fetchAllPost(pageNumber, pageSize);
        else UINetwork.fetchPostInProvince(pageNumber, pageSize, name);
        pageNum.setText((pageNumber+1)+"");
    }

    public void setPostFrameIni(){
        postFrameIni.setMouseTransparent(true);
        UIFunction.iniPostFrame();
        thisPage = 1;
        thisName = "";
        fetchPosts(0, pageSize0, thisName);
        postFrameMask.setMouseTransparent(false);
        UIAnimation.fadeAnimation(postFrameMask, event -> {
            postFrameMask.setMouseTransparent(true);
        },false, 2000);
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

    private String thisName = "";
    private void searchProvinceFunc(String provinceName){
        if (LanguageTool.provinceSetEng.contains(provinceName)){
            thisName = provinceName;
            fetchPosts(0, pageSize0, provinceName);
            thisPage = 1;
        }else if (LanguageTool.provinceSetChn.contains(provinceName)){
            thisName = LanguageTool.chineseToEnglish.get(provinceName);
            fetchPosts(0, 10, thisName);
            thisPage = 1;
        }else if (provinceName.length() == 0){
            thisName = "";
            fetchPosts(0, pageSize0, "");
            thisPage = 1;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        postFrameMask.setVisible(true);
        UIManager.postController = this;
        postScroll.setPannable(false);

        setButtonChange(nxtButton);
        setButtonChange(frButton);

        searchButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                searchButton.setOpacity(0.7);
            }
        });
        searchButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                searchButton.setOpacity(0.8);
            }
        });
        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                searchProvinceFunc(provinceSearch.getText());
            }
        });

        frButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (thisPage > 1){
                    thisPage--;
                    fetchPosts(thisPage-1, pageSize0, thisName);
                }
            }
        });

        nxtButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (thisPage < pageTotalNum){
                    thisPage++;
                    fetchPosts(thisPage-1, pageSize0, thisName);
                }
            }
        });

    }
}
