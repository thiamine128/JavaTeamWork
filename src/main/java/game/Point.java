package game;

/**
 * The type Point.
 */
public class Point {
    /**
     * The X.
     */
    double x, /**
     * The Y.
     */
    y;

    /**
     * Point minus point.
     *
     * @param c the c
     * @param d the d
     * @return the point
     */
    public static Point pointMinus(Point c,Point d) {
        Point res=new Point();
        res.x=c.x-d.x;
        res.y=c.x-d.y;
        return res;
    }
}
