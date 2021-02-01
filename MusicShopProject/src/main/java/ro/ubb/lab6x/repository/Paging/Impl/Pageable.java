package ro.ubb.lab6x.repository.Paging.Impl;

public class Pageable implements ro.ubb.lab6x.repository.Paging.Pageable {
    private int pageNumber;
    private int pageSize;

    public Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }
}
