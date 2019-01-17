package vk.framework.spring.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;

import vk.framework.spring.web.filter.CustomWhitelist;

public class HTMLTagFilterRequestWrapper extends HttpServletRequestWrapper {
	public HTMLTagFilterRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		} else {
			OutputSettings outputSettings = new OutputSettings();
			outputSettings.prettyPrint(false);

			for (int i = 0; i < values.length; ++i) {
				if (values[i] != null) {
					values[i] = Jsoup.clean(values[i], "", CustomWhitelist.full(), outputSettings);
				} else {
					values[i] = null;
				}
			}

			return values;
		}
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		} else {
			value = Jsoup.clean(value, CustomWhitelist.full());
			return value;
		}
	}
}