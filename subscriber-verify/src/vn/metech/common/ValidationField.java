package vn.metech.common;

import org.springframework.validation.FieldError;

public class ValidationField {

	private String param;
	private String error;
	private Object rejectValue;

	public ValidationField() {
	}

	public ValidationField(FieldError fieldError) {
		this(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
	}

	public ValidationField(String param, String error) {
		this.param = param;
		this.error = error;
	}

	public ValidationField(String param, String error, Object rejectValue) {
		this(param, error);
		this.rejectValue = rejectValue;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getRejectValue() {
		return rejectValue;
	}

	public void setRejectValue(Object rejectValue) {
		this.rejectValue = rejectValue;
	}
}
