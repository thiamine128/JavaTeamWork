package ui;

/**
 * The province information class: describe 34 provinces.
 */
public class ProvinceInfoStruct {
    private double x, y; //位置
    private double width, height; //大小
    private String name; //名字

    /**
     * Instantiates a new Province info struct.
     *
     * @param x      the x-axis
     * @param y      the y-axis
     * @param width  the width of the image
     * @param height the height of the image
     * @param name   the name of this province
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
     * Get x-axis.
     *
     * @return x-axis of this province.
     */
    public double getX(){
        return x;
    }

    /**
     * Get y-axis.
     *
     * @return y-axis of this province.
     */
    public double getY(){
        return y;
    }

    /**
     * Get width of this province.
     *
     * @return width
     */
    public double getWidth(){
        return width;
    }

    /**
     * Get height of this province.
     *
     * @return height
     */
    public double getHeight(){
        return height;
    }

    /**
     * Get name of the province.
     *
     * @return name of province
     */
    public String getName(){
        return name;
    }

}
