package vk.framework.spring.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShortUrlUtils {
	@Autowired
	protected Properties envProp;

	public String getNaverShortUrl(String target) throws Exception {
		String clientId = this.envProp.getProperty("naver.api.client.id");
		String clientSecret = this.envProp.getProperty("naver.api.client.secret");
		target = target.replace("&amp;", "&");
		if (target.contains("?")) {
			target = target.substring(0, target.indexOf("?"))
					+ URLEncoder.encode(target.substring(target.indexOf("?")), "UTF-8");
		}

		try {
			String e = "https://openapi.naver.com/v1/util/shorturl";
			URL url = new URL(e);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			String postParams = "url=" + target;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			StringBuffer response = new StringBuffer();

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			br.close();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = (JsonNode) mapper.readValue(response.toString(), JsonNode.class);
			return jsonNode.get("message").asText().equals("ok") ? jsonNode.findValue("url").asText() : null;
		} catch (Exception arg14) {
			System.out.println(arg14);
			return null;
		}
	}
}