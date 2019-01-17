package vk.framework.spring.validator;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.StringUtils;

public class LocalDateEditor extends PropertyEditorSupport {
	public void setAsText(String text) throws DateTimeParseException {
		if (StringUtils.isNotEmpty(text)) {
			this.setValue(LocalDate.parse(text));
		} else {
			this.setValue((Object) null);
		}

	}

	public String getAsText() {
		LocalDate value = (LocalDate) this.getValue();
		return value != null ? value.toString() : "";
	}
}