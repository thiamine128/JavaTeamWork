package controller;

import Game.Hamiltonian;
import Game.HamiltonianDetail;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;
import ui.UINetwork;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MainController implements Initializable {

    @FXML
    public Pane primePane; //总模板
    @FXML
    public Pane openingPane; //开头场景模板
    @FXML
    public Pane provincePane, pointPane; //各区域模板
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
    public ImageView toPostButton, toQuestionButton, HButton, hamiButton;
    public ImageView profilePhoto;
    private boolean Hsituation = false, provinceProtect = true;
    private Set<String> provinceSet = new HashSet<>();
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

    private Node findProvincePoint(String name){
        for (Node point : pointPane.getChildren()){
            if (point.getId().equals(name)) return point;
        }
        return null;
    }

    public Text hamiHint, hamiTitle;
    public Pane hamiPane;

    private void hamiltonianCal(){
        Set <Node> nodeSet = new HashSet<>();
        for (Node j : pointPane.getChildren()){
            if (j instanceof Line) nodeSet.add(j);
        }
        for (Node j : nodeSet) {
            UIAnimation.fadeAnimation(j, event -> pointPane.getChildren().removeAll(j),
                    false, 600);
        }
        if (provinceSet.size() > 2){
            pointPane.setOpacity(0.8);

            int n = provinceSet.size(), i = 0;
            Hamiltonian hamiltonian = new Hamiltonian();
            String [] provinces = new String[n];
            for (String name : provinceSet) provinces[i++] = name;
            HamiltonianDetail detail = hamiltonian.calcTime(provinces);
            System.out.println(Arrays.toString(provinces));
            System.out.println(Arrays.toString(detail.path));
            if (detail.found){
                String [] result = detail.path;
                int rlen = result.length;
                for (int j=0;j<rlen-1;j++){
                    Node p1 = findProvincePoint(result[j]);
                    Node p2 = findProvincePoint(result[j+1]);
                    if (p1 != null && p2 != null){
                        Line line = new Line();
                        line.setStroke(Color.rgb(182, 255, 253));
                        line.setStrokeWidth(3.0);
                        line.setOpacity(0.7);
                        line.setOpacity(0.0);
                        line.setStartX(p1.getLayoutX());
                        line.setStartY(p1.getLayoutY());
                        line.setEndX(p2.getLayoutX());
                        line.setEndY(p2.getLayoutY());
                        pointPane.getChildren().addAll(line);
                        UIAnimation.setBlackMask(line, null, 600);
                    }
                }
                setHamiHint("路径生成成功", String.format("总时长为%d小时", detail.cost));
            }else{
                setHamiHint("", "生成失败");
            }
        }else{
            setHamiHint("", "生成失败");
        }
    }

    private void setHamiHint(String title, String hint){
        UIAnimation.fadeAnimation(hamiTitle, null, false, 300);
        UIAnimation.fadeAnimation(hamiHint, event -> {
            hamiTitle.setText(title);
            hamiHint.setText(hint);
            UIAnimation.setBlackMask(hamiTitle, null, 600);
            UIAnimation.setBlackMask(hamiHint, null, 600);
        }, false, 300);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        hamiPane.setOpacity(0.0);
        hamiTitle.setText("");
        hamiHint.setText("请选择省份");
        pointPane.setMouseTransparent(true);
        pointPane.setOpacity(0.0);
        hamiButton.setMouseTransparent(true);
        hamiButton.setOpacity(0.0);

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

        toQuestionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setBlackMask(mask, event -> {
                    try {
                        UIManager.instance.toQuestionFrame();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, 600);
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

                        //启动哈密顿路径模式
                        for (Node province : provincePane.getChildren()){
                            province.setOnMouseExited(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if (!provinceSet.contains(province.getId()))
                                        province.setEffect(new ColorAdjust(1.0, 1.0, 0, 1.0));
                                }
                            });
                            province.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    province.setEffect(new ColorAdjust(0.9, 1.0, 0, 1.0));
                                }
                            });
                            province.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if (provinceSet.contains(province.getId())){ //取消选择
                                        provinceSet.remove(province.getId());
                                        UIAnimation.vectorMove(province, 0, 5, 200, null);
                                    }else{ //选择
                                        provinceSet.add(province.getId());
                                        UIAnimation.vectorMove(province, 0, -5, 200, null);
                                    }
                                }
                            });
                        }

                        hamiButton.setMouseTransparent(false);
                        UIAnimation.setBlackMask(hamiButton, null, 600, 0.0, 0.8);

                        hamiButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                hamiltonianCal();
                            }
                        });
                        hamiButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                UIAnimation.setRotateAnimation(hamiButton, 0, 360);
                            }
                        });

                        UIAnimation.setBlackMask(hamiPane, null, 600, 0.0, 0.8);

                    }else{
                        Hsituation = false;
                        HButton.setEffect(null);

                        for(Node province : provincePane.getChildren()){
                            if (provinceSet.contains(province.getId())){
                                provinceSet.remove(province.getId());
                                UIAnimation.vectorMove(province, 0, 5, 50, null);
                            }
                        }

                        UIAnimation.timer(100, event5 -> {
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
                            UIFunction.iniMainFrameButton(provincePane, infoImage, MainController.this);
                        });

                        UIAnimation.fadeAnimation(pointPane, null, false, 600);
                        Set <Node> nodeSet = new HashSet<>();
                        for (Node j : pointPane.getChildren()){
                            if (j instanceof Line) nodeSet.add(j);
                        }
                        for (Node j : nodeSet) pointPane.getChildren().removeAll(j);
                        hamiButton.setMouseTransparent(true);
                        UIAnimation.setBlackMask(hamiButton, null, 600, 0.8, 0.0);
                        UIAnimation.setBlackMask(hamiPane, null, 600, 0.8, 0.0);
                        hamiTitle.setText("");
                        hamiHint.setText("请选择省份");
                        //重新按钮设定
                    }
                }
            }
        });

    }
}
