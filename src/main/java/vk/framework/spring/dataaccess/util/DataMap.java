package vk.framework.spring.dataaccess.util;

import org.apache.commons.collections4.map.ListOrderedMap;

import vk.framework.spring.collections.CamelUtil;

public class DataMap extends ListOrderedMap<Object, Object> {
	private static final long serialVersionUID = 6723434363565852261L;

	public Object put(Object key, Object value) {
		return super.put(CamelUtil.convert2CamelCase((String) key), value);
	}
}