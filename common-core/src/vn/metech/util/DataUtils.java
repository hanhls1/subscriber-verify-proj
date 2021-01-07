package vn.metech.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataUtils {
	private DataUtils() {
	}

	// integer type data
	public static Integer toInt(String s, boolean defaultVal) {
		if (StringUtils.isEmpty(s)) {
			if (defaultVal)
				return 0;

			return null;
		}

		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static Integer toIntOrDefault(String s) {
		return toInt(s, true);
	}

	public static Integer toInt(String s) {
		return toInt(s, false);
	}

	// double type data
	public static Double toDouble(String s, boolean defaultVal) {
		if (StringUtils.isEmpty(s)) {
			if (defaultVal)
				return (double) 0;

			return null;
		}

		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return (double) 0;
		}
	}

	public static Double toDoubleOrDefault(String s) {
		return toDouble(s, true);
	}

	public static Double toDouble(String s) {
		return toDouble(s, false);
	}

	// list type data
	public static <T> List<T> nonNull(List<T> clt) {
		if (clt == null) {
			return Collections.emptyList();
		}

		return clt;
	}
}
