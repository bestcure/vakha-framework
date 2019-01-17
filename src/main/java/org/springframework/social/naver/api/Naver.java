/*
 * @(#)Naver.java version 2017. 11. 5
 *
 * Copyright by mornya. All rights reserved. Since 2006.
 * This application is based by SAPP Solution.
 */
package org.springframework.social.naver.api;

import org.springframework.social.ApiBinding;
import org.springframework.social.naver.api.abstracts.UserOperation;

public interface Naver extends ApiBinding {
	UserOperation userOperation();
}
