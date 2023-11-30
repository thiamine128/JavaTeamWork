package Click;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProvinceDetail {
    public String[] interest=new String[12];
    public String[] food=new String[12];
    public String[] folk=new String[12];
    public String[] interestName=new String[12];
    public String[] foodName=new String[12];
    public String[] folkName=new String[12];
    public int interestSum,foodSum,folkSum;
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
            res.interest[++res.interestSum]=s.substring(pos+1);
            res.interestName[res.interestSum]=s.substring(s.indexOf(".")+1,pos);
        }
        while(in.hasNext()) {
            String s=in.nextLine();
            if(s.contains("$")) break;
            if(s.isEmpty()) {
                continue;
            }
            int pos=s.indexOf("：");
            res.food[++res.foodSum]=s.substring(pos+1);
            res.foodName[res.foodSum]=s.substring(s.indexOf('.')+1,pos);
        }
        while(in.hasNext()) {
            String s=in.nextLine();
            if(s.contains("$")) break;
            if(s.isEmpty()) {
                continue;
            }
            int pos=s.indexOf("：");
            res.folk[++res.folkSum]=s.substring(pos+1);
            res.folkName[res.folkSum]=s.substring(s.indexOf('.')+1,pos);
        }
        return res;
    }

}
