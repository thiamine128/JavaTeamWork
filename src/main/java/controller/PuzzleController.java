package controller;

import game.PicturePuzzleGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ui.ProvinceInfoStruct;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The puzzle controller, corresponds to puzzleFrame.fxml.
 */
public class PuzzleController implements Initializable {

    /**
     * The constant puzzle province pane.
     */
//puzzleFrame.fxml 拼图界面
    public static Pane puzzleProvincePane; //省份载体
    /**
     * The puzzle group.
     */
    @FXML
    public Group puzzleGroup; //拼图载体
    /**
     * The puzzle background.
     */
    @FXML
    public ImageView puzzleCG; //拼图背景图片
    /**
     * The puzzle info.
     */
    @FXML
    public ImageView puzzleInfo; //拼图信息
    /**
     * The puzzle mask.
     */
    @FXML
    public ImageView puzzleMask; //拼图界面遮罩
    /**
     * The puzzle frame initializer.
     */
    public ImageView puzzleFrameIni; //初始化触发器
    /**
     * The puzzle cancel.
     */
    public ImageView puzzleCancel; //退出按键
    private List<ProvinceInfoStruct> provinceInfoSet = new ArrayList(); //省份信息列
    /**
     * The timer text.
     */
    public Text timerText;
    /**
     * The thread.
     */
    public Thread thread;
    /**
     * The timer.
     */
    public Long timer = 0L;

    /**
     * Puzzle frame trigger.
     */
    public void puzzleFrameTrigger(){

        puzzleFrameIni.setMouseTransparent(true);
        Image puzzleCGImage = new Image(getClass().getResourceAsStream("/puzzleImage/puzzleCG.jpg"));
        Image puzzleInfoImage = new Image(getClass().getResourceAsStream("/puzzleImage/puzzleInfoImage.png"));
        puzzleCG.setImage(puzzleCGImage); //设置背景图片
        puzzleInfo.setImage(puzzleInfoImage);
        puzzleInfo.setOpacity(0.0);
        puzzleMask.setMouseTransparent(true);
        puzzleMask.setOpacity(1.0);

        puzzleGroup.getChildren().clear();
        timer = 0L;
        timerText.setText("00:00");
        puzzleMask.setMouseTransparent(false);
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

                Image image = new Image(getClass().getResourceAsStream("/provinces/"+provincePuzzle.getName()+".png"));
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
            puzzleMask.setMouseTransparent(true);

        }, false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puzzleMask.setVisible(true);
        UIManager.puzzleController = this;
        TimerThread timerThread = new TimerThread();
        thread = new Thread(timerThread);
        thread.setDaemon(true);
        thread.start();
    }

}
