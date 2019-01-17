package vk.framework.spring.util;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class TagSupportUtil {
	public static Map<String, String> getGender() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("M", "남자");
		rMap.put("F", "여자");
		return rMap;
	}

	public static Map<String, String> getUseYn() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("Y", "예");
		rMap.put("N", "아니오");
		return rMap;
	}

	public static Map<String, String> getPublicYn() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("Y", "공개");
		rMap.put("N", "비공개");
		return rMap;
	}

	public static Map<String, String> getHour() {
		LinkedHashMap rMap = new LinkedHashMap();

		for (int i = 0; i < 24; ++i) {
			rMap.put(String.valueOf(i), i + "시");
		}

		return rMap;
	}

	public static Map<String, String> getTelAreaCode() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("02", "02");
		rMap.put("031", "031");
		rMap.put("032", "032");
		rMap.put("033", "033");
		rMap.put("041", "041");
		rMap.put("042", "042");
		rMap.put("043", "043");
		rMap.put("051", "051");
		rMap.put("052", "052");
		rMap.put("053", "053");
		rMap.put("054", "054");
		rMap.put("055", "055");
		rMap.put("061", "061");
		rMap.put("062", "062");
		rMap.put("063", "063");
		rMap.put("064", "064");
		rMap.put("070", "070");
		return rMap;
	}

	public static Map<String, String> getHpAreaCode() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("010", "010");
		rMap.put("011", "011");
		rMap.put("016", "016");
		rMap.put("017", "017");
		rMap.put("018", "018");
		rMap.put("019", "019");
		return rMap;
	}

	public static Map<String, String> getFivePointScale() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("5", "매우 잘함");
		rMap.put("4", "잘함");
		rMap.put("3", "보통");
		rMap.put("2", "부족");
		rMap.put("1", "매우 부족");
		return rMap;
	}

	public static Map<String, String> getOnlineBannerType() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("W", "WINDOW");
		rMap.put("P", "POPUP");
		rMap.put("E", "PAGE");
		return rMap;
	}

	public static Map<String, String> getForeign() {
		LinkedHashMap rMap = new LinkedHashMap();
		rMap.put("L", "내국인");
		rMap.put("F", "외국인");
		return rMap;
	}

	public static Map<String, String> getYearList() {
		Calendar cal = Calendar.getInstance();
		Integer year = Integer.valueOf(cal.get(1));
		LinkedHashMap rMap = new LinkedHashMap();

		for (Integer i = Integer.valueOf(year.intValue() + 2); i.intValue() > year.intValue() - 5; i = Integer
				.valueOf(i.intValue() - 1)) {
			rMap.put(String.valueOf(i), i + "년");
		}

		return rMap;
	}

	public static Map<String, String> getReturnYearList() {
		Calendar cal = Calendar.getInstance();
		Integer year = Integer.valueOf(cal.get(1));
		LinkedHashMap rMap = new LinkedHashMap();

		for (Integer i = Integer.valueOf(year.intValue() + 4); i.intValue() > year.intValue() - 7; i = Integer
				.valueOf(i.intValue() - 1)) {
			rMap.put(String.valueOf(i), i + "년");
		}

		return rMap;
	}

	public static Map<String, String> getLessonList() {
		LinkedHashMap rMap = new LinkedHashMap();

		for (Integer i = Integer.valueOf(1); i.intValue() < 16; i = Integer.valueOf(i.intValue() + 1)) {
			rMap.put(String.valueOf(i), String.valueOf(i));
		}

		return rMap;
	}
}