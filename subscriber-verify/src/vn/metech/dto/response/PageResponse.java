package vn.metech.dto.response;

import vn.metech.dto.request.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class PageResponse<T> {

    private long total;
    private int currentPage;
    private int pageSize;
    private List<T> data;

    public PageResponse() {
        this.total = 0;
        this.currentPage = 1;
        this.pageSize = 1;
        this.data = new ArrayList<>();
    }

    public PageResponse(long total, List<T> data, int currentPage, int pageSize) {
        this.total = total;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.data = data;
    }

    public PageResponse(long total, List<T> data, PageRequest pageRequest) {
        this(total, data, pageRequest.getCurrentPage(), pageRequest.getPageSize());
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
