package game;

/**
 * The type Hamiltonian detail.
 */
public class HamiltonianDetail {
    /**
     * The Path.
     */
    public String[] path;
    /**
     * The Cost.
     */
    public int cost;
    /**
     * The Found.
     */
    public boolean found;

    /**
     * Instantiates a new Hamiltonian detail.
     *
     * @param _path  the path
     * @param _cost  the cost
     * @param _found the found
     */
    HamiltonianDetail(String[] _path,int _cost,boolean _found) {
        path=_path;
        cost=_cost;
        found=_found;
    }
}
