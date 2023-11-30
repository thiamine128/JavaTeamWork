import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.event.EventHandler;

public class UIAnimation {
    public static void setMouseCircleAnimation(Group circleAnimationGroup){
        UIManager.mainScene.setOnMouseClicked(event ->{
            double centerX = event.getX(), centerY = event.getY();

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

    public static void fadeAnimation(Node item0){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        fadeTransition.setFromValue(1.0);   // 设置起始透明度为1.0
        fadeTransition.setToValue(0.0);     // 设置结束透明度为0.0
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.play();          // 播放动画
        item0.setDisable(true);         //图片失活
    }

    public static void setBlackMask(Node item0, EventHandler event){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        fadeTransition.setFromValue(0.0);   // 设置起始透明度为1.0
        fadeTransition.setToValue(1.0);     // 设置结束透明度为0.0
        fadeTransition.setCycleCount(1); //只播放一次
        fadeTransition.setNode(item0);  // 设置动画应用实例
        fadeTransition.setOnFinished(event);
        fadeTransition.play();          // 播放动画
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

}
