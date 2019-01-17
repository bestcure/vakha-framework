package vk.framework.spring.web;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import vk.framework.spring.validator.MultipleDateEditor;

public class WebBindingInitializer extends ConfigurableWebBindingInitializer {
	private final Logger log;
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	public WebBindingInitializer() {
		this.log = LoggerFactory.getLogger(this.getClass());
	}

	public void initBinder(WebDataBinder binder, WebRequest request) {
		super.initBinder(binder, request);
		binder.setAutoGrowCollectionLimit(1000000);
		binder.setAutoGrowNestedPaths(true);
		binder.registerCustomEditor(String.class, (PropertyEditor) new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(String.class, (PropertyEditor) new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, (PropertyEditor) new MultipleDateEditor());
		binder.setBindingErrorProcessor((BindingErrorProcessor) new DefaultBindingErrorProcessor() {

			public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {
				WebBindingInitializer.this.log.debug(ex.getMessage());
				if ("typeMismatch".equals(ex.getErrorCode())) {
					String message = "";
					if ("java.util.Date".equals(bindingResult.getFieldType(ex.getPropertyName()).getName())) {
						message = WebBindingInitializer.this.messageSource.getMessage("typeMismatch.java.util.Date",
								null, Locale.getDefault());
					} else if ("java.lang.Integer".equals(bindingResult.getFieldType(ex.getPropertyName()).getName())) {
						message = WebBindingInitializer.this.messageSource.getMessage("typeMismatch.java.lang.Integer",
								null, Locale.getDefault());
					} else if ("[Ljava.lang.Integer;"
							.equals(bindingResult.getFieldType(ex.getPropertyName()).getName())) {
						message = WebBindingInitializer.this.messageSource.getMessage("typeMismatch.java.lang.Integer",
								null, Locale.getDefault());
					}
					FieldError fieldError = new FieldError(bindingResult.getObjectName(), ex.getPropertyName(),
							message);
					bindingResult.addError((ObjectError) fieldError);
				} else {
					super.processPropertyAccessException(ex, bindingResult);
				}
			}
		});
	}

}