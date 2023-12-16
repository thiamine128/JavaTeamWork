package ui;

import javafx.scene.image.ImageView;

/**
 * The type Ui image process.
 */
public class UIImageProcess {

    /**
     * Change size.
     *
     * @param image0 the image
     * @param width  the width
     * @param height the height
     */
    public static void changeSize(ImageView image0, double width, double height){
        image0.setPreserveRatio(false);
        image0.setFitWidth(width);
        image0.setFitHeight(height);
    }

    /**
     * Change pos.
     *
     * @param image0  the image
     * @param layoutX the layout x
     * @param layoutY the layout y
     */
    public static void changePos(ImageView image0, double layoutX, double layoutY){
        image0.setLayoutX(layoutX);
        image0.setLayoutY(layoutY);
    }

}
