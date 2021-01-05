package vn.metech.dto.response.data;

public class Combo05Basic extends Combo04Basic {

	private LocationReport locationReports;

	public LocationReport getLocationReports() {
		return locationReports;
	}

	public void setLocationReports(LocationReport locationReports) {
		this.locationReports = locationReports;
	}

	public class LocationReport {

		private String address;
		private int status;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}
}
