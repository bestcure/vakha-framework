package vk.framework.spring.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByteUtils {
	protected static final Log log = LogFactory.getLog(ByteUtils.class);
	public static Byte DEFAULT_BYTE = new Byte((byte) 0);

	public static byte toByte(String value, byte defaultValue) {
		try {
			return Byte.parseByte(value);
		} catch (Exception arg2) {
			return defaultValue;
		}
	}

	public static Byte toByteObject(String value, Byte defaultValue) {
		try {
			return new Byte(value);
		} catch (Exception arg2) {
			return defaultValue;
		}
	}

	public static int unsignedByte(byte b) {
		return b & 255;
	}

	public static int toInt(byte[] src, int srcPos) {
		int dword = 0;

		for (int i = 0; i < 4; ++i) {
			dword = (dword << 8) + (src[i + srcPos] & 255);
		}

		return dword;
	}

	public static int toInt(byte[] src) {
		return toInt(src, 0);
	}

	public static long toLong(byte[] src, int srcPos) {
		long qword = 0L;

		for (int i = 0; i < 8; ++i) {
			qword = (qword << 8) + (long) (src[i + srcPos] & 255);
		}

		return qword;
	}

	public static long toLong(byte[] src) {
		return toLong(src, 0);
	}

	public static void toBytes(int value, byte[] dest, int destPos) {
		for (int i = 0; i < 4; ++i) {
			dest[i + destPos] = (byte) (value >> (7 - i) * 8);
		}

	}

	public static byte[] toBytes(int value) {
		byte[] dest = new byte[4];
		toBytes(value, dest, 0);
		return dest;
	}

	public static void toBytes(long value, byte[] dest, int destPos) {
		for (int i = 0; i < 8; ++i) {
			dest[i + destPos] = (byte) ((int) (value >> (7 - i) * 8));
		}

	}

	public static byte[] toBytes(long value) {
		byte[] dest = new byte[8];
		toBytes(value, dest, 0);
		return dest;
	}

	public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		} else if (radix != 16 && radix != 10 && radix != 8) {
			throw new IllegalArgumentException("For input radix: \"" + radix + "\"");
		} else {
			int divLen = radix == 16 ? 2 : 3;
			int length = digits.length();
			if (length % divLen == 1) {
				throw new IllegalArgumentException("For input string: \"" + digits + "\"");
			} else {
				length /= divLen;
				byte[] bytes = new byte[length];

				for (int i = 0; i < length; ++i) {
					int index = i * divLen;
					bytes[i] = (byte) Short.parseShort(digits.substring(index, index + divLen), radix);
				}

				return bytes;
			}
		}
	}

	public static byte[] toBytesFromHexString(String digits) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		} else {
			int length = digits.length();
			if (length % 2 == 1) {
				throw new IllegalArgumentException("For input string: \"" + digits + "\"");
			} else {
				length /= 2;
				byte[] bytes = new byte[length];

				for (int i = 0; i < length; ++i) {
					int index = i * 2;
					bytes[i] = (byte) Short.parseShort(digits.substring(index, index + 2), 16);
				}

				return bytes;
			}
		}
	}

	public static String toHexString(byte b) {
		StringBuffer result = new StringBuffer(3);
		result.append(Integer.toString((b & 240) >> 4, 16));
		result.append(Integer.toString(b & 15, 16));
		return result.toString();
	}

	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		} else {
			StringBuffer result = new StringBuffer();
			byte[] arg1 = bytes;
			int arg2 = bytes.length;

			for (int arg3 = 0; arg3 < arg2; ++arg3) {
				byte b = arg1[arg3];
				result.append(Integer.toString((b & 240) >> 4, 16));
				result.append(Integer.toString(b & 15, 16));
			}

			return result.toString();
		}
	}

	public static String toHexString(byte[] bytes, int offset, int length) {
		if (bytes == null) {
			return null;
		} else {
			StringBuffer result = new StringBuffer();

			for (int i = offset; i < offset + length; ++i) {
				result.append(Integer.toString((bytes[i] & 240) >> 4, 16));
				result.append(Integer.toString(bytes[i] & 15, 16));
			}

			return result.toString();
		}
	}

	public static boolean equals(byte[] array1, byte[] array2) {
		if (array1 == array2) {
			return true;
		} else if (array1 != null && array2 != null) {
			if (array1.length != array2.length) {
				return false;
			} else {
				for (int i = 0; i < array1.length; ++i) {
					if (array1[i] != array2[i]) {
						return false;
					}
				}

				return true;
			}
		} else {
			return false;
		}
	}
}