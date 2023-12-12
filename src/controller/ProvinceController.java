package controller;

import Click.ProvinceDetail;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ProvinceController implements Initializable {

    //provinceFrame.fxml 具体身份界面
    public Group provinceFrameGroup; //表面实例组
    public Rectangle maskRectangle; //图片设定矩形
    public ImageView sparkles; //相机上闪光特效
    public ImageView postButton, postTitle; //发帖按钮
    public ImageView provinceFrameMask; //黑色遮罩
    public ImageView provinceFrameBackButton; //返回按钮
    public ImageView provinceFrameIni; //初始化触发器
    public static String provinceName; //目前的身份名称
    public static ProvinceDetail detail; //身份信息实例
    public Text titleText, contentText; //下方文本标题、介绍
    public ImageView provinceFrameTitle; //具体省份界面左上标题
    public ImageView frButton, nxtButton; //向前向后按键
    public ImageView foodTitle, interestTitle, folkTitle; //三种类型右标题
    public ImageView foodButton, interestButton, folkButton; //三种类型按钮 (1)、(2)、(3)
    public ImageView mainImage; //照片墙
    public void provinceFrameTrigger(){ //具体省份界面初始化

        UIAnimation.fadeAnimation(provinceFrameMask, null, false);
        UIFunction.iniProvinceFrameButton(postButton, sparkles, provinceFrameBackButton);
        UIAnimation.titleSparkleAnimation(provinceFrameTitle, 0.5, 0.8);

        textPos = 1; textType = 1;
        setText(detail.foodName[textPos], detail.food[textPos]);
        setMainImage(detail.foodPath[textPos]);
        provinceFrameIni.setMouseTransparent(true);

    }

    public void changeType(int type0){
        if (type0 <= 3 && type0 >= 1){
            textType = type0;
            textPos = 1;
            changeTextPre();
        }
    }

    private int textPos = 1, textType = 1;
    public void tryFr(){
        if (textPos > 1){
            textPos--;
            changeTextPre();
        }
    }

    public void tryNxt(){
        int eind = 0;
        switch (textType){
            case 1: eind = detail.foodSum; break;
            case 2: eind = detail.interestSum; break;
            case 3: eind = detail.folkSum; break;
        }
        if (textPos < eind){
            textPos++;
            changeTextPre();
        }
    }

    private void changeTextPre(){
        UIAnimation.vectorMove(titleText, -10, 0, 600, null);
        UIAnimation.vectorMove(contentText, -10, 0, 600, null);
        UIAnimation.vectorMove(mainImage, -5, 0, 600, null);
        UIAnimation.setTextChangeAnimation(mainImage, null, 600, false);
        UIAnimation.setTextChangeAnimation(titleText, null, 600, false);
        UIAnimation.setTextChangeAnimation(contentText, event -> {
            changeText();
            changeMainImage();
            UIAnimation.setTextChangeAnimation(titleText, null, 600, true);
            UIAnimation.setTextChangeAnimation(contentText, null, 600, true);
            UIAnimation.setTextChangeAnimation(mainImage, null, 600, true);
            UIAnimation.vectorMove(mainImage, 5, 0, 600, null);
            UIAnimation.vectorMove(titleText, 10, 0, 600, null);
            UIAnimation.vectorMove(contentText, 10, 0, 600, null);
        }, 600, false);
    }

    private void changeMainImage(){
        switch (textType){
            case 1: setMainImage(detail.foodPath[textPos]); break;
            case 2: setMainImage(detail.interestPath[textPos]); break;
            case 3: setMainImage(detail.folkPath[textPos]); break;
        }
    }

    private void changeText(){
        switch (textType){
            case 1: setText(detail.foodName[textPos], detail.food[textPos]); break;
            case 2: setText(detail.interestName[textPos], detail.interest[textPos]); break;
            case 3: setText(detail.folkName[textPos], detail.folk[textPos]); break;
        }
    }

    private void setText(String titleString, String contentString){ //文字显示设置
        int sind = titleString.indexOf('.');
        titleText.setText(titleString.substring(sind+1));
        contentText.setText(contentString);
    }

    private void setMainImage(String path0){
        try {
            Random r = new Random();
            mainImage.setImage(new Image(path0+""+(r.nextInt(2)+1)+".jpg"));
            System.out.println(path0);
        }catch (Exception e){
             mainImage.setImage(new Image("./resources/mask.jpg"));
             e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.provinceController = this;
        provinceFrameMask.setVisible(true);
        mainImage.setPreserveRatio(false);
    }
}
