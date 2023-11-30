package controller;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import ui.UIAnimation;
import ui.UIFunction;
import ui.UIManager;
import ui.UINetwork;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    public TextField editorTitleField; //标题编辑器
    public HTMLEditor editor; //帖子编辑器
    public ImageView editorConfirm, editorCancel; //确认、取消按钮
    public Text editorHint, provinceInfo; //提示语
    public ImageView postSuccessHint; //发帖成功

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.editorController = this;
        postSuccessHint.setOpacity(0.0);
        editorHint.setText("");

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
                            UINetwork.publishPost(editorTitleField.getText(), provinceInfo.getText(), editor.getHtmlText()));
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
