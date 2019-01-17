package vk.framework.spring.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3SignedUrlService {
	private static Integer DEFAULT_EXPIRE_TIME = Integer.valueOf(60);
	@Autowired(required = false)
	private AmazonS3Client s3Client;
	private static final Pattern INLINE_FILE_PATTERN = Pattern.compile("\\.(pdf|jpg|gif|png)$", 2);

	public String generatePreSignedUrl(String bucketName, String objectKey, String fileName) throws Exception {
		return this.generatePreSignedUrl(bucketName, objectKey, fileName, DEFAULT_EXPIRE_TIME, false);
	}

	public String generatePreSignedUrl(String bucketName, String objectKey, String fileName, boolean headerInline)
			throws Exception {
		return this.generatePreSignedUrl(bucketName, objectKey, fileName, DEFAULT_EXPIRE_TIME, headerInline);
	}

	public String generatePreSignedUrl(String bucketName, String objectKey, String fileName, Integer minutes,
			boolean headerInline) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.add(12, minutes.intValue());
		Date dateLessThan = cal.getTime();
		objectKey = objectKey.replace("//", "/").trim();
		if (objectKey.startsWith("/")) {
			objectKey = objectKey.substring(1);
		}

		ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
		Matcher mtch = INLINE_FILE_PATTERN.matcher(fileName);
		if (headerInline) {
			if (!mtch.find()) {
				return null;
			}

			responseHeaders.setContentDisposition("inline;filename=");
		} else {
			responseHeaders.setContentDisposition("attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		}

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(dateLessThan);
		generatePresignedUrlRequest.setResponseHeaders(responseHeaders);
		URL s3Url = this.s3Client.generatePresignedUrl(generatePresignedUrlRequest);
		String url = s3Url.toString().replace("https://", "http://");
		return url;
	}
}