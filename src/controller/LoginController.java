package controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ui.AudioManager;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ImageView loginFrameIni; //登录界面触发器
    public ImageView startHint; //初始界面提示
    public ImageView loginMainTitle, loginMainTitleWhite; //总标题
    public AnchorPane loginPane; //登录输入端
    public ImageView confirmButton; //确认按键
    public ImageView reglogButton;
    public Text loginHint, reglogTitle;
    public TextField usernameInput, passwordInput;
    public int loginFrameSituation = 1; //1->登录 2->注册
    public ImageView loginFrameMask; //登录界面遮罩
    private static boolean loginFrameBoolean = true;
    private FadeTransition startHintTrans = new FadeTransition();

    public void loginFrameTrigger(){
        if (loginFrameBoolean){
            UIAnimation.titleSparkleAnimation(startHint, 0, 0.8, startHintTrans);
            loginFrameBoolean = false;
        }
    }

    public void clickStartHint(){
        loginFrameIni.setMouseTransparent(true);
        startHintTrans.stop();
        UIAnimation.fadeAnimation(startHint, null, false);
        UIAnimation.vectorMove(loginMainTitle, 0, -100, 700, event0->{
            UIAnimation.setBlackMask(loginPane, null, 500);
            UIFunction.iniLoginFrameButton(this);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.loginController = this;
        AudioManager.setBGMusic(1);
    }

}
