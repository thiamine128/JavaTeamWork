import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class UIManager extends Application {

    public static void main(String [] args){ //主函数
        launch(args); //启动窗口
    }

    public UIManager() {
        System.out.println(this);
    }

    @Override
    public void start(Stage stage) throws Exception { //窗口初始化

        Parent root = FXMLLoader.load(getClass().getResource("mainFrame.fxml")); //加载窗口
        mainScene = new Scene(root, 800, 600); //设置大小
        mainStage = stage;
        stage.setTitle("TRAVEL ~Wonderful Everyday~"); //标题名称设置
        stage.setScene(mainScene);

        stage.setResizable(false); //窗口大小固定
        Image icon = new Image(getClass().getResourceAsStream("resources/icon.png"));
        stage.getIcons().add(icon); //设置ICON 暂定黑骑士
        stage.show(); //显示

    }

    public boolean changeScene(String fxmlFileName) throws IOException { //切换fxml场景
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
        if (root == null) return false; //没找到
        mainScene = new Scene(root, 800, 600); //建新场景
        mainStage.setScene(mainScene); //设定
        return true; //找到了
    }

    public static Scene mainScene; //记录当前场景
    public static Stage mainStage; //当前舞台实例
    public Pane openingPane; //开头场景模板
    public Pane provincePane; //各区域模板
    public Group circleAnimationGroup; //鼠标点击动画
    public ImageView mask;
    public void openingAnimation(){
        UIAnimation.setBlackMask(mask, event -> {
            setOpeningEnd();
            UIAnimation.fadeAnimation(mask);
        }); //遮罩动画
        setMouseCircleAnimation(); //鼠标特效设置
        UIFunction.iniButton(provincePane); //按钮设定
    }

    public void setOpeningEnd(){
        openingPane.setDisable(true);
        openingPane.setVisible(false);
    }

    public void setMouseCircleAnimation(){
        UIAnimation.setMouseCircleAnimation(circleAnimationGroup); //鼠标效果设置
    }

}