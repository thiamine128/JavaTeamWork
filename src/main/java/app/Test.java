package app;

import province.ProvinceDetail;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {

    public static void main(String[] args) {
        String[] all={"henan","hebei","shanxi","liaoning","jilin","heilongjiang","jiangsu","zhejiang","anhui","fujian","jiangxi","shandong","hubei","hunan","guangdong","hainan","sichuan","guizhou","yunnan","shaanxi","gansu","qinghai","ningxia","xinjiang","xizang","guangxi","neimenggu","taiwan","xianggang","aomen","beijing","tianjin","shanghai","chongqing"};

        for(String name : all) {
            ProvinceDetail x=ProvinceDetail.getDetail(name);
            for(int i=1;i<=x.folkSum;i++) {
                String p=x.folkPath[i];
                String imagePath = p+"1.jpg"; // 图片文件的路径
//System.out.println(imagePath);
                try {

                    File imageFile = new File(imagePath);
//System.out.println(x);
                    BufferedImage image = ImageIO.read(Test.class.getResource(imagePath));
                    int width = image.getWidth();

                    int height = image.getHeight();

    //                System.out.println("Image width: " + width);
    //
    //                System.out.println("Image height: " + height);

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }

        }
    }
}