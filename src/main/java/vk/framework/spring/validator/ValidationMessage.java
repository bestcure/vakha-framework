package vk.framework.spring.validator;

public class ValidationMessage {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	private String fieldName;
	private String message;

	public ValidationMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}