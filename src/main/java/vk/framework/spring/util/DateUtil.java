package vk.framework.spring.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	public static int getFirstDay(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(7, day);
		cal.set(8, 1);
		cal.set(2, month);
		cal.set(1, year);
		return cal.get(5);
	}

	public static String dateFormat(String date) throws Exception {
		if (date == null) {
			return null;
		}
		if (date.length() == 8) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date dt = formatter.parse(date);
			formatter = new SimpleDateFormat("yyyy.MM.dd");
			return formatter.format(dt);
		}
		return date;
	}

	public static String dateFormat(Object date) throws Exception {
		if (date == null) {
			return null;
		}
		return DateUtil.dateFormat(date.toString());
	}

	public static String timeFormat(String value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value.length() == 4) {
			value = value.substring(0, 2) + ":" + value.substring(3, 4);
		}
		return value;
	}

	public static String timeFormat(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		return DateUtil.timeFormat(value.toString());
	}

	public static Date parseDate(String date) throws Exception {
		if (date == null || date.equals("")) {
			return null;
		}
		String[] parsePatterns = new String[]{"yyyy/MM/dd", "yyyy-MM-dd", "yyyy.MM.dd", "yyyyMMdd", "yyMMdd"};
		return DateUtils.parseDate((String) date, (String[]) parsePatterns);
	}

	public static Date parseDateTime(String date) throws Exception {
		if (date == null || date.equals("")) {
			return null;
		}
		String[] parsePatterns = new String[]{"yyyy/MM/dd hh:mm:ss.S", "yyyy-MM-dd hh:mm:ss.S", "yyyy.MM.dd hh:mm:ss.S",
				"yyyyMMdd hh:mm:ss.S", "yyMMdd hh:mm:ss.S"};
		return DateUtils.parseDate((String) date, (String[]) parsePatterns);
	}

	public static Date getIncrementDays(String strDate, int days) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = null;
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		try {
			year = Integer.parseInt(strDate.substring(0, 4));
			month = Integer.parseInt(strDate.substring(5, 7));
			day = Integer.parseInt(strDate.substring(8, 10));
			hour = Integer.parseInt(strDate.substring(11, 13));
			minute = Integer.parseInt(strDate.substring(14, 16));
			second = Integer.parseInt(strDate.substring(17, 19));
		} catch (Exception e) {
			hour = 0;
			minute = 0;
			second = 0;
		}
		calendar.set(year, month - 1, day + days, hour, minute, second);
		try {
			date = calendar.getTime();
		} catch (Exception e) {
			// empty catch block
		}
		return date;
	}
}
