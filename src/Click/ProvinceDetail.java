package Click;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class ProvinceDetail {
    public String[] interest=new String[12];
    public String[] food=new String[12];
    public String[] folk=new String[12];
    public String[] interestName=new String[12];
    public String[] foodName=new String[12];
    public String[] folkName=new String[12];
    public String[] interestPath=new String[12];
    public String[] foodPath=new String[12];
    public String[] folkPath=new String[12];
    public int interestSum,foodSum,folkSum;
//    Random seed=new Random();
//    ProvinceDetail x=ProvinceDetail.getDetail("beijing");
//        for(int i=1;i<=x.foodSum;i++) {
//        System.out.println(x.foodPath[i]+seed.nextInt(1,3)+".jpg");
//    }
    public static ProvinceDetail getDetail(String x) {
        ProvinceDetail res=new ProvinceDetail();
        String filePath="data/"+x+".txt";
        FileInputStream fin=null;
        try {
            fin=new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner in=new Scanner(fin);
        while(in.hasNext()) {
            String s=in.nextLine();
            if(s.contains("$")) break;
            if(s.isEmpty()) {
                continue;
            }
            int pos=s.indexOf("：");
            String name=s.substring(s.indexOf(".")+1,pos);
            res.interest[++res.interestSum]=s.substring(pos+1);
            res.interestName[res.interestSum]=name;
            res.interestPath[res.interestSum]="./image0/"+x+"_景点_"+name+"_";
        }
        while(in.hasNext()) {
            String s=in.nextLine();
            if(s.contains("$")) break;
            if(s.isEmpty()) {
                continue;
            }
            int pos=s.indexOf("：");
            String name=s.substring(s.indexOf('.')+1,pos);
            res.food[++res.foodSum]=s.substring(pos+1);
            res.foodName[res.foodSum]=name;
            res.foodPath[res.foodSum]="./image0/"+x+"_美食_"+name+"_";
        }
        while(in.hasNext()) {
            String s=in.nextLine();
            if(s.contains("$")) break;
            if(s.isEmpty()) {
                continue;
            }
            int pos=s.indexOf("：");
            String name=s.substring(s.indexOf('.')+1,pos);
            res.folk[++res.folkSum]=s.substring(pos+1);
            res.folkName[res.folkSum]=name;
            res.folkPath[res.folkSum]="./image0/"+x+"_民俗_"+name+"_";
        }
        return res;
    }

}
