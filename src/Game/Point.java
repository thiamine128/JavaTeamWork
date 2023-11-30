package Game;

public class Point {
    double x,y;
    public static Point pointMinus(Point c,Point d) {
        Point res=new Point();
        res.x=c.x-d.x;
        res.y=c.x-d.y;
        return res;
    }
}
