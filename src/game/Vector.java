package game;

/**
 * The type Vector.
 */
public class Vector {
    /**
     * The X.
     */
    double x, /**
     * The Y.
     */
    y;

    /**
     * Instantiates a new Vector.
     *
     * @param c the c
     * @param d the d
     */
    Vector(double c,double d) {
        x=c;
        y=d;
    }

    /**
     * Instantiates a new Vector.
     *
     * @param c the c
     * @param d the d
     */
    Vector(Point c,Point d) {
        this.x=d.x-c.x;
        this.y=d.y-c.y;
    }

    /**
     * Check same boolean.
     *
     * @param c   the c
     * @param d   the d
     * @param eps the eps
     * @return the boolean
     */
    public static boolean checkSame(Vector c,Vector d,double eps) {
        return Math.abs(c.x-d.x)<eps&&Math.abs(c.y-d.y)<eps;
    }

    /**
     * Get x double.
     *
     * @return the double
     */
    public double getX(){
        return x;
    }

    /**
     * Get y double.
     *
     * @return the double
     */
    public double getY(){
        return y;
    }
}
