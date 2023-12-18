package network.page;

import java.util.List;

public class Page<T> {
    private int number;
    private int totalPages;
    private List<T> list;

    public Page(int currentPage, int pageNum, List<T> list) {
        this.number = currentPage;
        this.totalPages = pageNum;
        this.list = list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
