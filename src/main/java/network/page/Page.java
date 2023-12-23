package network.page;

import java.util.List;

/**
 * The page container.
 *
 * @param <T> the type parameter
 */
public class Page<T> {
    private int number;
    private int totalPages;
    private List<T> list;

    /**
     * Instantiates a new Page.
     *
     * @param currentPage the current page number
     * @param pageNum     the page number in total
     * @param list        the list of content
     */
    public Page(int currentPage, int pageNum, List<T> list) {
        this.number = currentPage;
        this.totalPages = pageNum;
        this.list = list;
    }

    /**
     * Get total pages.
     *
     * @return the total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Set total pages.
     *
     * @param totalPages the total pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Get list.
     *
     * @return the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Sets list.
     *
     * @param list the list
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * Get page number.
     *
     * @return the page number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set page number.
     *
     * @param number the page number
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
