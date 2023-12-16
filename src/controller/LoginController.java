package controller;

import javafx.animation.FadeTransition;
import javafx.css.Style;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ui.AudioManager;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ImageView loginFrameIni, loginBG; //登录界面触发器
    public ImageView startHint; //初始界面提示
    public ImageView loginMainTitle, loginMainTitleWhite; //总标题
    public AnchorPane loginPane; //登录输, 入端
    public ImageView confirmButton; //确认按键
    public ImageView regButton, logButton, resetButton;
    public Text loginHint, reglogTitle, lemail, lcode;
    public TextField usernameInput, passwordInput, emailInput, codeInput;
    private LoginSituation loginFrameSituation = LoginSituation.NULL;
    public ImageView loginFrameMask; //登录界面遮罩
    private static boolean loginFrameBoolean = true;
    private FadeTransition startHintTrans = new FadeTransition();
    public ImageView sendButton;

    public LoginSituation getLoginFrameSituation(){
        return loginFrameSituation;
    }

    public void setSituation(LoginSituation situation){
        if (situation != loginFrameSituation){
            LoginSituation fr = loginFrameSituation;
            loginFrameSituation = situation;
            loginPane.setMouseTransparent(true);
            UIAnimation.timer(2000, event -> loginPane.setMouseTransparent(false));
            switch (situation){
                case LOGIN:{
                    UIAnimation.setTextChangeAnimation(reglogTitle, event1 -> {
                        reglogTitle.setText("LOGIN");
                        Font font = reglogTitle.getFont();
                        reglogTitle.setFont(Font.font(font.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 36));
                        UIAnimation.setTextChangeAnimation(reglogTitle, event -> {
                        }, 600, true);
                    }, 600, false);
                    UIAnimation.vectorMove(reglogTitle, 0, 122, 600, null);
                    if (fr != LoginSituation.NULL)
                        UIAnimation.setBlackMask(loginMainTitle, null, 600, 0.0, 0.8);
                    usernameInput.setPromptText("请输入用户名或邮箱");
                    passwordInput.setPromptText("请输入登录密码");
                    UIAnimation.fadeAnimation(lcode, null, false, 300);
                    UIAnimation.fadeAnimation(lemail, null, false, 300);
                    UIAnimation.fadeAnimation(codeInput, null, false, 300);
                    UIAnimation.fadeAnimation(emailInput, null, false, 300);
                    UIAnimation.fadeAnimation(sendButton, null, false, 300);
                    lcode.setMouseTransparent(true);
                    lemail.setMouseTransparent(true);
                    codeInput.setMouseTransparent(true);
                    emailInput.setMouseTransparent(true);
                    sendButton.setMouseTransparent(true);
                    break;
                }
                case RESET:{
                    UIAnimation.setTextChangeAnimation(reglogTitle, event1 -> {
                        reglogTitle.setText("RETRIEVE");
                        Font font = reglogTitle.getFont();
                        reglogTitle.setFont(Font.font(font.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 45));
                        UIAnimation.setTextChangeAnimation(reglogTitle, event -> {
                        }, 600, true);
                    }, 600, false);
                    usernameInput.setPromptText("请输入对应用户名");
                    passwordInput.setPromptText("请输入新密码");
                    emailInput.setPromptText("请输入你的邮箱");
                    codeInput.setPromptText("请输入验证码");
                    if (fr == LoginSituation.LOGIN){
                        UIAnimation.setBlackMask(loginMainTitle, null, 600, 0.8, 0.0);
                        UIAnimation.vectorMove(reglogTitle, 0, -122, 600, null);
                        UIAnimation.setBlackMask(lcode, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(lemail, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(codeInput, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(emailInput, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(sendButton, null, 300, 0.0, 0.8);
                        lcode.setMouseTransparent(false);
                        lemail.setMouseTransparent(false);
                        codeInput.setMouseTransparent(false);
                        emailInput.setMouseTransparent(false);
                        sendButton.setMouseTransparent(false);
                    }
                    break;
                }
                case REGISTER:{
                    UIAnimation.setTextChangeAnimation(reglogTitle, event1 -> {
                        reglogTitle.setText("REGISTER");
                        Font font = reglogTitle.getFont();
                        reglogTitle.setFont(Font.font(font.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 45));
                        UIAnimation.setTextChangeAnimation(reglogTitle, event -> {
                        }, 600, true);
                    }, 600, false);
                    usernameInput.setPromptText("请输入用户名");
                    passwordInput.setPromptText("请输入密码");
                    emailInput.setPromptText("请输入邮箱");
                    codeInput.setPromptText("请输入验证码");
                    if (fr == LoginSituation.LOGIN){
                        UIAnimation.setBlackMask(loginMainTitle, null, 600, 0.8, 0.0);
                        UIAnimation.vectorMove(reglogTitle, 0, -122, 600, null);

                        UIAnimation.setBlackMask(lcode, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(lemail, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(codeInput, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(emailInput, null, 300, 0.0, 0.8);
                        UIAnimation.setBlackMask(sendButton, null, 300, 0.0, 0.8);
                        lcode.setMouseTransparent(false);
                        lemail.setMouseTransparent(false);
                        codeInput.setMouseTransparent(false);
                        emailInput.setMouseTransparent(false);
                        sendButton.setMouseTransparent(false);
                    }

                    break;
                }
            }
        }
    }




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
        UIAnimation.vectorMove(loginMainTitle, 0, -80, 700, event0->{
            UIAnimation.setBlackMask(loginPane, null, 500);
            UIFunction.iniLoginFrameButton(this);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginMainTitle.setVisible(true);
        UIManager.loginController = this;
        AudioManager.setBGMusic(1);
        loginBG.setImage(new Image("./resources/loginImage/bg1.jpg"));
        loginPane.setOpacity(0.0);
        setSituation(LoginSituation.LOGIN);
    }

}

