package post;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ui.UIManager;

import java.nio.file.Path;

/**
 * a class showing a uploaded image.
 */
public class ImageSelect extends HBox {
    private TextField imageName;
    private ImageView cancelButton;
    /**
     * The path of the image.
     */
    public Path path;

    /**
     * Instantiates a new object.
     *
     * @param path      the path of the image.
     * @param imageName the name of the image.
     */
    public ImageSelect(Path path, String imageName){

        this.setOpacity(0.8);
        this.path = path;
        this.imageName = new TextField();
        this.imageName.setText(imageName);
        this.imageName.setMouseTransparent(true);
        this.imageName.setMinHeight(28);
        this.imageName.setMaxHeight(28);
        this.imageName.setPrefWidth(115);
        this.getChildren().addAll(this.imageName);

        cancelButton = new ImageView();
        cancelButton.setFitWidth(28);
        cancelButton.setFitHeight(28);
        cancelButton.setImage(new Image(getClass().getResourceAsStream("/puzzleImage/cancel.png")));
        ImageSelect thisImage = this;
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UIManager.editorController.deletePath(path, thisImage);
            }
        });
        this.getChildren().addAll(cancelButton);

        this.setPrefHeight(28);
        this.setPrefWidth(143);
    }

}