package vk.framework.spring.web;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class UserArgumentResolver implements WebArgumentResolver {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		Class clazz = methodParameter.getParameterType();
		String paramName = methodParameter.getParameterName();
		this.log.debug("=============" + paramName);
		this.log.debug("=============" + clazz.getName());
		if (clazz.equals(ModelMap.class) && paramName.equals("vo")) {
			HashMap commandMap = new HashMap();
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			Enumeration enumeration = request.getParameterNames();

			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String[] values = request.getParameterValues(key);
				if (values != null) {
					commandMap.put(key, values.length > 1 ? values : values[0]);
					if (this.log.isDebugEnabled()) {
						this.log.debug("commandMap :" + commandMap.toString());
					}
				}
			}

			return commandMap;
		} else {
			return UNRESOLVED;
		}
	}
}