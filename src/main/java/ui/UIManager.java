package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import controller.*;
import game.*;
import post.LanguageTool;

/**
 * The UI manager, the most important part of this software.
 */
public class UIManager extends Application {
    /**
     * The constant instance.
     */
    public static UIManager instance;

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String [] args){ //主函数
        launch(UIManager.class); //启动窗口
    }

    private void setNetworkManager(){
        instance = this;
        UIFunction.manager = this;
        AppClientEventHandler.manager = this;
    }

    private void fxmlFileLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Parent loginFrameParent = loader.load(getClass().getResource("/fxmlFile/loginFrame.fxml"));
        loginFrameScene = new Scene(loginFrameParent);

        Parent editorFrameParent = loader.load(getClass().getResource("/fxmlFile/editorFrame.fxml"));
        editorFrameScene = new Scene(editorFrameParent);

        Parent mainFrameParent = loader.load(getClass().getResource("/fxmlFile/mainFrame.fxml"));
        mainFrameScene = new Scene(mainFrameParent);

        Parent postFrameParent = loader.load(getClass().getResource("/fxmlFile/postFrame.fxml"));
        postFrameScene = new Scene(postFrameParent);

        Parent postViewFrameParent = loader.load(getClass().getResource("/fxmlFile/postViewFrame.fxml"));
        postViewFrameScene = new Scene(postViewFrameParent);

        Parent provinceFrameParent = loader.load(getClass().getResource("/fxmlFile/provinceFrame.fxml"));
        provinceFrameScene = new Scene(provinceFrameParent);

        Parent puzzleFrameParent = loader.load(getClass().getResource("/fxmlFile/puzzleFrame.fxml"));
        puzzleFrameScene = new Scene(puzzleFrameParent);

        Parent personFrameParent = loader.load(getClass().getResource("/fxmlFile/personFrame.fxml"));
        personFrameScene = new Scene(personFrameParent);

        Parent questionFrameParent = loader.load(getClass().getResource("/fxmlFile/questionFrame.fxml"));
        questionFrameScene = new Scene(questionFrameParent);
    }

    /**
     * To editor frame.
     *
     * @throws Exception the exception
     */
    public void toEditorFrame() throws Exception{
        editorController.editorConfirm.setMouseTransparent(false);
        editorController.editorCancel.setMouseTransparent(false);
        editorController.editor.setMouseTransparent(false);
        editorController.editorTitleField.setMouseTransparent(false);
        editorController.editor.setHtmlText("");
        editorController.editorTitleField.setText("");
        editorController.provinceInfo.setText(LanguageTool.englishToChinese.get(ProvinceController.provinceName));
        editorController.resetPath();
        changeScene(editorFrameScene);
    }

    /**
     * To post view frame.
     *
     * @throws Exception the exception
     */
    public void toPostViewFrame() throws Exception{
        changeScene(postViewFrameScene);
    }

    /**
     * To puzzle frame.
     * &#064;Description  去往拼图游戏界面
     *
     * @throws Exception the exception
     * @author RyotoBUAA
     */
    public void toPuzzleFrame() throws Exception { //切换到拼图界面
        PuzzleController.puzzleProvincePane = mainController.provincePane;
        PicturePuzzleGame.prepare(mainController.provincePane);
        changeScene(puzzleFrameScene);
        puzzleController.puzzleFrameTrigger();
    }

    /**
     * To post frame.
     *
     * @throws Exception the exception
     */
    public void toPostFrame() throws Exception{ //切换到帖子浏览场景
        changeScene(postFrameScene);
        postController.setPostFrameIni();
    }

    /**
     * To post frame.
     *
     * @param bool the bool
     * @throws Exception the exception
     */
    public void toPostFrame(boolean bool) throws Exception{ //切换到帖子浏览场景
        changeScene(postFrameScene);
    }

    /**
     * To province frame.
     *
     * @throws Exception the exception
     */
    public void toProvinceFrame() throws Exception { //切换到具体省份界面
        changeScene(provinceFrameScene);
        provinceController.provinceFrameTrigger();
    }

    /**
     * To province frame.
     *
     * @param bool the bool
     * @throws Exception the exception
     */
    public void toProvinceFrame(boolean bool) throws Exception { //切换到具体省份界面
        changeScene(provinceFrameScene);
    }

    /**
     * To main frame.
     *
     * @param maskControl the mask control
     * @throws Exception the exception
     */
    public void toMainFrame(boolean maskControl) throws Exception { //切换到地图界面
        changeScene(mainFrameScene);
        if (maskControl) mainController.mainFrameTrigger();
    }

    /**
     * To person frame.
     *
     * @param frameEnum     the frame enum
     * @param changeControl the change control
     * @throws Exception the exception
     */
    public void toPersonFrame(FrameEnum frameEnum, boolean changeControl) throws Exception{
        personController.backFrame = frameEnum;
        personController.setProvinceImage();
        if (changeControl){
            UIManager.personController.changeButton.setMouseTransparent(false);
            UIManager.personController.changeButton.setOpacity(0.8);
        }else{
            UIManager.personController.changeButton.setMouseTransparent(true);
            UIManager.personController.changeButton.setOpacity(0.0);
        }
        changeScene(personFrameScene);
    }

    /**
     * To question frame.
     *
     * @throws Exception the exception
     */
    public void toQuestionFrame() throws Exception {
        questionController.resetQuestion();
        changeScene(questionFrameScene);
    }

    /**
     * Change scene.
     *
     * @param scene the scene
     * @throws Exception the exception
     */
    public void changeScene(Scene scene) throws Exception { //切换fxml场景
        mainScene = scene;
        mainStage.setScene(scene); //设定
    }

    /**
     * The constant mainScene.
     */
    public static Scene mainScene; //记录当前场景
    /**
     * The constant mainStage.
     */
    public static Stage mainStage; //当前舞台实例

    private static Scene mainFrameScene; //主界面舞台
    private static Scene puzzleFrameScene; //拼图界面舞台
    private static Scene provinceFrameScene; //具体省份界面舞台
    private static Scene postFrameScene; //帖子浏览器
    private static Scene editorFrameScene; //帖子编辑器舞台
    private static Scene postViewFrameScene; //具体帖子浏览器
    private static Scene loginFrameScene; //登录界面舞台
    private static Scene personFrameScene;
    private static Scene questionFrameScene;
    private final double iniSceneWidth = 1000, iniSceneHeight = 800; //大小设定
    /**
     * The constant editorController.
     */
    public static EditorController editorController;
    /**
     * The constant loginController.
     */
    public static LoginController loginController;
    /**
     * The constant mainController.
     */
    public static MainController mainController;
    /**
     * The constant postController.
     */
    public static PostController postController;
    /**
     * The constant postViewController.
     */
    public static PostViewController postViewController;
    /**
     * The constant provinceController.
     */
    public static ProvinceController provinceController;
    /**
     * The constant puzzleController.
     */
    public static PuzzleController puzzleController;
    /**
     * The constant personController.
     */
    public static PersonController personController;
    /**
     * The constant questionController.
     */
    public static QuestionController questionController;

    @Override
    public void start(Stage stage) throws Exception { //窗口初始化

        fxmlFileLoad(); //fxml文件加载
        setNetworkManager(); //传实例

        mainScene = loginFrameScene; //初始场景设置

        mainStage = stage;
        stage.setTitle("TRAVEL ~Wonderful Everyday~"); //标题名称设置
        stage.setScene(mainScene);
        stage.setWidth(iniSceneWidth);
        stage.setHeight(iniSceneHeight);
        stage.setResizable(false); //窗口大小固定
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        stage.getIcons().add(icon); //设置ICON 暂定黑骑士
        stage.show(); //显示
    }
}