package vn.metech.shared;

import vn.metech.constant.Paging;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public abstract class PagedRequest implements IPaging {

	@NotNull
	@Min(1)
	protected int currentPage;

	@NotNull
	@Min(1)
	protected int pageSize;

	protected PagedRequest() {
		pageSize = Paging.PAGE_SIZE;
		currentPage = Paging.FIRST_PAGE;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : Paging.PAGE_SIZE;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage > 0 ? currentPage : Paging.FIRST_PAGE;
	}

	@Override
	public int skip() {
		return (currentPage - 1) * pageSize;
	}

	@Override
	public int take() {
		return pageSize;
	}
}