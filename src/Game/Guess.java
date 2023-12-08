package Game;

import Click.ProvinceDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Guess {
    static ProvinceDetail[] allDetail=new ProvinceDetail[40];
    static String[] all={"henan","hebei","shanxi","liaoning","jilin","heilongjiang","jiangsu","zhejiang","anhui","fujian","jiangxi","shandong","hubei","hunan","guangdong","hainan","sichuan","guizhou","yunnan","shaanxi","gansu","qinghai","ningxia","xinjiang","xizang","guangxi","neimenggu","taiwan","xianggang","aomen","beijing","tianjin","shanghai","chongqing"};
    public static void prepare() {
        for(int i=0;i<34;i++) {
            allDetail[i]=ProvinceDetail.getDetail(all[i]);
        }
    }
    public static GuessDetail[] reset(int opt,int sum) {
        Random seed=new Random();
        List<Integer> a=new ArrayList<>();
        String[] type={"景点","美食","民俗"};
        for(int i=0;i<34;i++) a.add(i);
        GuessDetail[] res=new GuessDetail[sum];
        for(int i=0;i<sum;i++) {
            GuessDetail one=new GuessDetail();
            Collections.shuffle(a,seed);
            one.ansProvince=all[a.get(0)];//钦定第一个为答案
            int chooseType=0;
            if(opt<3) chooseType=opt;
            else chooseType=seed.nextInt(3);
            one.type=type[chooseType];
            for(int j=0;j<4;j++) {
                String name="";
                int nowId=a.get(j);
                if(chooseType==0) {
                    name=allDetail[nowId].interestName[seed.nextInt(allDetail[nowId].interestSum)+1];
                }
                else if(chooseType==1) {
                    name=allDetail[nowId].foodName[seed.nextInt(allDetail[nowId].foodSum)+1];
                }
                else {
                    name=allDetail[nowId].folkName[seed.nextInt(allDetail[nowId].folkSum)+1];
                }
                int random0 = seed.nextInt(2)+1;
                String path="./image0/"+all[nowId]+"_"+type[chooseType]+"_"+(name+"_"+(random0))+".jpg";
                one.path.add(path);
            }
            Collections.shuffle(one.path,seed);
            for(int j=0;j<4;j++) {
                if(one.path.get(j).contains(one.ansProvince)) {
                    one.ansId=j;
                    break;
                }
            }
            res[i]=one;
        }
        return res;
    }
}
