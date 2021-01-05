package vn.metech.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:sss";

	private DateUtils() {
	}

	public static String formatDate(Long ms, String pattern) {
		return formatDate(new Date(ms), pattern);
	}

	public static String formatDate(Long ms) {
		return formatDate(new Date(ms));
	}

	public static String formatDate(Date date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_FORMAT);
	}

	public static Date parseDate(String s, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			return sdf.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseDate(String s) {
		Date d = parseDate(s, DEFAULT_FORMAT);
		d = d != null ? d : parseDate(s, DATE_TIME_FORMAT);
		d = d != null ? d : parseDate(s, DATE_FORMAT);

		return d;
	}


	public static Date add(Date d, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(field, amount);

		return c.getTime();
	}

	public static Date addSecond(Date d, int amount) {
		return add(d, Calendar.SECOND, amount);
	}

	public static Date addMinute(Date d, int amount) {
		return add(d, Calendar.MINUTE, amount);
	}

	public static Date addHour(Date d, int amount) {
		return add(d, Calendar.HOUR, amount);
	}

	public static Date addDay(Date d, int amount) {
		return add(d, Calendar.DAY_OF_YEAR, amount);
	}

	public static Date addMonth(Date d, int amount) {
		return add(d, Calendar.MONTH, amount);
	}

	public static Date beginDay(Date d) {
		if (d == null) {
			d = new Date();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date endDay(Date d) {
		if (d == null) {
			d = new Date();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 99);

		return calendar.getTime();
	}

	public static Date setter(Object d) {
		if (d instanceof Long) {
			return new Date((long) d);
		}

		if (d instanceof String) {
			return DateUtils.parseDate((String) d);
		}

		if (d instanceof Date) {
			return (Date) d;
		}

		return null;
	}

}
