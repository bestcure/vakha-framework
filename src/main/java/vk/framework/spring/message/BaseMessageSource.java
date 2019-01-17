package vk.framework.spring.message;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import vk.framework.spring.message.DatabaseMessageSource;

public class BaseMessageSource implements MessageSource {
	private DatabaseMessageSource databaseMessageSource;

	public void setDatabaseMessageSource(DatabaseMessageSource databaseMessageSource) {
		this.databaseMessageSource = databaseMessageSource;
	}

	public DatabaseMessageSource getDatabaseMessageSource() {
		return this.databaseMessageSource;
	}

	public String getMessage(String code) {
		String message = "";

		try {
			message = this.getDatabaseMessageSource().getMessage(code, (Object[]) null,
					LocaleContextHolder.getLocale());
		} catch (Exception arg3) {
			message = code;
		}

		return message;
	}

	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return this.getDatabaseMessageSource().getMessage(code, args, defaultMessage, locale);
	}

	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return this.getDatabaseMessageSource().getMessage(code, args, locale);
	}

	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return this.getDatabaseMessageSource().getMessage(resolvable, locale);
	}
}