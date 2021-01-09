package vn.metech.shared;

import java.util.ArrayList;
import java.util.List;

public class PagedResult<T> {

	private int totalRecords;
	private int dataCount;
	private List<T> data;

	public PagedResult() {
		data = new ArrayList<>();
	}

	public PagedResult(int totalRecords) {
		this();
		this.totalRecords = totalRecords;
	}

	public PagedResult(List<T> data) {
		this(-1, data);
	}

	public PagedResult(int totalRecords, List<T> data) {
		this();
		if (data != null) {
			this.data = data;
		}

		this.totalRecords = totalRecords >= 0 ? totalRecords : this.data.size();
	}

	public void addData(T data) {
		if (data == null) {
			return;
		}

		this.data.add(data);
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords == null ? 0 : totalRecords.intValue();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getDataCount() {
		return data.size();
	}
}
