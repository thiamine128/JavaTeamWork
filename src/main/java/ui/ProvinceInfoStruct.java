package ui;

/**
 * The province info struct.
 */
public class ProvinceInfoStruct {
    private double x, y; //位置
    private double width, height; //大小
    private String name; //名字

    /**
     * Instantiates a new Province info struct.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param name   the name
     */
//构造方法
    public ProvinceInfoStruct(double x, double y, double width, double height, String name){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
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

    /**
     * Get width double.
     *
     * @return the double
     */
    public double getWidth(){
        return width;
    }

    /**
     * Get height double.
     *
     * @return the double
     */
    public double getHeight(){
        return height;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return name;
    }

}
