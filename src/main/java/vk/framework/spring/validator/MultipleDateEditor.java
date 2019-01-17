package vk.framework.spring.validator;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;

public class MultipleDateEditor extends PropertyEditorSupport {
	public static final String DEFAULT_OUTPUT_FORMAT = "yyyy/MM/dd";
	public static final String[] DEFAULT_INPUT_FORMATS = new String[]{"yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm",
			"yyyy.MM.dd HH:mm", "yyyy/MM/dd", "yyyy.MM.dd", "yyyy-MM-dd", "dd-MM-yyyy"};
	private String outputFormat;
	private String[] inputFormats;
	private boolean allowEmpty;

	public MultipleDateEditor() {
		this.outputFormat = "yyyy/MM/dd";
		this.inputFormats = DEFAULT_INPUT_FORMATS;
		this.allowEmpty = true;
	}

	public MultipleDateEditor(String outputFormat, String[] inputFormats) {
		this.outputFormat = outputFormat;
		this.inputFormats = inputFormats;
		this.allowEmpty = false;
	}

	public MultipleDateEditor(String outputFormat, String[] inputFormats, boolean allowEmpty) {
		this.outputFormat = outputFormat;
		this.inputFormats = inputFormats;
		this.allowEmpty = allowEmpty;
	}

	public String getAsText() {
		return this.allowEmpty && this.getValue() == null
				? ""
				: DateFormatUtils.format((Date) this.getValue(), this.outputFormat);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		try {
			if (StringUtils.hasText(text)) {
				this.setValue(DateUtils.parseDate(text, this.inputFormats));
			} else {
				if (!this.allowEmpty) {
					throw new IllegalArgumentException("The text specified for parsing is null");
				}

				this.setValue((Object) null);
			}

		} catch (ParseException arg2) {
			throw new IllegalArgumentException(
					"Could not parse text [" + text + "] into any available date input formats", arg2);
		}
	}

	public boolean isAllowEmpty() {
		return this.allowEmpty;
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public String[] getInputFormats() {
		return this.inputFormats;
	}

	public void setInputFormats(String[] inputFormats) {
		this.inputFormats = inputFormats;
	}

	public String getOutputFormat() {
		return this.outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
}