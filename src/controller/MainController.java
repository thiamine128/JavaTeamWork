package controller;

import Game.Hamiltonian;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;
import ui.UINetwork;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public Pane primePane; //总模板
    @FXML
    public Pane openingPane; //开头场景模板
    @FXML
    public Pane provincePane; //各区域模板
    @FXML
    public Group circleAnimationGroup; //鼠标点击动画
    @FXML
    public ImageView mask; //遮罩动画
    @FXML
    public ImageView infoImage, provinceEdge; //省份艺术字信息
    public ImageView mainFrameIni; //初始化触发器
    public ImageView toPuzzleButton; //拼图游戏按钮
    public ImageView mainFrameBG; //开头场景
    public Text frameUsername;
    public ImageView toPostButton, toQuestionButton, HButton;
    public ImageView profilePhoto;
    private boolean Hsituation = false, provinceProtect = true;

    @FXML
    private void provinceAppear(Node[] provinceArray, int cnt, int i){ //省份贴图登入动画
        UIAnimation.buttonIniAnimation(provinceArray[i]);
        UIAnimation.setBlackMask(provinceArray[i], event2 -> {
            if (i+1 < cnt) provinceAppear(provinceArray, cnt, i+1);
        }, 500*Math.random());
    }

    public void mainFrameTrigger(){ //主界面触发
        UIAnimation.fadeAnimation(mask, null, false);
        mainFrameIni.setMouseTransparent(true);
        mainFrameBG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setSparkleCircle(mouseEvent.getX(), mouseEvent.getY(), circleAnimationGroup);
                UIAnimation.timer(200, event -> {
                    try {
                        mainFrameButtonIni();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
    @FXML
    public void mainFrameButtonIni() throws Exception {

        UIAnimation.setBlackMask(mask, event -> {

            for (Node province : provincePane.getChildren()){
                province.setOpacity(0.0);
            }

            openingPane.setVisible(false);
            openingPane.setDisable(true);
            mask.setMouseTransparent(true);
            UIAnimation.timer(2000, event5->{
                UIAnimation.fadeAnimation(mask, event1->{
                    Node [] provinceArray = new Node[40];
                    int cnt = 0;
                    for (Node province : provincePane.getChildren()){
                        provinceArray[cnt++] = province;
                    }
                    provinceAppear(provinceArray, 5, 0);
                    provinceAppear(provinceArray, 10, 5);
                    provinceAppear(provinceArray, 15, 10);
                    provinceAppear(provinceArray, 20, 15);
                    provinceAppear(provinceArray, 25, 20);
                    provinceAppear(provinceArray, 30, 25);
                    provinceAppear(provinceArray, 34, 30);
                }, false);
            });

        }, 1000); //遮罩动画

        setMouseCircleAnimation(circleAnimationGroup); //鼠标特效设置
        UIFunction.iniMainFrameButton(provincePane, infoImage, this); //按钮设定

    }

    public void setMouseCircleAnimation(Group group){
        UIAnimation.setMouseCircleAnimation(group); //鼠标效果设置
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.mainController = this;
        provinceEdge.setPreserveRatio(false);
        mask.setVisible(true);
        openingPane.setVisible(true);

        profilePhoto.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                profilePhoto.setOpacity(0.8);
            }
        });
        profilePhoto.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                profilePhoto.setOpacity(1.0);
            }
        });
        profilePhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    UINetwork.fetchProfile(frameUsername.getText());
                    UIManager.instance.toPersonFrame(FrameEnum.MainFrame, true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        toQuestionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(toQuestionButton, 0, 360);
            }
        });
        HButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(HButton, 0, 360);
            }
        });
        HButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //启动哈密顿路径模式
                if (provinceProtect){
                    provinceProtect = false;
                    if (!Hsituation){
                        Hsituation = true;
                        HButton.setEffect(new ColorAdjust(0.5, 0.5, 0.5, 0.5));
                        UIAnimation.setRotateAnimation(HButton, 0, 360);

                        for(Node province : provincePane.getChildren()){
                            UIAnimation.timer(300*Math.random(), event0->{
                                province.setEffect(new ColorAdjust(1.0, 1.0, 0, 1.0));
                                UIAnimation.vectorMove(province, 0, -20, 200, event -> {
                                    UIAnimation.vectorMove(province, 0, 20, 200, event1 -> {
                                        UIAnimation.timer(500, event2 -> provinceProtect = true);
                                    });
                                });
                            });
                        }
                    }else{
                        Hsituation = false;
                        HButton.setEffect(null);

                        for(Node province : provincePane.getChildren()){
                            UIAnimation.timer(300*Math.random(), event0->{
                                province.setEffect(null);
                                UIAnimation.vectorMove(province, 0, -20, 200, event -> {
                                    UIAnimation.vectorMove(province, 0, 20, 200, event1 -> {
                                        UIAnimation.timer(500, event2 -> provinceProtect = true);
                                    });
                                });
                            });
                        }
                    }
                }
            }
        });

    }
}
