package controller;

import Game.PicturePuzzleGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ui.ProvinceInfoStruct;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PuzzleController implements Initializable {

    //puzzleFrame.fxml 拼图界面
    public static Pane puzzleProvincePane; //省份载体
    @FXML
    public Group puzzleGroup; //拼图载体
    @FXML
    public ImageView puzzleCG; //拼图背景图片
    @FXML
    public ImageView puzzleInfo; //拼图信息
    @FXML
    public ImageView puzzleMask; //拼图界面遮罩
    public ImageView puzzleFrameIni; //初始化触发器
    public ImageView puzzleCancel; //退出按键
    private List<ProvinceInfoStruct> provinceInfoSet = new ArrayList(); //省份信息列
    public void puzzleFrameTrigger(){

        puzzleFrameIni.setMouseTransparent(true);
        Image puzzleCGImage = new Image("resources/puzzleImage/puzzleCG.jpg");
        Image puzzleInfoImage = new Image("resources/puzzleImage/puzzleInfoImage.png");
        puzzleCG.setImage(puzzleCGImage); //设置背景图片
        puzzleInfo.setImage(puzzleInfoImage);
        puzzleInfo.setOpacity(0.0);
        puzzleMask.setMouseTransparent(true);
        puzzleMask.setOpacity(1.0);

        puzzleGroup.getChildren().clear();

        UIAnimation.fadeAnimation(puzzleMask, event -> {

            UIAnimation.setBlackMask(puzzleInfo, null, 500);

            if (provinceInfoSet.isEmpty()){
                for (Node province : puzzleProvincePane.getChildren()){
                    if (!province.getId().equals("aomen") && !province.getId().equals("xianggang")){
                        double x = province.getLayoutX(), y = province.getLayoutY();
                        double width = province.getLayoutBounds().getWidth();
                        double height = province.getLayoutBounds().getHeight();
                        provinceInfoSet.add(new ProvinceInfoStruct(x,y,width,height,province.getId()));
                    }
                }
            }

            for (ProvinceInfoStruct provincePuzzle : provinceInfoSet){

                Image image = new Image("resources/provinces/"+provincePuzzle.getName()+".png");
                ImageView provinceImage = new ImageView();
                provinceImage.setImage(image);
                provinceImage.setOpacity(0.0);
                provinceImage.setId(provincePuzzle.getName());

                provinceImage.setFitWidth(provincePuzzle.getWidth());
                provinceImage.setFitHeight(provincePuzzle.getHeight());

                //拼图坐标设置
                PicturePuzzleGame.reset(100, 600, 100, 600); //拼图重置
                UIFunction.resetWin();

                provinceImage.setLayoutX(PicturePuzzleGame.getPosition(provinceImage.getId()).getX());
                provinceImage.setLayoutY(PicturePuzzleGame.getPosition(provinceImage.getId()).getY());

                provinceImage.setPickOnBounds(false);

                puzzleGroup.getChildren().addAll(provinceImage);

                UIAnimation.setBlackMask(provinceImage,
                        event1 -> {
                            UIFunction.setMousePuzzleTrans(provinceImage, puzzleGroup);},
                        100+300*Math.random());
            }

        }, false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.puzzleController = this;
    }
}
