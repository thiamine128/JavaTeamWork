package ui;

public class ProvinceInfoStruct {
    private double x, y; //位置
    private double width, height; //大小
    private String name; //名字

    //构造方法
    public ProvinceInfoStruct(double x, double y, double width, double height, String name){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }

    public String getName(){
        return name;
    }

}
