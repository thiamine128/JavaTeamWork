package game;

/**
 * The vector.
 */
public class Vector {
    /**
     * The x.
     */
    double x, /**
     * The y.
     */
    y;

    /**
     * Instantiates a new vector.
     *
     * @param c the c
     * @param d the d
     */
    Vector(double c,double d) {
        x=c;
        y=d;
    }

    /**
     * Instantiates a new vector.
     *
     * @param c the c
     * @param d the d
     */
    Vector(Point c,Point d) {
        this.x=d.x-c.x;
        this.y=d.y-c.y;
    }

    /**
     * Check whether the two vectors are the same vector.
     *
     * @param c   the vector c
     * @param d   the vector d
     * @param eps the error
     * @return the same flag
     */
    public static boolean checkSame(Vector c,Vector d,double eps) {
        return Math.abs(c.x-d.x)<eps&&Math.abs(c.y-d.y)<eps;
    }

    /**
     * Get x.
     *
     * @return the x
     */
    public double getX(){
        return x;
    }

    /**
     * Get y.
     *
     * @return the y
     */
    public double getY(){
        return y;
    }
}
