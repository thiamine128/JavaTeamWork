package game;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import java.util.HashMap;

/**
 * The picture puzzle game.
 */
public class PicturePuzzleGame {
    /**
     * The province id.
     */
    static HashMap<String,Integer> provinceId=new HashMap<>();
    /**
     * The count.
     */
    static int cnt, /**
     * The number.
     */
    n=32;
    /**
     * The standard.
     */
    static Vector[] standard=new Vector[40];
    /**
     * The constant pos.
     */
    public static Vector[] pos=new Vector[40];

    /**
     * Reset.
     *
     * @param x1 the x position 1
     * @param x2 the x position 2
     * @param y1 the y position 1
     * @param y2 the y position 2
     */
    static public void reset(double x1,double x2,double y1,double y2) {
        for(int i=1;i<=n;i++) {
            pos[i]=new Vector(x1+(x2-x1)*Math.random(),y1+(y2-y1)*Math.random());
        }
    }

    /**
     * Get position.
     *
     * @param province the province
     * @return the position
     */
    static public Vector getPosition(String province) {
        return pos[provinceId.get(province)];
    }

    /**
     * Change position.
     *
     * @param province the province
     * @param x        the x
     * @param y        the y
     */
    static public void changePosition(String province,double x,double y) {
        int u=provinceId.get(province);
        pos[u].x = x;
        pos[u].y = y;
    }

    /**
     * The constant prepare key flag.
     */
    public static boolean prepareKey = true;

    /**
     * Prepare for the following operation.
     *
     *
     * @param provincePane the province pane
     */
    static public void prepare(Pane provincePane) {
        if (prepareKey){
            prepareKey = false;
            double sx=0.0,sy=0.0;
            for(Node province : provincePane.getChildren()) {
                cnt++;
                if(province.getId().equals("beijing")) {
                    sx=province.getLayoutX();
                    sy=province.getLayoutY();
                }
                provinceId.put(province.getId(),cnt);
            }
            for(Node province : provincePane.getChildren()) {
                int v=provinceId.get(province.getId());
                Vector vector=new Vector(province.getLayoutX()-sx,province.getLayoutY()-sy);
                standard[v]=vector;
            }
        }

    }

    /**
     * Check whether the Jigsaw puzzle is valid.
     *
     * @return the valid or not
     */
    static public boolean check() {
        for(int i=1;i<=32;i++) {
            Vector tmp=new Vector(pos[i].x-pos[provinceId.get("beijing")].x,pos[i].y-pos[provinceId.get("beijing")].y);
            if(!Vector.checkSame(standard[i], tmp,20.0)) {
                return false;
            }
        }
        return true;
    }
}
