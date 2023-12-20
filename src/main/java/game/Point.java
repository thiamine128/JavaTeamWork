package game;

/**
 * The point.
 */
public class Point {
    /**
     * The x.
     */
    double x, /**
     * The y.
     */
    y;

    /**
     * Point minus point.
     *
     * @param c the point c
     * @param d the point d
     * @return the result point
     */
    public static Point pointMinus(Point c,Point d) {
        Point res=new Point();
        res.x=c.x-d.x;
        res.y=c.x-d.y;
        return res;
    }
}
