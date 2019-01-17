package vk.framework.spring.web.functions;

import java.util.Date;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import vk.framework.spring.util.DateUtil;

public class DateTimeFunctions {
	public static String defaultStringDateFormat(String date) throws Exception {
		return date != null && !date.equals("") ? DateUtil.dateFormat(date) : null;
	}

	public static String defaultDateFormat(Object date) throws Exception {
		if (date == null) {
			return null;
		} else {
			if (date instanceof String) {
				date = DateUtil.parseDateTime((String) date);
			}

			FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd");
			return dateFormat.format(date);
		}
	}

	public static String defaultDateFormat(Date date) {
		if (date == null) {
			return null;
		} else {
			FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd");
			return dateFormat.format(date);
		}
	}

	public static String defaultDateTimeFormat(Object date) throws Exception {
		if (date == null) {
			return null;
		} else {
			if (date instanceof String) {
				date = DateUtil.parseDateTime((String) date);
			}

			FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd HH:mm");
			return dateFormat.format(date);
		}
	}

	public static String defaultDateTimeFormat(Date date) {
		if (date == null) {
			return null;
		} else {
			FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd HH:mm");
			return dateFormat.format(date);
		}
	}

	public static String defaultDateTimeSecondFormat(Object date) throws Exception {
		if (date == null) {
			return null;
		} else {
			if (date instanceof String) {
				date = DateUtil.parseDateTime((String) date);
			}

			FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
			return dateFormat.format(date);
		}
	}

	public static String defaultDateTimeSecondFormat(Date date) {
		if (date == null) {
			return null;
		} else {
			FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
			return dateFormat.format(date);
		}
	}

	public static String dateFormat(Object date, String format) throws Exception {
		if (date == null) {
			return null;
		} else {
			if (date instanceof String) {
				date = DateUtil.parseDateTime((String) date);
			}

			FastDateFormat dateFormat = FastDateFormat.getInstance(format);
			return dateFormat.format(date);
		}
	}

	public static String dateFormat(Date date, String format) {
		if (date == null) {
			return null;
		} else {
			FastDateFormat dateFormat = FastDateFormat.getInstance(format);
			return dateFormat.format(date);
		}
	}

	public static String formatElapsedTime(Long second) {
		return second == null ? null : DurationFormatUtils.formatDuration(second.longValue() * 1000L, "mm:ss");
	}
}