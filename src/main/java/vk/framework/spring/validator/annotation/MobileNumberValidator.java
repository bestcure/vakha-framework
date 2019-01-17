package vk.framework.spring.validator.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vk.framework.spring.validator.annotation.MobileNumber;

public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {
	public void initialize(MobileNumber mobileNumber) {
	}

	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		return value != null && !value.equals("") ? value.matches("(01[016789])-(\\d{3,4})-(\\d{4})") : true;
	}
}