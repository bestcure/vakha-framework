package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class FormTag extends AbstractFormTag {
	private String method = "post";
	private String action;
	private String target;
	private String enctype;

	public final void doTag() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		HttpServletRequest request = super.getRequest();
		HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
		CsrfToken token = csrfTokenRepository.loadToken(request);
		if (token == null) {
			token = csrfTokenRepository.generateToken(request);
		}

		super.writer.append("<form");
		super.writeBaseAttribute();
		super.writer.append(this.getDynamicAttribute());
		super.writeAttribute(this.writer, "method", this.getMethod());
		super.writeAttribute(this.writer, "action", this.getAction());
		super.writeAttribute(this.writer, "target", this.getTarget());
		super.writeAttribute(this.writer, "enctype", this.getEnctype());
		super.writer.append(">");
		body.invoke(this.writer);
		Enumeration parameterNames = request.getParameterNames();

		while (true) {
			String paramName;
			String paramValue;
			do {
				do {
					if (!parameterNames.hasMoreElements()) {
						super.writer.append("<input type=\"hidden\" name=\"" + token.getParameterName() + "\" value=\""
								+ token.getToken() + "\" data-creator=\"framework\"/>");
						super.writer.append("</form>");
						super.getJspContext().getOut().print(super.writer);
						return;
					}

					paramName = (String) parameterNames.nextElement();
					paramValue = request.getParameter(paramName);
				} while (this.writer.getBuffer().indexOf(paramName) != -1);
			} while (paramName.indexOf("search") <= -1 && paramName.indexOf("_menu") <= -1);

			super.writer.append("<input type=\"hidden\" name=\"" + paramName + "\" id=\"" + paramName + "\" value=\""
					+ paramValue + "\" data-creator=\"framework\" />");
		}
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getEnctype() {
		return this.enctype;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
}