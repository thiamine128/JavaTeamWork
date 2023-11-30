package ui;

import javafx.scene.image.ImageView;

public class UIImageProcess {

    public static void changeSize(ImageView image0, double width, double height){
        image0.setPreserveRatio(false);
        image0.setFitWidth(width);
        image0.setFitHeight(height);
    }

    public static void changePos(ImageView image0, double layoutX, double layoutY){
        image0.setLayoutX(layoutX);
        image0.setLayoutY(layoutY);
    }

}
