package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import post.LanguageTool;
import ui.UIAnimation;
import ui.UIManager;
import ui.UINetwork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PersonController implements Initializable {

    public FileChooser fileChooser; //图片选择器
    public ImageView personCancel, changeButton; //退出按钮、切换头像按钮
    public ImageView level, protraitImage, hkImage, mcImage; //用户等级、头像、港澳贴图
    public Group provinceGroup; //身份图片组
    public Text username, loginDate; //用户名、注册日期
    public ImageView puzzleTrophy, postTrophy, questionTrophy; //奖杯
    public Text puzzleHint, postHint, questionHint; //奖杯名称显示
    public StackedBarChart<String, Integer> chart;
    private List<Node> provinceList = new ArrayList<>(); //省份贴图存储
    private double ratioTest = 2.0; //缩放比例
    public FrameEnum backFrame = FrameEnum.MainFrame;
    public void setProvinceImage(){ //设置省份贴图
        provinceGroup.getChildren().clear();
        for (Node province : UIManager.mainController.provincePane.getChildren()){
            ImageView province0 = new ImageView(new Image("resources/provinces/"+province.getId()+".png"));
            provinceGroup.getChildren().addAll(province0);
            province0.setId(province.getId());
            provinceList.add(province0);
            province0.setFitHeight(province.getLayoutBounds().getHeight()/ratioTest);
            province0.setFitWidth(province.getLayoutBounds().getWidth()/ratioTest);
            province0.setLayoutX(province.getLayoutX()/ratioTest);
            province0.setLayoutY(province.getLayoutY()/ratioTest);
        }
    }

    public void setProvinceColor(String provinceName, int cnt){
        if (cnt > 10) cnt = 10;
        double degree = 1.0 - 0.3*(cnt)/10;
        for (Node provinceImage : provinceList)if(provinceImage.getId().equals(provinceName)){
            provinceImage.setEffect(new ColorAdjust(degree, 1.0, 0.0, 1.0));
        }
        if (provinceName.equals("aomen")) mcImage.setEffect(new ColorAdjust(degree, 1.0, 0.0, 1.0));
        if (provinceName.equals("xianggang")) hkImage.setEffect(new ColorAdjust(degree, 1.0, 0.0, 1.0));
    }

    public void uploadPersonalProtrait() throws IOException { //上传图片
        File personalImage = fileChooser.showOpenDialog(UIManager.mainStage);
        if (personalImage != null){
            UINetwork.uploadProtrait(personalImage.toPath());
            UIAnimation.timer(2000, event ->
                    UINetwork.fetchProfile(UIManager.mainController.frameUsername.getText()));
        }
    }

    public void addChartInfo(Map<String, Long> mp){

        int cntSum = 0;
        Map<String, Integer> mp0 = new TreeMap<>();
        for (String key : mp.keySet()){
            int cnt = Math.toIntExact(mp.get(key));
            mp0.put(key, -cnt);
            cntSum += cnt;
        }
        chart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        int j = 0;
        for (String key : mp0.keySet()){
            int cnt = -mp0.get(key);
            series.getData().add(new XYChart.Data(LanguageTool.englishToChinese.get(key), cnt));
            j++;
            if (j > 10) break;
        }
        chart.getData().add(series);
        for(Node n : chart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: blue");
        }
        if (cntSum < 5) level.setImage(new Image("resources/personImage/1.png"));
        else if (cntSum < 15) level.setImage(new Image("resources/personImage/2.png"));
        else if (cntSum < 25) level.setImage(new Image("resources/personImage/3.png"));
        else if (cntSum < 50) level.setImage(new Image("resources/personImage/4.png"));
        else level.setImage(new Image("resources/personImage/5.png"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chart.setAnimated(false);
        chart.setLegendVisible(false);

        UIManager.personController = this;

        level.setOpacity(0.8);
        puzzleHint.setOpacity(0.0);
        postHint.setOpacity(0.0);
        questionHint.setOpacity(0.0);

        fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("图片文件",
                "*.jpg", "*.jpeg", "*.png"));

        level.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                level.setEffect(new ColorAdjust(0.7, 0.5, 0.5, 0.7));
            }
        });
        level.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                level.setEffect(null);
            }
        });

        changeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(changeButton, 0, 360);
            }
        });
        changeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    uploadPersonalProtrait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        personCancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                personCancel.setOpacity(0.5);
            }
        });
        personCancel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                personCancel.setOpacity(0.8);
            }
        });
        personCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                //回到哪里？看具体情况
                try {
                    switch (backFrame){
                        case MainFrame: UIManager.instance.toMainFrame(false); break;
                        case PostFrame: UIManager.instance.toPostFrame(true); break;
                        case PostViewFrame: UIManager.instance.toPostViewFrame(); break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        puzzleTrophy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                puzzleTrophy.setOpacity(0.7);
                UIAnimation.setBlackMask(puzzleHint, null, 600, puzzleHint.getOpacity(), 0.8);
            }
        });
        puzzleTrophy.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                puzzleTrophy.setOpacity(0.8);
                UIAnimation.setBlackMask(puzzleHint, null, 600, puzzleHint.getOpacity(), 0.0);
            }
        });

        postTrophy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                postTrophy.setOpacity(0.7);
                UIAnimation.setBlackMask(postHint, null, 600, postHint.getOpacity(), 0.8);
            }
        });
        postTrophy.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                postTrophy.setOpacity(0.8);
                UIAnimation.setBlackMask(postHint, null, 600, postHint.getOpacity(), 0.0);
            }
        });

        questionTrophy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionTrophy.setOpacity(0.7);
                UIAnimation.setBlackMask(questionHint, null, 600, questionHint.getOpacity(), 0.8);
            }
        });
        questionTrophy.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionTrophy.setOpacity(0.8);
                UIAnimation.setBlackMask(questionHint, null, 600, questionHint.getOpacity(), 0.0);
            }
        });

    }
}