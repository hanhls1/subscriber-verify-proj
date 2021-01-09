package vn.metech.shared;

import vn.metech.constant.Carrier;
import vn.metech.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class Carriers {
	private Carriers() {
	}

	private static Map<String, Carrier> carriers;

	static {
		carriers = new HashMap<>();
		carriers.put("086", Carrier.VIETTEL);
		carriers.put("096", Carrier.VIETTEL);
		carriers.put("097", Carrier.VIETTEL);
		carriers.put("098", Carrier.VIETTEL);
		carriers.put("032", Carrier.VIETTEL);
		carriers.put("033", Carrier.VIETTEL);
		carriers.put("034", Carrier.VIETTEL);
		carriers.put("035", Carrier.VIETTEL);
		carriers.put("036", Carrier.VIETTEL);
		carriers.put("037", Carrier.VIETTEL);
		carriers.put("038", Carrier.VIETTEL);
		carriers.put("039", Carrier.VIETTEL);

		carriers.put("089", Carrier.MOBIFONE);
		carriers.put("090", Carrier.MOBIFONE);
		carriers.put("093", Carrier.MOBIFONE);
		carriers.put("070", Carrier.MOBIFONE);
		carriers.put("076", Carrier.MOBIFONE);
		carriers.put("077", Carrier.MOBIFONE);
		carriers.put("078", Carrier.MOBIFONE);
		carriers.put("079", Carrier.MOBIFONE);

		carriers.put("088", Carrier.VINAPHONE);
		carriers.put("091", Carrier.VINAPHONE);
		carriers.put("094", Carrier.VINAPHONE);
		carriers.put("081", Carrier.VINAPHONE);
		carriers.put("082", Carrier.VINAPHONE);
		carriers.put("083", Carrier.VINAPHONE);
		carriers.put("084", Carrier.VINAPHONE);
		carriers.put("085", Carrier.VINAPHONE);

		carriers.put("092", Carrier.VIETNAM_MOBILE);
		carriers.put("056", Carrier.VIETNAM_MOBILE);
		carriers.put("058", Carrier.VIETNAM_MOBILE);

		carriers.put("099", Carrier.GTEL);
		carriers.put("059", Carrier.GTEL);
	}

	public static Carrier of(String phoneNumber) {
		if (StringUtils.isEmpty(phoneNumber)
						|| !(phoneNumber.startsWith("0")
						|| phoneNumber.startsWith("84")
						|| phoneNumber.startsWith("+84")))
			return Carrier.UNKNOWN;

		phoneNumber = phoneNumber.replaceAll("\\+", "");
		if (phoneNumber.startsWith("84")) phoneNumber = phoneNumber.replaceFirst("84", "0");
		if (phoneNumber.length() != 10) return Carrier.UNKNOWN;
		Carrier carrier = carriers.get(phoneNumber.substring(0, 3));

		return carrier != null ? carrier : Carrier.UNKNOWN;
	}
}
