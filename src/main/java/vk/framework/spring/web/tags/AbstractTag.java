package vk.framework.spring.web.tags;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;

public abstract class AbstractTag extends SimpleTagSupport implements DynamicAttributes {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private RequestContext requestContext;
	public static final String REQUEST_CONTEXT_PAGE_ATTRIBUTE = "org.springframework.web.servlet.tags.REQUEST_CONTEXT";
	protected static final String COLUMN_ROW_NUMBER = "__ROW_NUMBER__";
	protected static final String LINE_SEP = "\n";
	private static final String ATTR_FORMAT = "%s=\"%s\"";
	private final HashMap<String, Object> attributes = new HashMap();

	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		this.attributes.put(localName, value);
	}

	public String getDynamicAttribute() {
		StringWriter writer = new StringWriter();
		if (!this.attributes.isEmpty()) {
			writer.append(" ");
		}

		Iterator arg1 = this.attributes.keySet().iterator();

		while (arg1.hasNext()) {
			String attrName = (String) arg1.next();
			writer.append(String.format("%s=\"%s\"", new Object[]{attrName, this.attributes.get(attrName)}));
		}

		return writer.toString();
	}

	public HttpServletRequest getRequest() throws JspException {
		return (HttpServletRequest) this.getPageContext().getRequest();
	}

	public PageContext getPageContext() throws JspException {
		return (PageContext) super.getJspContext();
	}

	public MessageSource getMessageSource() throws JspException {
		this.requestContext = (RequestContext) this.getPageContext()
				.getAttribute("org.springframework.web.servlet.tags.REQUEST_CONTEXT");
		if (this.requestContext == null) {
			this.requestContext = new JspAwareRequestContext(this.getPageContext());
			this.getPageContext().setAttribute("org.springframework.web.servlet.tags.REQUEST_CONTEXT",
					this.requestContext);
		}

		return this.requestContext.getMessageSource();
	}

	public String convertMultiLangText(String value) throws JspException {
		try {
			value = this.getMessageSource().getMessage(value, (Object[]) null, LocaleContextHolder.getLocale());
			return value;
		} catch (Exception arg2) {
			return value;
		}
	}

	public void writeAttribute(StringWriter writer, String attributeName, String attributeValue) {
		if (!StringUtils.isEmpty(attributeValue)) {
			writer.append(" ").append(attributeName).append("=\"").append(attributeValue).append("\"");
		}

	}
}