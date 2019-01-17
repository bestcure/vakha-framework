package vk.framework.spring.validator.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vk.framework.spring.validator.annotation.ResidentNumber;

public class ResidentNumberValidator implements ConstraintValidator<ResidentNumber, String> {
	public void initialize(ResidentNumber residentNumber) {
	}

	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		if (value != null && !value.equals("")) {
			String regex = "\\d{6}[1234]\\d{6}";
			if (!value.matches(regex)) {
				return false;
			} else {
				try {
					String charArray = value.substring(0, 6);
					charArray = (value.charAt(6) != 49 && value.charAt(6) != 50 ? "20" : "19") + charArray;
					charArray = charArray.substring(0, 4) + "/" + charArray.substring(4, 6) + "/"
							+ charArray.substring(6, 8);
					SimpleDateFormat sum = new SimpleDateFormat("yyyy/MM/dd");
					Date date = sum.parse(charArray);
					String arrDivide = sum.format(date);
					if (!arrDivide.equalsIgnoreCase(charArray)) {
						return false;
					}
				} catch (ParseException arg8) {
					arg8.printStackTrace();
					return false;
				}

				char[] arg9 = value.toCharArray();
				long arg10 = 0L;
				int[] arg11 = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5};

				int checkdigit;
				for (checkdigit = 0; checkdigit < arg9.length - 1; ++checkdigit) {
					arg10 += (long) (Integer.parseInt(String.valueOf(arg9[checkdigit])) * arg11[checkdigit]);
				}

				checkdigit = (int) (11L - arg10 % 11L) % 10;
				return checkdigit == Integer.parseInt(String.valueOf(arg9[12]));
			}
		} else {
			return true;
		}
	}
}