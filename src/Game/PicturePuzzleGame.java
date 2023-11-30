package Game;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import java.util.HashMap;

public class PicturePuzzleGame {
    static HashMap<String,Integer> provinceId=new HashMap<>();
    static int cnt, n=32;
    static Vector[] standard=new Vector[40];
    public static Vector[] pos=new Vector[40];
    static public void reset(double x1,double x2,double y1,double y2) {
        for(int i=1;i<=n;i++) {
            pos[i]=new Vector(x1+(x2-x1)*Math.random(),y1+(y2-y1)*Math.random());
        }
    }
    static public Vector getPosition(String province) {
        return pos[provinceId.get(province)];
    }
    static public void changePosition(String province,double x,double y) {
        int u=provinceId.get(province);
        pos[u].x = x;
        pos[u].y = y;
    }

    public static boolean prepareKey = true;
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
    static public boolean check() {
        for(int i=1;i<=32;i++) {
            Vector tmp=new Vector(pos[i].x-pos[provinceId.get("beijing")].x,pos[i].y-pos[provinceId.get("beijing")].y);
            if(!Vector.checkSame(standard[i], tmp,100.0)) {
                return false;
            }
        }
        return true;
    }
}
