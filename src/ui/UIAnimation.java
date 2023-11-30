package ui;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.event.EventHandler;

public class UIAnimation {

    public static void setRotateAnimation(Node item0, double startAngle, double endAngle){
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(item0);
        rotateTransition.setDuration(Duration.millis(500));
        rotateTransition.setFromAngle(startAngle);
        rotateTransition.setToAngle(endAngle);
        rotateTransition.play();
    }

    public static void setMouseCircleAnimation(Group circleAnimationGroup){
        UIManager.mainScene.setOnMouseClicked(event ->{
            double centerX = event.getX();
            double centerY = event.getY();
            FadeTransition openTransition = new FadeTransition();
            FadeTransition fadeTransition = new FadeTransition();

            Circle circle = new Circle(centerX, centerY, 7);
            circle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            circleAnimationGroup.getChildren().addAll(circle);

            //出现动画
            openTransition.setCycleCount(1);
            openTransition.setFromValue(0.5);
            openTransition.setToValue(0.6);
            openTransition.setNode(circle);
            openTransition.setDuration(Duration.millis(30));
            openTransition.setOnFinished(event1 -> fadeTransition.play());
            openTransition.play();

            //设置消失动画
            fadeTransition.setCycleCount(1);
            fadeTransition.setFromValue(0.6);
            fadeTransition.setToValue(0);
            fadeTransition.setNode(circle);
            fadeTransition.setDuration(Duration.millis(150));
            fadeTransition.setOnFinished(event1 -> circleAnimationGroup.getChildren().removeAll(circle));
        });

    }

    public static void fadeAnimation(Node item0, EventHandler event, boolean controlDisable){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        fadeTransition.setFromValue(1.0);   // 设置起始透明度为1.0
        fadeTransition.setToValue(0.0);     // 设置结束透明度为0.0
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.setOnFinished(event);
        fadeTransition.play();          // 播放动画
        item0.setDisable(controlDisable);         //图片失活
    }

    public static void fadeAnimation(Node item0, EventHandler event, boolean controlDisable, double timeScale){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(timeScale));
        fadeTransition.setFromValue(1.0);   // 设置起始透明度为1.0
        fadeTransition.setToValue(0.0);     // 设置结束透明度为0.0
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.setOnFinished(event);
        fadeTransition.play();          // 播放动画
        item0.setDisable(controlDisable);         //图片失活
    }

    public static void sparkleAnimation(Node item0, FadeTransition transition){
        transition.setNode(item0);
        transition.setFromValue(0.0);
        transition.setToValue(0.5);
        transition.setDuration(Duration.millis(800));
        transition.setCycleCount(FadeTransition.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    public static void titleSparkleAnimation(Node item0, double fromValue, double toValue){
        FadeTransition transition = new FadeTransition();
        transition.setNode(item0);
        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        transition.setDuration(Duration.millis(800));
        transition.setCycleCount(FadeTransition.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    public static void titleSparkleAnimation(Node item0, double fromValue, double toValue, FadeTransition transition){
        transition.setNode(item0);
        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        transition.setDuration(Duration.millis(800));
        transition.setCycleCount(FadeTransition.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    public static void setBlackMask(Node item0, EventHandler event, double timeScale){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(timeScale));
        fadeTransition.setFromValue(0.0);   // 设置起始透明度为1.0
        fadeTransition.setToValue(1.0);     // 设置结束透明度为0.0
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.setOnFinished(event);
        fadeTransition.play();          // 播放动画
    }

    public static void setBlackMask(Node item0, EventHandler event, double timeScale, double fromValue, double toValue){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(timeScale));
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.setOnFinished(event);
        fadeTransition.play();          // 播放动画
    }

    public static void vectorMove(Node item0, double x0, double y0, double timeScale, EventHandler event){
        TranslateTransition transition = new TranslateTransition();
        transition.setByX(x0);
        transition.setByY(y0);
        transition.setNode(item0);
        transition.setCycleCount(1);
        transition.setDuration(Duration.millis(timeScale));
        transition.setOnFinished(event);
        transition.play();
    }

    public static void buttonSimpleAnimation(
            Node province, TranslateTransition transition, double paceLength, boolean cycleControl){
        transition.setByY(paceLength); //按钮向上偏移
        transition.setNode(province); //按钮实例
        if (cycleControl) {
            transition.setCycleCount(TranslateTransition.INDEFINITE); //循环播放
            transition.setDuration(Duration.millis(300)); //持续时间
        }
        else {
            transition.setCycleCount(1);
            transition.setDuration(Duration.millis(300)); //持续时间
        }
        transition.setAutoReverse(true); //自动翻转
        transition.setOnFinished(event->province.setEffect(null)); //还原按钮属性
        transition.play(); //播放
    }

    public static void buttonIniAnimation(Node province){
        TranslateTransition transition = new TranslateTransition();
        transition.setByY(-20); //按钮向上偏移
        transition.setNode(province); //按钮实例
        transition.setCycleCount(2);
        transition.setDuration(Duration.millis(100)); //持续时间
        transition.setAutoReverse(true); //自动翻转
        transition.play(); //播放
    }

    public static void buttonInfoImageAnimation(Node imageView, FadeTransition fadeTransition, boolean control){
        fadeTransition.stop();
        double imageOpacity = imageView.getOpacity();
        fadeTransition.setFromValue(imageOpacity);   // 设置起始透明度为图片透明度
        if (control) fadeTransition.setToValue(1.0); // 设置结束透明度为1.0
        else fadeTransition.setToValue(0.0); //设置结束透明度为0.0
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(imageView);  // 设置动画应用实例
        fadeTransition.play();          // 播放动画
    }

    //烟花动画：游戏胜利结算
    public static void puzzleFireworks(double centerX, double centerY, Group sceneGroup) {
        for (int i = 0; i < 1145; i++) {

            PathTransition transition = new PathTransition();
            FadeTransition fadeTransition = new FadeTransition();
            Circle circle = new Circle(centerX, centerY, 1.0+4.5 * Math.random()); //烟花单位元
            circle.setFill(Color.color(Math.random(), Math.random(), Math.random())); //颜色
            sceneGroup.getChildren().addAll(circle); //载入Group

            Path fireworksPath = new Path(new MoveTo(centerX, centerY),
                    new LineTo(centerX + 200 * Math.random(), centerY)); //设置运动轨迹
            fireworksPath.getTransforms().add(new Rotate(360 * Math.random(), centerX, centerY));

            //路径动画设置
            transition.setPath(fireworksPath);
            transition.setNode(circle);
            transition.setCycleCount(1);
            transition.setOnFinished(event1 -> fadeTransition.play());
            transition.setDuration(Duration.seconds(Math.random() + 0.5));
            transition.play();

            //消失动画设置
            fadeTransition.setCycleCount(1);
            fadeTransition.setToValue(0);
            fadeTransition.setNode(circle);
            fadeTransition.setDuration(Duration.millis(200));
            fadeTransition.setOnFinished(event1 ->
                    sceneGroup.getChildren().removeAll(circle)); //回收烟花单位元
        }
    }

    public static void setSparkleCircle(double centerX, double centerY, Group sceneGroup) {
        for (int i = 0; i < 500; i++) {

            PathTransition transition = new PathTransition();
            FadeTransition fadeTransition = new FadeTransition();
            Circle circle = new Circle(centerX, centerY, 1.0+1.5 * Math.random()); //烟花单位元
            circle.setFill(Color.color(1, 1, 1)); //颜色
            sceneGroup.getChildren().addAll(circle); //载入Group

            Path fireworksPath = new Path(new MoveTo(centerX, centerY),
                    new LineTo(centerX + 800 * Math.random(), centerY)); //设置运动轨迹
            fireworksPath.getTransforms().add(new Rotate(360 * Math.random(), centerX, centerY));

            //路径动画设置
            transition.setPath(fireworksPath);
            transition.setNode(circle);
            transition.setCycleCount(1);
            transition.setOnFinished(event1 -> fadeTransition.play());
            transition.setDuration(Duration.seconds(Math.random() + 0.5));
            transition.play();

            //消失动画设置
            fadeTransition.setCycleCount(1);
            fadeTransition.setToValue(0);
            fadeTransition.setNode(circle);
            fadeTransition.setDuration(Duration.millis(200));
            fadeTransition.setOnFinished(event1 ->
                    sceneGroup.getChildren().removeAll(circle)); //回收烟花单位元
        }
    }

    public static void setTextChangeAnimation(Node item0, EventHandler event, double timeScale, boolean control){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(timeScale));
        if (control){
            fadeTransition.setFromValue(0.1);
            fadeTransition.setToValue(0.8);
        }else{
            fadeTransition.setFromValue(0.8);
            fadeTransition.setToValue(0.1);
        }
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.setOnFinished(event);
        fadeTransition.play();          // 播放动画
    }

    public static void timer(double timeScale, EventHandler event){ //计时器
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(timeScale));
        fadeTransition.setCycleCount(1);
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(0.8);
        fadeTransition.setNode(new ImageView());
        fadeTransition.setOnFinished(event);
        fadeTransition.play();
    }

}
