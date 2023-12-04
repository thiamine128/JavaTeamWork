package Game;

public class HamiltonianDetail {
    public String[] path;
    public int cost;
    public boolean found;
    HamiltonianDetail(String[] _path,int _cost,boolean _found) {
        path=_path;
        cost=_cost;
        found=_found;
    }
}
