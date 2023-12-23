package game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Math.min;

/**
 * The Hamiltonian algorithm.
 */
public class Hamiltonian {
    /**
     * The distance.
     */
    int[][] dis=new int[40][40];
    /**
     * The time.
     */
    int[][] tim=new int[40][40];
    /**
     * The number.
     */
    int N, /**
     * The infinity.
     */
    INF=1000000000;
    /**
     * The Chinese id.
     */
    HashMap<String,Integer> ChId=new HashMap<>();
    /**
     * The English id.
     */
    HashMap<String,Integer> EnId=new HashMap<>();
    /**
     * The states.
     */
    int[][] f=new int[1<<20][20];
    /**
     * The names.
     */
    String[] name=new String[40];

    /**
     * Instantiates a new Hamiltonian.
     * We prepare sth for the following calculation.
     */
    public Hamiltonian() {
        InputStream fin=null;
        fin = getClass().getResourceAsStream("/distance/cities_name.txt");
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
        fin = getClass().getResourceAsStream("/distance/all_dis_driving.txt");
        in=new Scanner(fin);
        while(in.hasNext()) {
            String ss=in.nextLine();
            String[] s=ss.split(" ");
            dis[ChId.get(s[0])][ChId.get(s[1])]=Integer.parseInt(s[2]);
        }
        in.close();
        fin = getClass().getResourceAsStream("/distance/all_time_driving.txt");
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

    /**
     * The Points.
     */
    int[] points=new int[20];

    /**
     * Calculate distance hamiltonian detail.
     *
     * @param all the all points
     * @return the hamiltonian detail
     */
    public HamiltonianDetail calcDis(String[] all) {
        int n=all.length;
        String[] res=new String[n+1];
        if(n>17||n<2) return new HamiltonianDetail(res,0,false);
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
        return new HamiltonianDetail(res,minn,true);
    }

    /**
     * Calculate time Hamiltonian detail.
     *
     * @param all the all points
     * @return the Hamiltonian detail
     */
    public HamiltonianDetail calcTime(String[] all) {
        int n=all.length;
        String[] res=new String[n+1];
        if(n>17) return new HamiltonianDetail(res,0,false);
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
        return new HamiltonianDetail(res,minn,true);
    }

    /**
     * The Path.
     */
    ArrayList<Integer> path=new ArrayList<>();
    /**
     * Calculate distance hamiltonian detail.
     *
     * @param u start point, s status, n sum of provinces
     *
     */
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
    /**
     * Calculate distance hamiltonian detail.
     *
     * @param u start point, s status, n sum of provinces
     *
     */
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
