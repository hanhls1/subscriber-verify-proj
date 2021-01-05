package vn.metech.dto.request;

import org.springframework.data.domain.Pageable;

public abstract class PageRequest {

    protected Integer currentPage;
    protected Integer pageSize;

    protected PageRequest() {
        this.currentPage = 1;
        this.pageSize = 20;
    }

    protected PageRequest(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int skip() {
        return (getCurrentPage() - 1) * getPageSize();
    }

    public Pageable pageable() {
        return org.springframework.data.domain.PageRequest.of(getCurrentPage() - 1, getPageSize());
    }

    public Integer getCurrentPage() {
        return (currentPage == null || currentPage < 1) ? 1 : currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null || pageSize < 1) ? 20 : pageSize;
    }
}
