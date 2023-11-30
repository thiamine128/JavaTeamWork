package Game;
import javafx.scene.Node;
public class Vector {
    double x,y;
    Vector(double c,double d) {
        x=c;
        y=d;
    }
    Vector(Point c,Point d) {
        this.x=d.x-c.x;
        this.y=d.y-c.y;
    }
    public static boolean checkSame(Vector c,Vector d,double eps) {
        return Math.abs(c.x-d.x)<eps&&Math.abs(c.y-d.y)<eps;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
