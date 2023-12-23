package ui;

import javafx.scene.image.ImageView;

/**
 * image processor.
 */
public class UIImageProcess {

    /**
     * Change size of some image.
     *
     * @param image0 some image need to handled.
     * @param width  the width of the image.
     * @param height the height of the image.
     */
    public static void changeSize(ImageView image0, double width, double height){
        image0.setPreserveRatio(false);
        image0.setFitWidth(width);
        image0.setFitHeight(height);
    }

    /**
     * Change position of some image.
     *
     * @param image0  some image need to be handled.
     * @param layoutX x-axis.
     * @param layoutY y-axis.
     */
    public static void changePos(ImageView image0, double layoutX, double layoutY){
        image0.setLayoutX(layoutX);
        image0.setLayoutY(layoutY);
    }

}
