package vk.framework.spring.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class StringUtil {
	public static final String EMPTY = "";

	public static String cutString(String value, Integer length) {
		if (value != null && !value.equals("")) {
			try {
				value = value.trim();
				if (value.length() > length.intValue()) {
					value = value.substring(0, length.intValue()) + "..";
				}

				return value;
			} catch (Exception arg2) {
				arg2.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String remove(String str, char remove) {
		if (!isEmpty(str) && str.indexOf(remove) != -1) {
			char[] chars = str.toCharArray();
			int pos = 0;

			for (int i = 0; i < chars.length; ++i) {
				if (chars[i] != remove) {
					chars[pos++] = chars[i];
				}
			}

			return new String(chars, 0, pos);
		} else {
			return str;
		}
	}

	public static String removeCommaChar(String str) {
		return remove(str, ',');
	}

	public static String removeMinusChar(String str) {
		return remove(str, '-');
	}

	public static String replace(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		String srcStr = source;

		while (srcStr.indexOf(subject) >= 0) {
			preStr = srcStr.substring(0, srcStr.indexOf(subject));
			nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
			srcStr = nextStr;
			rtnStr.append(preStr).append(object);
		}

		rtnStr.append(nextStr);
		return rtnStr.toString();
	}

	public static String replaceOnce(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		if (source.indexOf(subject) >= 0) {
			preStr = source.substring(0, source.indexOf(subject));
			String nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
			rtnStr.append(preStr).append(object).append(nextStr);
			return rtnStr.toString();
		} else {
			return source;
		}
	}

	public static String replaceChar(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String srcStr = source;

		for (int i = 0; i < subject.length(); ++i) {
			char chA = subject.charAt(i);
			if (srcStr.indexOf(chA) >= 0) {
				preStr = srcStr.substring(0, srcStr.indexOf(chA));
				String nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
				srcStr = rtnStr.append(preStr).append(object).append(nextStr).toString();
			}
		}

		return srcStr;
	}

	public static int indexOf(String str, String searchStr) {
		return str != null && searchStr != null ? str.indexOf(searchStr) : -1;
	}

	public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
		return sourceStr == null && compareStr == null
				? returnStr
				: (sourceStr == null && compareStr != null
						? defaultStr
						: (sourceStr.trim().equals(compareStr) ? returnStr : defaultStr));
	}

	public static String decode(String sourceStr, String compareStr, String returnStr) {
		return decode(sourceStr, compareStr, returnStr, sourceStr);
	}

	public static String isNullToString(Object object) {
		String string = "";
		if (object != null) {
			string = object.toString().trim();
		}

		return string;
	}

	public static String nullConvert(Object src) {
		return src != null && src instanceof BigDecimal
				? ((BigDecimal) src).toString()
				: (src != null && !src.equals("null") ? ((String) src).trim() : "");
	}

	public static String nullConvert(String src) {
		return src != null && !src.equals("null") && !"".equals(src) && !" ".equals(src) ? src.trim() : "";
	}

	public static int zeroConvert(Object src) {
		return src != null && !src.equals("null") ? Integer.parseInt(((String) src).trim()) : 0;
	}

	public static int zeroConvert(String src) {
		return src != null && !src.equals("null") && !"".equals(src) && !" ".equals(src)
				? Integer.parseInt(src.trim())
				: 0;
	}

	public static int thndConvert(String src) {
		return src != null && !src.equals("null") && !"".equals(src) && !" ".equals(src)
				? Integer.parseInt(src.trim())
				: 1000;
	}

	public static String removeWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		} else {
			int sz = str.length();
			char[] chs = new char[sz];
			int count = 0;

			for (int i = 0; i < sz; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					chs[count++] = str.charAt(i);
				}
			}

			if (count == sz) {
				return str;
			} else {
				return new String(chs, 0, count);
			}
		}
	}

	public static String checkHtmlView(String strString) {
		String strNew = "";

		try {
			StringBuffer ex = new StringBuffer("");
			int len = strString.length();

			for (int i = 0; i < len; ++i) {
				char chrBuff = strString.charAt(i);
				switch (chrBuff) {
					case '\n' :
						ex.append("<br>");
						break;
					case ' ' :
						ex.append("&nbsp;");
						break;
					case '\"' :
						ex.append("&quot;");
						break;
					case '<' :
						ex.append("&lt;");
						break;
					case '>' :
						ex.append("&gt;");
						break;
					default :
						ex.append(chrBuff);
				}
			}

			strNew = ex.toString();
			return strNew;
		} catch (Exception arg5) {
			return null;
		}
	}

	public static String[] split(String source, String separator) throws Exception {
		String[] returnVal = null;
		int cnt = 1;
		int index = source.indexOf(separator);

		int index0;
		for (index0 = 0; index >= 0; index = source.indexOf(separator, index + 1)) {
			++cnt;
		}

		returnVal = new String[cnt];
		cnt = 0;

		for (index = source.indexOf(separator); index >= 0; ++cnt) {
			returnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
		}

		returnVal[cnt] = source.substring(index0);
		return returnVal;
	}

	public static String lowerCase(String str) {
		return str == null ? null : str.toLowerCase();
	}

	public static String upperCase(String str) {
		return str == null ? null : str.toUpperCase();
	}

	public static String stripStart(String str, String stripChars) {
		int strLen = str.length();
		if (str != null && str.length() != 0) {
			int start = 0;
			if (stripChars == null) {
				while (start != strLen && Character.isWhitespace(str.charAt(start))) {
					++start;
				}
			} else {
				if (stripChars.length() == 0) {
					return str;
				}

				while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
					++start;
				}
			}

			return str.substring(start);
		} else {
			return str;
		}
	}

	public static String stripEnd(String str, String stripChars) {
		int end = str.length();
		if (str != null && end != 0) {
			if (stripChars == null) {
				while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
					--end;
				}
			} else {
				if (stripChars.length() == 0) {
					return str;
				}

				while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
					--end;
				}
			}

			return str.substring(0, end);
		} else {
			return str;
		}
	}

	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		} else {
			String srcStr = stripStart(str, stripChars);
			return stripEnd(srcStr, stripChars);
		}
	}

	public static String stripTags(String str) {
		return isEmpty(str) ? str : Jsoup.clean(str, Whitelist.none());
	}

	public static String[] split(String source, String separator, int arraylength) throws Exception {
		String[] returnVal = new String[arraylength];
		int cnt = 0;
		int index0 = 0;

		for (int index = source.indexOf(separator); index >= 0 && cnt < arraylength - 1; ++cnt) {
			returnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
		}

		returnVal[cnt] = source.substring(index0);
		if (cnt < arraylength - 1) {
			for (int i = cnt + 1; i < arraylength; ++i) {
				returnVal[i] = "";
			}
		}

		return returnVal;
	}

	public static String getRandomStr(char startChr, char endChr) {
		String randomStr = null;
		int startInt = Integer.valueOf(startChr).intValue();
		int endInt = Integer.valueOf(endChr).intValue();
		if (startInt > endInt) {
			throw new IllegalArgumentException("Start String: " + startChr + " End String: " + endChr);
		} else {
			try {
				SecureRandom e = new SecureRandom();

				int randomInt;
				do {
					randomInt = e.nextInt(endInt + 1);
				} while (randomInt < startInt);

				randomStr = (char) randomInt + "";
				return randomStr;
			} catch (Exception arg6) {
				throw new RuntimeException(arg6);
			}
		}
	}

	public static String getEncdDcd(String srcString, String srcCharsetNm, String cnvrCharsetNm) {
		String rtnStr = null;
		if (srcString == null) {
			return null;
		} else {
			try {
				rtnStr = new String(srcString.getBytes(srcCharsetNm), cnvrCharsetNm);
			} catch (UnsupportedEncodingException arg4) {
				rtnStr = null;
			}

			return rtnStr;
		}
	}

	public static String getSpclStrCnvr(String srcString) {
		String rtnStr = null;

		try {
			StringBuffer e = new StringBuffer("");
			int len = srcString.length();

			for (int i = 0; i < len; ++i) {
				char chrBuff = srcString.charAt(i);
				switch (chrBuff) {
					case '&' :
						e.append("&amp;");
						break;
					case '<' :
						e.append("&lt;");
						break;
					case '>' :
						e.append("&gt;");
						break;
					default :
						e.append(chrBuff);
				}
			}

			rtnStr = e.toString();
			return rtnStr;
		} catch (Exception arg5) {
			throw new RuntimeException(arg5);
		}
	}

	public static String getTimeStamp() {
		String rtnStr = null;
		String pattern = "yyyyMMddhhmmssSSS";

		try {
			SimpleDateFormat e = new SimpleDateFormat(pattern, Locale.KOREA);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			rtnStr = e.format(Long.valueOf(ts.getTime()));
			return rtnStr;
		} catch (Exception arg3) {
			throw new RuntimeException(arg3);
		}
	}

	public static String getHtmlStrCnvr(String srcString) {
		String tmpString = srcString;

		try {
			tmpString = tmpString.replaceAll("&lt;", "<");
			tmpString = tmpString.replaceAll("&gt;", ">");
			tmpString = tmpString.replaceAll("&amp;", "&");
			tmpString = tmpString.replaceAll("&nbsp;", " ");
			tmpString = tmpString.replaceAll("&apos;", "\'");
			tmpString = tmpString.replaceAll("&quot;", "\"");
			return tmpString;
		} catch (Exception arg2) {
			throw new RuntimeException(arg2);
		}
	}

	public static String intToString(int num, int digits) {
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		DecimalFormat df = new DecimalFormat(String.valueOf(zeros));
		return df.format((long) num);
	}

	public static String makeAuthCode() {
		String[] tmp = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		int length = tmp.length;
		String returnVal = "";

		for (int i = 0; i < 6; ++i) {
			returnVal = returnVal + String.valueOf((int) (Math.random() * (double) length));
		}

		return returnVal;
	}

	public static String uniqueId() {
		return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
	}

	public static String lpad(String s, int n) {
		return paddingString(s, n, ' ', true);
	}

	public static String lpad(String s, int n, char c) {
		return paddingString(s, n, c, true);
	}

	public static int length(String str) {
		int count = 0;

		for (int i = 0; i < str.length(); ++i) {
			if (str.charAt(i) > 127) {
				count += 2;
			} else {
				++count;
			}
		}

		return count;
	}

	public static String rpad(String s, int n) {
		return paddingString(s, n, ' ', false);
	}

	public static String rpad(String s, int n, char c) {
		return paddingString(s, n, c, false);
	}

	private static String paddingString(String s, int n, char c, boolean paddingLeft) {
		if (s == null) {
			return s;
		} else {
			int add = n - s.length();
			if (add <= 0) {
				return s;
			} else {
				StringBuffer str = new StringBuffer(s);
				char[] ch = new char[add];
				Arrays.fill(ch, c);
				if (paddingLeft) {
					str.insert(0, ch);
				} else {
					str.append(ch);
				}

				return str.toString();
			}
		}
	}

	public static String phoneFormat(String str) {
		if (str.indexOf("02") == 0) {
			if (str.length() < 10) {
				str = str.replaceAll("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
			} else {
				str = str.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "$1-$2-$3");
			}
		} else if (str.length() < 11) {
			str = str.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
		} else {
			str = str.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
		}

		return str;
	}
}