package vk.framework.spring.util;

public class ArrayUtils {
	public static boolean isDuplicateArray(Integer[] values) {
		boolean isDuplicated = false;
		Integer tempOrder = null;
		if (values == null) {
			return isDuplicated;
		} else {
			for (int i = 0; i < values.length; ++i) {
				tempOrder = values[i];

				for (int j = 0; j < values.length; ++j) {
					if (i != j && tempOrder == values[j]) {
						isDuplicated = true;
						break;
					}
				}

				if (isDuplicated) {
					break;
				}
			}

			return isDuplicated;
		}
	}
}