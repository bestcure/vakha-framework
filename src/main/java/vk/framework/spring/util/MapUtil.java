package vk.framework.spring.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import vk.framework.spring.dataaccess.util.DataMap;

public class MapUtil {
	protected static final Log log = LogFactory.getLog(MapUtil.class);

	public static int maxKeyLength(Map<?, ?> map) {
		String key = null;
		Set keySet = map.keySet();
		Iterator keyIter = keySet.iterator();
		int preLength = 0;
		boolean curLength = false;

		while (keyIter.hasNext()) {
			key = keyIter.next().toString();
			int curLength1 = key.length();
			if (preLength < curLength1) {
				preLength = curLength1;
			}
		}

		return preLength;
	}

	public static void mergeMap(Map targetMap, Map addMap) {
		if (targetMap != null && addMap != null) {
			Iterator iter = null;
			Entry entry = null;

			try {
				iter = addMap.entrySet().iterator();

				while (iter.hasNext()) {
					entry = (Entry) iter.next();
					targetMap.put(entry.getKey(), entry.getValue());
				}
			} catch (Exception arg4) {
				arg4.printStackTrace();
			}

		}
	}

	public static DataMap nullToEmptyString(DataMap dataMap) throws Exception {
		Iterator arg0 = dataMap.entrySet().iterator();

		while (arg0.hasNext()) {
			Entry entry = (Entry) arg0.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				dataMap.put(key, "");
			}
		}

		return dataMap;
	}
}