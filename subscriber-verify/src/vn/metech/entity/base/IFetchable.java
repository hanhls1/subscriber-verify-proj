package vn.metech.entity.base;

import java.util.Date;

public interface IFetchable {

	int getFetchTimes();

	Date getLastFetch();

	boolean isFetchable();

	void resetFetch();

}
