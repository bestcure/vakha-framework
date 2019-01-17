package vk.framework.spring.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtils {
	private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

	public static String sha256Hash(String value, String salt) {
		Sha256Hash sha256Hash = null;
		if (StringUtils.isEmpty(salt)) {
			sha256Hash = new Sha256Hash(value);
		} else {
			sha256Hash = new Sha256Hash(value, salt);
		}

		return sha256Hash.toHex();
	}

	public static String sha256Hash(String value) {
		return sha256Hash(value, (String) null);
	}

	public static String aesEncrypt(String hexKey, String value) {
		AesCipherService aesCipherService = new AesCipherService();
		return aesCipherService.encrypt(value.getBytes(), Hex.decode(hexKey)).toHex();
	}

	public static String aesDecrypt(String hexKey, String value) {
		String returnValue = "";
		AesCipherService aesCipherService = new AesCipherService();

		try {
			returnValue = new String(aesCipherService.decrypt(Hex.decode(value), Hex.decode(hexKey)).getBytes());
		} catch (Exception arg4) {
			log.warn(arg4.getMessage());
			returnValue = value;
		}

		return returnValue;
	}

	public static String secureRandom(int numBytes) {
		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		return randomNumberGenerator.nextBytes(numBytes).toHex();
	}

	public static String secureRandom() {
		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		return randomNumberGenerator.nextBytes().toHex();
	}
}