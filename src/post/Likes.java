package post;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Likes extends Text {

    public Likes(){
        this.setLayoutX(865);
        this.setLayoutY(720);
        Font font = Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12);
        this.setFont(font);
        this.setText("+1");
        this.setFill(Color.rgb(255, 255, 245));
        this.setOpacity(0.0);
    }

}
