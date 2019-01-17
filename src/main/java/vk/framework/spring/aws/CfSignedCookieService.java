package vk.framework.spring.aws;

import com.amazonaws.services.cloudfront.CloudFrontCookieSigner;
import com.amazonaws.services.cloudfront.CloudFrontCookieSigner.CookiesForCustomPolicy;
import com.amazonaws.services.cloudfront.util.SignerUtils;

import vk.framework.spring.util.SessionCookieUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

public class CfSignedCookieService {
	private String domain = "";
	private String keyPairId = "";
	private String privateKeyFile = "";
	private static Integer DEFAULT_EXPIRE_TIME = Integer.valueOf(60);
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	public CfSignedCookieService(String domain, String keyPairId, String privateKeyFile) {
		this.domain = domain;
		this.keyPairId = keyPairId;
		this.privateKeyFile = privateKeyFile;
	}

	public CookiesForCustomPolicy generateSignedCookie(String bucketName, String objectKey, String revision,
			Integer minutes) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.add(12, -3);
		Date activeFrom = cal.getTime();
		cal.add(12, minutes.intValue());
		Date expiresOn = cal.getTime();
		String serverScheme = this.request.getHeader("x-forwarded-proto");
		if (serverScheme == null || serverScheme.equals("")) {
			serverScheme = this.request.getScheme();
		}

		String resourceUrl = serverScheme + "://" + this.domain + objectKey;
		if (objectKey.startsWith("/")) {
			objectKey = objectKey.substring(1);
		}

		String ipAddress = this.request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = this.request.getRemoteAddr();
		}

		CookiesForCustomPolicy cookies = CloudFrontCookieSigner.getCookiesForCustomPolicy(resourceUrl,
				SignerUtils.loadPrivateKey(this.privateKeyFile), this.keyPairId, expiresOn, activeFrom, "0.0.0.0/0");
		String revisionName = DigestUtils.md5DigestAsHex((objectKey + "_" + revision).getBytes());
		SessionCookieUtil.deleteCookie(this.request, this.response, (String) cookies.getKeyPairId().getKey(), true);
		SessionCookieUtil.deleteCookie(this.request, this.response, (String) cookies.getPolicy().getKey(), true);
		SessionCookieUtil.deleteCookie(this.request, this.response, (String) cookies.getSignature().getKey(), true);
		SessionCookieUtil.deleteCookie(this.request, this.response, "cf_revisions", true);
		SessionCookieUtil.setCookie(this.request, this.response, (String) cookies.getKeyPairId().getKey(),
				(String) cookies.getKeyPairId().getValue(), true);
		SessionCookieUtil.setCookie(this.request, this.response, (String) cookies.getPolicy().getKey(),
				(String) cookies.getPolicy().getValue(), true);
		SessionCookieUtil.setCookie(this.request, this.response, (String) cookies.getSignature().getKey(),
				(String) cookies.getSignature().getValue(), true);
		SessionCookieUtil.setCookie(this.request, this.response, "cf_revisions", revisionName, true);
		return cookies;
	}

	public CookiesForCustomPolicy generateSignedCookie(String bucketName, String objectKey, String revision)
			throws Exception {
		return this.generateSignedCookie(bucketName, objectKey, revision, DEFAULT_EXPIRE_TIME);
	}
}