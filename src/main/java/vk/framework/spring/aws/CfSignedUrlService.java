package vk.framework.spring.aws;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CfSignedUrlService {
	private String domain = "";
	private String keyPairId = "";
	private String privateKeyFile = "";
	private static Integer DEFAULT_EXPIRE_TIME = Integer.valueOf(60);
	@Autowired
	private HttpServletRequest request;

	public CfSignedUrlService(String domain, String keyPairId, String privateKeyFile) {
		this.domain = domain;
		this.keyPairId = keyPairId;
		this.privateKeyFile = privateKeyFile;
	}

	public String generatePreSignedUrl(String bucketName, String objectKey, Integer minutes) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.add(12, minutes.intValue());
		Date dateLessThan = cal.getTime();
		objectKey = objectKey.replace("//", "/");
		String serverScheme = this.request.getHeader("x-forwarded-proto");
		if (serverScheme == null || serverScheme.equals("")) {
			serverScheme = this.request.getScheme();
		}

		String resourceUrl = serverScheme + "://" + this.domain + objectKey;
		return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(resourceUrl, this.keyPairId,
				SignerUtils.loadPrivateKey(this.privateKeyFile), dateLessThan);
	}

	public String generatePreSignedUrl(String bucketName, String objectKey) throws Exception {
		return this.generatePreSignedUrl(bucketName, objectKey, DEFAULT_EXPIRE_TIME);
	}
}