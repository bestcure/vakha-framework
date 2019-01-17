package vk.framework.spring.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import vk.framework.spring.util.ByteUtils;

public class ObjectUtil {
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECURITY_KEY = "fb388cb8be1956c90164b4562073fda9";

	public static String serializeObjectToString(Object object) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
		gzipOutputStream.close();
		arrayOutputStream.close();
		objectOutputStream.close();
		SecretKeySpec key = new SecretKeySpec(ByteUtils.toBytes("fb388cb8be1956c90164b4562073fda9", 16), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(1, key);
		byte[] plain = arrayOutputStream.toByteArray();
		byte[] encrypt = cipher.doFinal(plain);
		return ByteUtils.toHexString(encrypt);
	}

	public static Object deserializeObjectFromString(String objectString) throws Exception {
		SecretKeySpec key = new SecretKeySpec(ByteUtils.toBytes("fb388cb8be1956c90164b4562073fda9", 16), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(2, key);
		byte[] decrypt = cipher.doFinal(ByteUtils.toBytesFromHexString(objectString));
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(decrypt);
		GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		gzipInputStream.close();
		arrayInputStream.close();
		return object;
	}
}