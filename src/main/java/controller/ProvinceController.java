package controller;

import province.ProvinceDetail;
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

/**
 * The type Province controller.
 */
public class ProvinceController implements Initializable {

    /**
     * The Province bg.
     */
//provinceFrame.fxml 具体身份界面
    public ImageView provinceBG;
    /**
     * The Province frame group.
     */
    public Group provinceFrameGroup; //表面实例组
    /**
     * The Mask rectangle.
     */
    public Rectangle maskRectangle; //图片设定矩形
    /**
     * The Sparkles.
     */
    public ImageView sparkles; //相机上闪光特效
    /**
     * The Post button.
     */
    public ImageView postButton, /**
     * The Post title.
     */
    postTitle; //发帖按钮
    /**
     * The Province frame mask.
     */
    public ImageView provinceFrameMask; //黑色遮罩
    /**
     * The Province frame back button.
     */
    public ImageView provinceFrameBackButton; //返回按钮
    /**
     * The Province frame ini.
     */
    public ImageView provinceFrameIni; //初始化触发器
    /**
     * The constant provinceName.
     */
    public static String provinceName; //目前的身份名称
    /**
     * The constant detail.
     */
    public static ProvinceDetail detail; //身份信息实例
    /**
     * The Title text.
     */
    public Text titleText, /**
     * The Content text.
     */
    contentText; //下方文本标题、介绍
    /**
     * The Province frame title.
     */
    public ImageView provinceFrameTitle; //具体省份界面左上标题
    /**
     * The Fr button.
     */
    public ImageView frButton, /**
     * The Nxt button.
     */
    nxtButton; //向前向后按键
    /**
     * The Food title.
     */
    public ImageView foodTitle, /**
     * The Interest title.
     */
    interestTitle, /**
     * The Folk title.
     */
    folkTitle; //三种类型右标题
    /**
     * The Food button.
     */
    public ImageView foodButton, /**
     * The Interest button.
     */
    interestButton, /**
     * The Folk button.
     */
    folkButton; //三种类型按钮 (1)、(2)、(3)
    /**
     * The Main image.
     */
    public ImageView mainImage; //照片墙

    /**
     * Province frame trigger.
     */
    public void provinceFrameTrigger(){ //具体省份界面初始化

        Random r = new Random();
        int y = Math.abs(r.nextInt())%6 + 1;
        System.out.println("image: "+y);
        provinceBG.setImage(new Image(getClass().getResourceAsStream("/provinceFrameImage/bg"+y+".jpg")));
        provinceFrameMask.setMouseTransparent(false);
        UIAnimation.fadeAnimation(provinceFrameMask, event -> {
            provinceFrameMask.setMouseTransparent(true);
        }, false);
        UIFunction.iniProvinceFrameButton(postButton, sparkles, provinceFrameBackButton);
        UIAnimation.titleSparkleAnimation(provinceFrameTitle, 0.5, 0.8);

        textPos = 1; textType = 1;
        setText(detail.foodName[textPos], detail.food[textPos]);
        setMainImage(detail.foodPath[textPos]);
        provinceFrameIni.setMouseTransparent(true);

    }

    /**
     * Change type.
     *
     * @param type0 the type 0
     */
    public void changeType(int type0){
        if (type0 <= 3 && type0 >= 1){
            textType = type0;
            textPos = 1;
            changeTextPre();
        }
    }

    private int textPos = 1, textType = 1;

    /**
     * Try fr.
     */
    public void tryFr(){
        if (textPos > 1){
            textPos--;
            changeTextPre();
        }
    }

    /**
     * Try nxt.
     */
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
            mainImage.setImage(new Image(getClass().getResourceAsStream(path0+""+(r.nextInt(2)+1)+".jpg")));
            System.out.println(path0);
        }catch (Exception e){
             mainImage.setImage(new Image(getClass().getResourceAsStream("/mask.jpg")));
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
