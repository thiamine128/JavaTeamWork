import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class UIFunction {
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
    public static void iniButton(Pane provincePane){
        iniProvinceY(provincePane); //初始化Y坐标
        //按钮功能设置
        for (Node province : provincePane.getChildren()){
            province.setPickOnBounds(false); //透明边界不触发
            //鼠标点击
            province.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    //按钮点击调用
                    System.out.println(province.getId());

                }
            });
            //鼠标进入
            province.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    //终止无效的进程
                    TranslateTransition transition = nodeToTransDown.get(province);
                    transition.stop();
                    transition = nodeToTransUp.get(province);
                    transition.stop();

                    //启动上升动画
                    double deltaY = nodeToY.get(province)-10-province.getTranslateY();
                    UIAnimation.buttonSimpleAnimation(province, transition, deltaY, true);

                    //颜色设计
                    ColorAdjust color_adjust = new ColorAdjust();
                    color_adjust.setHue(0.9);
                    color_adjust.setBrightness(0.4);
                    color_adjust.setContrast(0.8);
                    color_adjust.setSaturation(0.5);
                    province.setEffect(color_adjust);

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

                }
            });
        }
    }

}
