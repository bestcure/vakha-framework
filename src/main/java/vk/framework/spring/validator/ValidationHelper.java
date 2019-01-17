package vk.framework.spring.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vk.framework.spring.dataaccess.util.DataMap;
import vk.framework.spring.validator.ValidationMessage;
import vk.framework.spring.validator.ValidationResponse;

public class ValidationHelper {
	private BindingResult bindingResult;
	private ValidationResponse validationResponse;
	private List<ValidationMessage> errorMessages;
	private DataMap resultValue;

	public ValidationHelper() {
		this.validationResponse = new ValidationResponse();
		this.errorMessages = new ArrayList();
		this.resultValue = new DataMap();
	}

	public ValidationHelper(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
		this.validationResponse = new ValidationResponse();
		this.errorMessages = new ArrayList();
		this.resultValue = new DataMap();
		if (this.bindingResult.hasErrors()) {
			List allErrors = this.bindingResult.getFieldErrors();
			Iterator arg2 = allErrors.iterator();

			while (arg2.hasNext()) {
				FieldError objError = (FieldError) arg2.next();
				this.errorMessages.add(new ValidationMessage(objError.getField(), objError.getDefaultMessage()));
			}

			this.validationResponse.setResult(this.errorMessages);
		}

	}

	public void rejectValue(String fieldName, String message) {
		this.errorMessages.add(new ValidationMessage(fieldName, message));
	}

	public boolean hasErrors() {
		return this.errorMessages.size() > 0;
	}

	public void setResultMessage(String message) {
		this.validationResponse.setResult(message);
	}

	public void setResultValue(DataMap resultMap) {
		this.resultValue.putAll(resultMap);
	}

	public ValidationResponse getValidationResponse() {
		if (this.hasErrors()) {
			this.validationResponse.setStatus("FAIL");
			this.validationResponse.setResult(this.errorMessages);
		} else {
			this.validationResponse.setStatus("SUCCESS");
			this.validationResponse.setValue(this.resultValue);
		}

		return this.validationResponse;
	}
}