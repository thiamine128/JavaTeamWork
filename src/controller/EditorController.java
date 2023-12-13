package controller;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import post.ImageSelect;
import post.LanguageTool;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;
import ui.UINetwork;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    public TextField editorTitleField; //标题编辑器
    public HTMLEditor editor; //帖子编辑器
    public ImageView editorConfirm, editorCancel, editorImage; //确认、取消按钮
    public Text editorHint;
    public Text provinceInfo; //提示语
    public ImageView postSuccessHint; //发帖成功
    public FileChooser fileChooser; //图片选择器
    private List<Path> postImagePath = new ArrayList<>(); //帖子图片路径
    public HBox imageHbox; //照片预览器
    public void resetPath() {
        postImagePath.clear(); //清空图片路径组
    }

    public void deletePath(Path path, ImageSelect imageSelect){
        postImagePath.remove(path);
        imageHbox.getChildren().removeAll(imageSelect);
    }

    public void uploadPostImage() throws IOException { //上传图片
        if (postImagePath.size() < 2){
            File postImage = fileChooser.showOpenDialog(UIManager.mainStage);
            if (postImage != null){
                Path imagePath = postImage.toPath();
                postImagePath.add(imagePath);
                imageHbox.getChildren().addAll(new ImageSelect(imagePath, postImage.getName()));
            }
        }else{
            editorHint.setText("上传照片过多");
            UIAnimation.setBlackMask(editorHint, null, 600);
        }
    }

    public void resetEditor(){
        editorHint.setText("发送失败");
        UIAnimation.timer(3000,event -> {
            editorConfirm.setMouseTransparent(false);
            editorCancel.setMouseTransparent(false);
            editor.setMouseTransparent(false);
            editorTitleField.setMouseTransparent(false);
        });
        editorHint.setOpacity(0.8);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("图片文件",
                "*.jpg", "*.jpeg", "*.png"));

        UIManager.editorController = this;
        postSuccessHint.setOpacity(0.0);
        editorHint.setText("");
        editorTitleField.setPromptText("在这里输入标题");

        editorImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editorImage.setOpacity(0.5);
                editorHint.setText("上传照片");
                UIAnimation.setBlackMask(editorHint, null, 600);
            }
        });
        editorImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editorImage.setOpacity(0.8);
                if (!editor.isMouseTransparent())
                    UIAnimation.fadeAnimation(editorHint, null, false, 600);
            }
        });
        editorImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    uploadPostImage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        editorConfirm.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editorConfirm.setOpacity(0.5);
                editorHint.setText("确认发送");
                UIAnimation.setBlackMask(editorHint, null, 600);
            }
        });
        editorConfirm.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editorConfirm.setOpacity(0.8);
                if (!editor.isMouseTransparent())
                    UIAnimation.fadeAnimation(editorHint, null, false, 600);
            }
        });
        editorConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editorTitleField.getText().length() > 0 && editor.getHtmlText().length() > 10){
                    editorHint.setText("发送请求中");
                    editorConfirm.setMouseTransparent(true);
                    editorCancel.setMouseTransparent(true);
                    editor.setMouseTransparent(true);
                    editorTitleField.setMouseTransparent(true);
                    editorHint.setOpacity(0.8);
                    UIAnimation.timer(2000, event ->
                            UINetwork.publishPost(editorTitleField.getText(),
                                    LanguageTool.chineseToEnglish.get(provinceInfo.getText()),
                                    editor.getHtmlText(), postImagePath));
                }else editorHint.setText("发送内容过短！");
            }
        });

        editorCancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editorCancel.setOpacity(0.5);
                editorHint.setText("取消发送");
                UIAnimation.setBlackMask(editorHint, null, 600);
            }
        });
        editorCancel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editorCancel.setOpacity(0.8);
                UIAnimation.fadeAnimation(editorHint, null, false, 600);
            }
        });
        editorCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    editorTitleField.clear();
                    editor.setHtmlText("");
                    UIFunction.manager.toProvinceFrame(true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

}
