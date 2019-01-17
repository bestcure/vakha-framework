package vk.framework.spring.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil extends FileUtils {
	protected static final Log log = LogFactory.getLog(FileUtil.class);

	public static void createDirectory(String path) throws IOException {
		forceMkdir(new File(path));
	}

	public static boolean isExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static boolean renameFile(String fromDir, String toDir) {
		return renameFile(new File(fromDir), new File(toDir));
	}

	public static boolean renameFile(File fromFile, File toFile) {
		if (fromFile.isDirectory()) {
			File[] files = fromFile.listFiles();
			File[] arg2 = files;
			int arg3 = files.length;

			for (int arg4 = 0; arg4 < arg3; ++arg4) {
				File eachFile = arg2[arg4];
				File toFileChild = new File(toFile, eachFile.getName());
				if (!eachFile.renameTo(toFileChild)) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean renameDirectory(String fromDir, String toDir) {
		return renameDirectory(new File(fromDir), new File(toDir));
	}

	public static boolean renameDirectory(File fromFile, File toFile) {
		if (fromFile.isDirectory()) {
			File[] files = fromFile.listFiles();
			if (files == null) {
				return fromFile.renameTo(toFile);
			} else if (!toFile.mkdirs()) {
				return false;
			} else {
				File[] arg2 = files;
				int arg3 = files.length;

				for (int arg4 = 0; arg4 < arg3; ++arg4) {
					File eachFile = arg2[arg4];
					File toFileChild = new File(toFile, eachFile.getName());
					if (eachFile.isDirectory()) {
						if (!renameDirectory(eachFile, toFileChild)) {
							return false;
						}
					} else if (!eachFile.renameTo(toFileChild)) {
						return false;
					}
				}

				return fromFile.delete();
			}
		} else {
			return fromFile.getParent() != null && !toFile.mkdirs() ? false : fromFile.renameTo(toFile);
		}
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}

	public static boolean deleteQuietly(String srcPath) {
		return (new File(srcPath)).delete();
	}
}