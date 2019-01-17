package vk.framework.spring.security;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vk.framework.spring.security.UserDetail;

@Component
public class UserDetailHelper {
	public static UserDetail getAuthenticatedUser() {
		UserDetail user = new UserDetail();

		try {
			BeanUtils.copyProperties(user, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		} catch (Exception arg1) {
			user = null;
		}

		return user;
	}

	public static Boolean isAuthenticated() {
		return getAuthenticatedUser() == null ? Boolean.FALSE : Boolean.TRUE;
	}
}