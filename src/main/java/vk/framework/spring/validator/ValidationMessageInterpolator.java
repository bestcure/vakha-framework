package vk.framework.spring.validator;

import java.util.Locale;
import javax.validation.MessageInterpolator.Context;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.stereotype.Component;

@Component("validationMessageInterpolator")
public class ValidationMessageInterpolator extends ResourceBundleMessageInterpolator {
	public String interpolate(String message, Context context) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + message);
		return super.interpolate(message, context);
	}

	public String interpolate(String message, Context context, Locale locale) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + message);
		return super.interpolate(message, context, locale);
	}
}