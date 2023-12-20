package game;

/**
 * The Hamiltonian detail.
 */
public class HamiltonianDetail {
    /**
     * The path.
     */
    public String[] path;
    /**
     * The cost.
     */
    public int cost;
    /**
     * The found flag.
     */
    public boolean found;

    /**
     * Instantiates a new Hamiltonian detail.
     *
     * @param _path  the path
     * @param _cost  the cost
     * @param _found the found flag
     */
    HamiltonianDetail(String[] _path,int _cost,boolean _found) {
        path=_path;
        cost=_cost;
        found=_found;
    }
}
