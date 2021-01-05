package vn.metech.dto.monitor;

public class PageDto {

    private int page;
    private int size;

    public PageDto(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageDto() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
