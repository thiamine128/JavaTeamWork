package Game;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Math.min;
import static java.util.Collections.swap;

public class Hamiltonian {
    int[][] dis=new int[40][40];
    int[][] tim=new int[40][40];
    int N,INF=1000000000;
    HashMap<String,Integer> ChId=new HashMap<>();
    HashMap<String,Integer> EnId=new HashMap<>();
    int[][] f=new int[1<<20][20];
    String[] name=new String[40];
    public Hamiltonian() {
        FileInputStream fin=null;
        try {
            fin=new FileInputStream("./distance/cities_name.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner in=new Scanner(fin);
        int cnt=0;
        while(in.hasNext()) {
            String s=in.nextLine();
            String Ch=s.substring(0,s.indexOf(' '));
            String En=s.substring(s.indexOf(' ')+1);
            cnt++;
            ChId.put(Ch,cnt);
            EnId.put(En,cnt);
            name[cnt]=En;
        }
        in.close();
        try {
            fin=new FileInputStream("./distance/all_dis_driving.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        in=new Scanner(fin);
        while(in.hasNext()) {
            String ss=in.nextLine();
            String[] s=ss.split(" ");
            dis[ChId.get(s[0])][ChId.get(s[1])]=Integer.parseInt(s[2]);
        }
        in.close();
        try {
            fin=new FileInputStream("./distance/all_time_driving.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        in=new Scanner(fin);
        while(in.hasNext()) {
            String ss=in.nextLine();
            String[] s=ss.split(" ");
            tim[ChId.get(s[0])][ChId.get(s[1])]=Integer.parseInt(s[2]);
        }
        in.close();
        try {
            fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    int[] points=new int[20];
    public HamiltonianDetial calcDis(String[] all) {
        int n=all.length;
        String[] res=new String[n+1];
        if(n>17) return new HamiltonianDetial(res,0,false);
        for(int i=0;i<n;i++) {
            points[i]=EnId.get(all[i]);
        }
        for(int i=0;i<1<<n;i++) {
            for(int j=0;j<n;j++) {
                f[i][j]=INF;
            }
        }
        f[1][0]=0;
        for(int i=0;i<1<<n;i++) {
            for(int j=0;j<n;j++) {
                if((i&(1<<j))==0) continue;
                for(int k=0;k<n;k++) {
                    if((i&(1<<k))==0) continue;
                    if(j==k) continue;
                    if((i&(1<<j))>0) {
                        f[i][j]=min(f[i][j],f[i^(1<<j)][k]+dis[points[k]][points[j]]);
                    }
                }
            }
        }
        int minn=2*INF,id=-1;
        for(int i=1;i<n;i++) {
            if(minn>f[(1<<n)-1][i]+dis[points[i]][points[0]]) {
                id=i;
                minn=f[(1<<n)-1][i]+dis[points[i]][points[0]];
            }
        }
        dfs(id,(1<<n)-1,n);
        for(int i=0;i<n;i++) {
            res[i]=name[points[path.get(n-i-1)]];
        }
        res[n]=name[points[path.get(n-1)]];
        return new HamiltonianDetial(res,minn,true);
    }
    public HamiltonianDetial calcTime(String[] all) {
        int n=all.length;
        String[] res=new String[n+1];
        if(n>17) return new HamiltonianDetial(res,0,false);
        for(int i=0;i<n;i++) {
            points[i]=EnId.get(all[i]);
        }
        for(int i=0;i<1<<n;i++) {
            for(int j=0;j<n;j++) {
                f[i][j]=INF;
            }
        }
        f[1][0]=0;
        for(int i=0;i<1<<n;i++) {
            for(int j=0;j<n;j++) {
                if((i&(1<<j))==0) continue;
                for(int k=0;k<n;k++) {
                    if((i&(1<<k))==0) continue;
                    if(j==k) continue;
                    if((i&(1<<j))>0) {
                        f[i][j]=min(f[i][j],f[i^(1<<j)][k]+tim[points[k]][points[j]]);
                    }
                }
            }
        }
        int minn=2*INF,id=-1;
        for(int i=1;i<n;i++) {
            if(minn>f[(1<<n)-1][i]+tim[points[i]][points[0]]) {
                id=i;
                minn=f[(1<<n)-1][i]+tim[points[i]][points[0]];
            }
        }
        dfs2(id,(1<<n)-1,n);
        for(int i=0;i<n;i++) {
            res[i]=name[points[path.get(n-i-1)]];
        }
        res[n]=name[points[path.get(n-1)]];
        return new HamiltonianDetial(res,minn,true);
    }
    ArrayList<Integer> path=new ArrayList<>();
    private void dfs(int u,int s,int n) {
        path.add(u);
        if(s==0) return ;
        for(int i=0;i<n;i++) {
            if(i==u) continue;
            if((s&(1<<i))==0) continue;
            if(f[s][u]==f[s^(1<<u)][i]+dis[points[i]][points[u]]) {
                dfs(i,s^(1<<u),n);
                return ;
            }
        }
    }
    private void dfs2(int u,int s,int n) {
        path.add(u);
        if(s==0) return ;
        for(int i=0;i<n;i++) {
            if(i==u) continue;
            if((s&(1<<i))==0) continue;
            if(f[s][u]==f[s^(1<<u)][i]+tim[points[i]][points[u]]) {
                dfs2(i,s^(1<<u),n);
                return ;
            }
        }
    }
}
