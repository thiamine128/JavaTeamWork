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
 * The UI manager, an important part of this software.
 */
public class UIManager extends Application {
    /**
     * The instance of UIManager.
     */
    public static UIManager instance;

    /**
     * the entry of this software.
     *
     * @param args parameters in command line.
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
     * the function switch to editorFrame.
     *
     * @throws Exception the exception when using this function.
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
     * the function switch to postFrame.
     *
     * @throws Exception the exception when using this function.
     */
    public void toPostViewFrame() throws Exception{
        changeScene(postViewFrameScene);
    }

    /**
     * the function switch to puzzleFrame.
     *
     * @throws Exception the exception when using this function.
     */
    public void toPuzzleFrame() throws Exception { //切换到拼图界面
        PuzzleController.puzzleProvincePane = mainController.provincePane;
        PicturePuzzleGame.prepare(mainController.provincePane);
        changeScene(puzzleFrameScene);
        puzzleController.puzzleFrameTrigger();
    }

    /**
     * the function switch to postFrame.
     *
     * @throws Exception the exception when using this function.
     */
    public void toPostFrame() throws Exception{ //切换到帖子浏览场景
        changeScene(postFrameScene);
        postController.setPostFrameIni();
    }

    /**
     * the function switch to postFrame.
     *
     * @param bool control the animations.
     * @throws Exception the exception when using this function.
     */
    public void toPostFrame(boolean bool) throws Exception{ //切换到帖子浏览场景
        changeScene(postFrameScene);
    }

    /**
     * the function switch to provinceFrame.
     *
     * @throws Exception the exception when using this function.
     */
    public void toProvinceFrame() throws Exception { //切换到具体省份界面
        changeScene(provinceFrameScene);
        provinceController.provinceFrameTrigger();
    }

    /**
     * the function switch to provinceFrame.
     *
     * @throws Exception the exception when using this function.
     */
    public void toProvinceFrame(boolean bool) throws Exception { //切换到具体省份界面
        changeScene(provinceFrameScene);
    }

    /**
     * the function switch to mainFrame.
     *
     * @param maskControl decide whether to have a mask.
     * @throws Exception the exception when using this function.
     */
    public void toMainFrame(boolean maskControl) throws Exception { //切换到地图界面
        changeScene(mainFrameScene);
        if (maskControl) mainController.mainFrameTrigger();
    }

    /**
     * the function switch to personFrame.
     *
     * @param frameEnum     the frame now.
     * @param changeControl decide whether to change.
     * @throws Exception the exception when using this function.
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
     * the function switch to questionFrame.
     *
     * @throws Exception the exception when using this function.
     */
    public void toQuestionFrame() throws Exception {
        questionController.resetQuestion();
        changeScene(questionFrameScene);
    }

    /**
     * Change scene.
     *
     * @param scene new scene
     * @throws Exception the exception when using this function.
     */
    public void changeScene(Scene scene) throws Exception { //切换fxml场景
        mainScene = scene;
        mainStage.setScene(scene); //设定
    }

    /**
     * The constant mainScene: the instance of current scene.
     */
    public static Scene mainScene; //记录当前场景
    /**
     * The constant mainStage: the instance of current stage.
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
     *  editorController.
     */
    public static EditorController editorController;
    /**
     *  loginController.
     */
    public static LoginController loginController;
    /**
     *  mainController.
     */
    public static MainController mainController;
    /**
     *  postController.
     */
    public static PostController postController;
    /**
     *  postViewController.
     */
    public static PostViewController postViewController;
    /**
     *  provinceController.
     */
    public static ProvinceController provinceController;
    /**
     *  puzzleController.
     */
    public static PuzzleController puzzleController;
    /**
     *  personController.
     */
    public static PersonController personController;
    /**
     *  questionController.
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