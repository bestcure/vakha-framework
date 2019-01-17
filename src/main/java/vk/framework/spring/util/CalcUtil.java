package vk.framework.spring.util;

public class CalcUtil {
	public static int getDiscountAmt(Integer amt, Integer discountRate) {
		return amt.intValue() - amt.intValue() * (discountRate.intValue() / 100);
	}
}