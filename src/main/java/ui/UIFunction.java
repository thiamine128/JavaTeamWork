package ui;

import controller.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;
import province.ProvinceDetail;
import game.*;

/**
 * The UI function.
 */
public class UIFunction {

    /**
     * The constant manager.
     */
    public static UIManager manager;

    /**
     * Ini post frame.
     */
    public static void iniPostFrame(){

        manager.postController.postCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.postController.postFrameMask.setMouseTransparent(false);
                UIAnimation.setBlackMask(manager.postController.postFrameMask, event0 -> {
                    try {
                        manager.postController.postFrameIni.setMouseTransparent(false);
                        manager.toMainFrame(true);
                        manager.postController.postFrameMask.setMouseTransparent(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, 600);
            }
        });
        manager.postController.postCancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.postController.postCancel.setOpacity(0.6);
            }
        });
        manager.postController.postCancel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.postController.postCancel.setOpacity(0.8);
            }
        });
    }

    private static FadeTransition loginHintTrans = new FadeTransition();
    private static boolean sendCold = true;

    /**
     * Ini login frame button.
     *
     * @param loginController the login controller
     */
    public static void iniLoginFrameButton(LoginController loginController){

        ColorAdjust color_adjust = new ColorAdjust();
        color_adjust.setHue(0.7);
        color_adjust.setBrightness(0.4);
        color_adjust.setContrast(0.5);
        color_adjust.setSaturation(0.5);

        loginController.sendButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.sendButton.setEffect(color_adjust);
                loginController.loginHint.setText("发送邮件");
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, true);
            }
        });
        loginController.sendButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.sendButton.setEffect(null);
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, false);
            }
        });
        loginController.sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (sendCold){
                    sendCold = false;
                    UIAnimation.timer(10000, event -> sendCold = true);
                    UINetwork.sendEmail(loginController.emailInput.getText());
                }else loginController.loginHint.setText("操作过于频繁！");
            }
        });

        loginController.confirmButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.confirmButton.setEffect(color_adjust);
                LoginSituation situation = loginController.getLoginFrameSituation();
                switch (situation){
                    case LOGIN: loginController.loginHint.setText("确定进行登录"); break;
                    case REGISTER: loginController.loginHint.setText("确定进行注册"); break;
                    case RESET: loginController.loginHint.setText("确定找回密码"); break;
                }
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, true);
            }
        });
        loginController.confirmButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.confirmButton.setEffect(null);
                if (!loginController.loginPane.isMouseTransparent())
                    UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, false);
            }
        });
        //登录入口
        loginController.confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //登录？注册？

                switch (loginController.getLoginFrameSituation()){
                    case LOGIN: {
                        loginController.loginPane.setMouseTransparent(true);
                        loginController.loginHint.setText("发送请求中");
                        loginController.loginHint.setOpacity(0.8);
                        UINetwork.trylogin(loginController.usernameInput.getText(), loginController.passwordInput.getText());
                        break;
                    }
                    case REGISTER: {
                        loginController.loginPane.setMouseTransparent(true);
                        loginController.loginHint.setText("发送请求中");
                        loginController.loginHint.setOpacity(0.8);
                        UINetwork.tryRegister(loginController.usernameInput.getText(), loginController.passwordInput.getText(),
                                loginController.emailInput.getText(), loginController.codeInput.getText());
                        break;
                    }
                    case RESET:{
                        loginController.loginPane.setMouseTransparent(true);
                        loginController.loginHint.setText("发送请求中");
                        loginController.loginHint.setOpacity(0.8);
                        UINetwork.resetPassword(loginController.emailInput.getText(), loginController.codeInput.getText(),
                                loginController.passwordInput.getText());
                        break;
                    }
                }
            }
        });

        loginController.regButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.regButton.setEffect(color_adjust);
                loginController.loginHint.setText("进入注册页面");
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, true);
            }
        });
        loginController.regButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.regButton.setEffect(null);
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, false);
            }
        });
        loginController.regButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.setSituation(LoginSituation.REGISTER);
            }
        });

        loginController.logButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.logButton.setEffect(color_adjust);
                loginController.loginHint.setText("进入登录页面");
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, true);
            }
        });
        loginController.logButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.logButton.setEffect(null);
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, false);
            }
        });
        loginController.logButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.setSituation(LoginSituation.LOGIN);
            }
        });

        loginController.resetButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.resetButton.setEffect(color_adjust);
                loginController.loginHint.setText("找回密码");
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, true);
            }
        });
        loginController.resetButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.resetButton.setEffect(null);
                UIAnimation.buttonInfoImageAnimation(loginController.loginHint, loginHintTrans, false);
            }
        });
        loginController.resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginController.setSituation(LoginSituation.RESET);
            }
        });

    }

    private static double mouseX, mouseY;
    private static boolean mouseKey = false, isWin = false;

    /**
     * Check win boolean.
     *
     * @return the boolean
     */
    public static boolean checkWin(){
        return isWin;
    }

    /**
     * Reset win.
     */
    public static void resetWin(){
        isWin = false;
    }

    /**
     * Set mouse puzzle trans.
     *
     * @param provinceImage the province image
     * @param puzzleGroup   the puzzle group
     */
    public static void setMousePuzzleTrans(ImageView provinceImage, Group puzzleGroup){

        mouseKey = false;

        provinceImage.setOnMouseEntered(event->{

            ColorAdjust color_adjust = new ColorAdjust();
            color_adjust.setHue(0.9);
            color_adjust.setBrightness(0.4);
            color_adjust.setContrast(0.8);
            color_adjust.setSaturation(0.5);
            provinceImage.setEffect(color_adjust);

        });


        manager.puzzleController.puzzleCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.puzzleController.puzzleMask.setMouseTransparent(false);
                UIAnimation.setBlackMask(manager.puzzleController.puzzleMask, event0 -> {
                    try {
                        manager.puzzleController.puzzleFrameIni.setMouseTransparent(false);
                        manager.toMainFrame(true);
                        manager.puzzleController.puzzleMask.setMouseTransparent(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, 600);
            }
        });
        manager.puzzleController.puzzleCancel.setOnMouseEntered(event->{
            ColorAdjust color_adjust = new ColorAdjust();
            color_adjust.setHue(0.9);
            color_adjust.setBrightness(0.4);
            color_adjust.setContrast(0.8);
            color_adjust.setSaturation(0.5);
            manager.puzzleController.puzzleCancel.setEffect(color_adjust);
        });
        manager.puzzleController.puzzleCancel.setOnMouseExited(event->manager.puzzleController.puzzleCancel.setEffect(null));

        provinceImage.setOnMouseExited(event->{
            provinceImage.setEffect(null);
            mouseKey = false;
            if (!isWin){
                isWin = PicturePuzzleGame.check();
                if (isWin) {
                    System.out.println("Win!"); //debug
                    for (int i=1;i<=3;i++){
                        UIAnimation.puzzleFireworks(//横坐标、纵坐标、场景组
                                50+900*Math.random(), 50+700*Math.random(), puzzleGroup, true);
                    }
                    try {
                        UINetwork.setPuzzleWin(UIManager.puzzleController.timer);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    UIAnimation.timer(5000, event1 -> {
                        manager.puzzleController.puzzleMask.setMouseTransparent(false);
                        UIAnimation.setBlackMask(manager.puzzleController.puzzleMask, event0 -> {
                            try {
                                manager.puzzleController.puzzleFrameIni.setMouseTransparent(false);
                                manager.toMainFrame(true);
                                manager.puzzleController.puzzleMask.setMouseTransparent(true);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }, 600);
                    });

                }
            }
        });

        provinceImage.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseKey = false;
                }
            }
        );

        provinceImage.setOnMouseDragged(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (!isWin){
                        double mouseNewX = event.getScreenX();
                        double mouseNewY = event.getScreenY();

                        if (!mouseKey)
                            mouseKey = true;
                        else{
                            //移动
                            double deltaX = mouseNewX - mouseX;
                            double deltaY = mouseNewY - mouseY;
                            provinceImage.setLayoutX(provinceImage.getLayoutX()+deltaX);
                            provinceImage.setLayoutY(provinceImage.getLayoutY()+deltaY);
                            PicturePuzzleGame.changePosition(provinceImage.getId(),
                                    provinceImage.getLayoutX(), provinceImage.getLayoutY());
                        }

                        mouseX = mouseNewX;
                        mouseY = mouseNewY;
                    }

                }
            }
        );

        provinceImage.setOnMouseDragReleased(
            new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent mouseDragEvent) {
                    mouseKey = false;
                    System.out.println("The drag is over");
                }
            }
        );

    }

    private static Map<Node, Double> nodeToY = new HashMap<>();
    private static void iniProvinceY(Pane provincePane){
        for (Node province : provincePane.getChildren()){
            //记录每一个省的Y坐标
            nodeToY.put(province, province.getTranslateY());
            nodeToTransUp.put(province, new TranslateTransition());
            nodeToTransDown.put(province, new TranslateTransition());
        }
    }

    private static Map<Node, TranslateTransition> nodeToTransUp = new HashMap<>();
    private static Map<Node, TranslateTransition> nodeToTransDown = new HashMap<>();
    private static FadeTransition infoImageFadeTransition = new FadeTransition();
    private static FadeTransition edgeImageFadeTransition = new FadeTransition();

    /**
     * Info fade.
     */
    public static void infoFade(){
        UIAnimation.buttonInfoImageAnimation(UIManager.mainController.infoImage, infoImageFadeTransition, false);
    }

    /**
     * Ini main frame button.
     *
     * @param provincePane   the province pane
     * @param infoImage      the info image
     * @param mainController the main controller
     */
    public static void iniMainFrameButton(Pane provincePane, ImageView infoImage, MainController mainController){

        infoImage.setOpacity(0.0);
        iniProvinceY(provincePane); //初始化Y坐标
        mainController.toPuzzleButton.setPickOnBounds(false);
        mainController.toPostButton.setPickOnBounds(false);

        mainController.HButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(mainController.HButton, 0, 360);
                if (!mainController.Hsituation){
                    Image image = new Image(getClass().getResourceAsStream("/infoImage/path.png"));
                    infoImage.setImage(image); //设置艺术字信息
                    UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, true);
                }
            }
        });
        mainController.HButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, false);
            }
        });

        mainController.toQuestionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(mainController.toQuestionButton, 0, 360);
                if (!mainController.Hsituation){
                    Image image = new Image(getClass().getResourceAsStream("/infoImage/question.png"));
                    infoImage.setImage(image); //设置艺术字信息
                    UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, true);
                }
            }
        });
        mainController.toQuestionButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, false);
            }
        });

        mainController.toPostButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(mainController.toPostButton, 0, 360);
                if (!mainController.Hsituation){
                    Image image = new Image(getClass().getResourceAsStream("/infoImage/post.png"));
                    infoImage.setImage(image); //设置艺术字信息
                    UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, true);
                }
            }
        });
        mainController.toPostButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, false);
            }
        });

        mainController.toPostButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //进入帖子界面
                mainController.mask.setMouseTransparent(false);
                UIAnimation.setBlackMask(mainController.mask, event -> {
                    mainController.loadTitle.setText("旅行论坛");
                    mainController.loadText.setText("在这里与全国的旅行者进行交流！");
                    mainController.loadText.setOpacity(0.0);
                    mainController.loadTitle.setOpacity(0.0);
                    mainController.rec.setLayoutX(mainController.rec.getLayoutX()-1200);
                    UIAnimation.setBlackMask(mainController.loadPane, event1 -> {
                        UIAnimation.setBlackMask(mainController.loadTitle, null, 300, 0.0, 0.8);
                        UIAnimation.vectorMove(mainController.rec, 1200, 0, 300, null);
                        UIAnimation.setBlackMask(mainController.loadText, event4->{
                            UIAnimation.vectorMove(mainController.loadKey, 400, 0, 3000, event2 -> {
                                UIAnimation.vectorMove(mainController.loadKey, -400, 0, 10, null);
                                UIAnimation.fadeAnimation(mainController.loadPane, event3 -> {
                                    try {
                                        mainController.mainFrameIni.setMouseTransparent(false);
                                        mainController.mask.setMouseTransparent(true);
                                        manager.toPostFrame();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }, false, 100);
                            });
                        }, 300, 0.0, 0.8);
                    }, 300);
                }, 300);
            }
        });

        mainController.toPuzzleButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(mainController.toPuzzleButton, 0, 360);
                if (!mainController.Hsituation){
                    Image image = new Image(getClass().getResourceAsStream("/infoImage/puzzle.png"));
                    infoImage.setImage(image); //设置艺术字信息
                    UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, true);
                }

            }
        });
        mainController.toPuzzleButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, false);
            }
        });


        mainController.toPuzzleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainController.mask.setMouseTransparent(false);
                UIAnimation.setBlackMask(mainController.mask, event -> {
                    mainController.loadTitle.setText("地理拼图");
                    mainController.loadText.setText("识别省份轮廓，还原整个中国版图");
                    mainController.loadText.setOpacity(0.0);
                    mainController.loadTitle.setOpacity(0.0);
                    mainController.rec.setLayoutX(mainController.rec.getLayoutX()-1200);
                    UIAnimation.setBlackMask(mainController.loadPane, event1 -> {
                        UIAnimation.setBlackMask(mainController.loadTitle, null, 300, 0.0, 0.8);
                        UIAnimation.vectorMove(mainController.rec, 1200, 0, 300, null);
                        UIAnimation.setBlackMask(mainController.loadText, event4->{
                            UIAnimation.vectorMove(mainController.loadKey, 400, 0, 3000, event2 -> {
                                UIAnimation.vectorMove(mainController.loadKey, -400, 0, 10, null);
                                UIAnimation.fadeAnimation(mainController.loadPane, event3 -> {
                                    try {
                                        mainController.mainFrameIni.setMouseTransparent(false);
                                        mainController.mask.setMouseTransparent(true);
                                        manager.toPuzzleFrame();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }, false, 100);
                            });
                        }, 300, 0.0, 0.8);
                    }, 300);
                }, 300);
            }
        });

        //按钮功能设置
        for (Node province : provincePane.getChildren()){
            province.setPickOnBounds(false); //透明边界不触发
            //鼠标点击
            province.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    //按钮点击调用
                    ProvinceDetail provinceDetail = ProvinceDetail.getDetail(province.getId());
                    ProvinceController.detail = provinceDetail;
                    ProvinceController.provinceName = province.getId();

                    mainController.mask.setMouseTransparent(false);
                    UIAnimation.setBlackMask(mainController.mask, event1 -> {
                        try {
                            mainController.mainFrameIni.setMouseTransparent(false);
                            mainController.mask.setMouseTransparent(true);
                            manager.toProvinceFrame();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }, 600);

                }
            });
            //鼠标进入
            province.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    String provinceName = province.getId();

                    //终止无效的进程
                    TranslateTransition transition = nodeToTransDown.get(province);
                    transition.stop();
                    transition = nodeToTransUp.get(province);
                    transition.stop();

                    if (!provinceName.equals("xianggang") && !provinceName.equals("aomen")){
                        //启动上升动画
                        double deltaY = nodeToY.get(province)-10-province.getTranslateY();
                        UIAnimation.buttonSimpleAnimation(province, transition, deltaY, true);
                    }

                    //颜色设计
                    ColorAdjust color_adjust = new ColorAdjust();
                    color_adjust.setHue(0.9);
                    color_adjust.setBrightness(0.4);
                    color_adjust.setContrast(0.8);
                    color_adjust.setSaturation(0.5);
                    province.setEffect(color_adjust);

                    Image image = new Image(getClass().getResource("/infoImage/" + provinceName + ".png").toExternalForm());
                    infoImage.setImage(image); //设置艺术字信息
                    UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, true);

                    Image image1 = new Image(getClass().getResource("/provinces/" + provinceName + ".png").toExternalForm());
                    double iniw = image1.getWidth(), inih = image1.getHeight();
                    UIManager.mainController.provinceEdge.setImage(image1);
                    double preFitHeight = UIManager.mainController.provinceEdge.getFitHeight();
                    UIManager.mainController.provinceEdge.setFitHeight(
                            UIManager.mainController.provinceEdge.getFitWidth()*inih/iniw
                    );
                    UIManager.mainController.provinceEdge.setLayoutY(
                            UIManager.mainController.provinceEdge.getLayoutY() -
                                    (UIManager.mainController.provinceEdge.getFitHeight()-preFitHeight)
                    );

                    UIAnimation.buttonInfoImageAnimation(UIManager.mainController.provinceEdge,
                            edgeImageFadeTransition, true);

                }
            });
            //鼠标退出
            province.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    //终止无效的进程
                    TranslateTransition transition = nodeToTransUp.get(province);
                    transition.stop();
                    transition = nodeToTransDown.get(province);
                    transition.stop();

                    //启动下降动画
                    double deltaY = nodeToY.get(province)-province.getTranslateY();
                    UIAnimation.buttonSimpleAnimation(province, transition, deltaY, false);

                    //关闭艺术字
                    UIAnimation.buttonInfoImageAnimation(infoImage, infoImageFadeTransition, false);
                    UIAnimation.buttonInfoImageAnimation(UIManager.mainController.provinceEdge,
                            edgeImageFadeTransition, false);

                }
            });
        }
    }

    private static FadeTransition sparklesFadeTransition = new FadeTransition();
    private static FadeTransition foodTrans = new FadeTransition();
    private static FadeTransition interestTrans = new FadeTransition();
    private static FadeTransition folkTrans = new FadeTransition();
    private static FadeTransition postTrans = new FadeTransition();

    /**
     * Ini province frame button.
     *
     * @param postButton   the post button
     * @param sparkleImage the sparkle image
     * @param BackButton   the back button
     */
    public static void iniProvinceFrameButton(Node postButton, Node sparkleImage, Node BackButton){

        sparkleImage.setMouseTransparent(true);

        manager.provinceController.foodTitle.setOpacity(0.0);
        manager.provinceController.interestTitle.setOpacity(0.0);
        manager.provinceController.folkTitle.setOpacity(0.0);
        manager.provinceController.postTitle.setOpacity(0.0);

        postButton.setPickOnBounds(true);

        postButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    manager.toEditorFrame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        postButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent mouseEvent) {
                 UIAnimation.setRotateAnimation(postButton, 0, -30);
                 UIAnimation.sparkleAnimation(sparkleImage, sparklesFadeTransition);
                 UIAnimation.buttonInfoImageAnimation(manager.provinceController.postTitle, postTrans, true);
             }
         });

        postButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.setRotateAnimation(postButton, -30, 0);
                sparklesFadeTransition.stop();
                UIAnimation.fadeAnimation(sparkleImage, null, false);
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.postTitle, postTrans, false);
            }
        });

        BackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent mouseEvent) {
                 manager.provinceController.provinceFrameMask.setMouseTransparent(false);
                 UIAnimation.setBlackMask(manager.provinceController.provinceFrameMask, event -> {
                     try {
                         manager.provinceController.provinceFrameIni.setMouseTransparent(false);
                         manager.toMainFrame(true);
                         manager.provinceController.provinceFrameMask.setMouseTransparent(true);
                     } catch (Exception e) {
                         throw new RuntimeException(e);
                     }
                 }, 600);

             }
        });

        ColorAdjust color_adjust = new ColorAdjust();
        color_adjust.setHue(0.9);
        color_adjust.setBrightness(0.4);
        color_adjust.setContrast(0.8);
        color_adjust.setSaturation(0.3);

        manager.provinceController.foodButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.foodTitle, foodTrans, true);
                manager.provinceController.foodButton.setEffect(color_adjust);
            }
        });
        manager.provinceController.foodButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.foodTitle, foodTrans, false);
                manager.provinceController.foodButton.setEffect(null);
            }
        });
        manager.provinceController.foodButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.changeType(1);
            }
        });

        manager.provinceController.interestButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.interestTitle, interestTrans, true);
                manager.provinceController.interestButton.setEffect(color_adjust);
            }
        });
        manager.provinceController.interestButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.interestTitle, interestTrans, false);
                manager.provinceController.interestButton.setEffect(null);
            }
        });
        manager.provinceController.interestButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.changeType(2);
            }
        });

        manager.provinceController.folkButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.folkTitle, folkTrans, true);
                manager.provinceController.folkButton.setEffect(color_adjust);
            }
        });
        manager.provinceController.folkButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIAnimation.buttonInfoImageAnimation(manager.provinceController.folkTitle, folkTrans, false);
                manager.provinceController.folkButton.setEffect(null);
            }
        });
        manager.provinceController.folkButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.changeType(3);
            }
        });

        manager.provinceController.frButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.frButton.setEffect(color_adjust);
            }
        });
        manager.provinceController.frButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.frButton.setEffect(null);
            }
        });
        manager.provinceController.frButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.tryFr();
            }
        });

        manager.provinceController.nxtButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.nxtButton.setEffect(color_adjust);
            }
        });
        manager.provinceController.nxtButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.nxtButton.setEffect(null);
            }
        });
        manager.provinceController.nxtButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                manager.provinceController.tryNxt();
            }
        });

    }

}
