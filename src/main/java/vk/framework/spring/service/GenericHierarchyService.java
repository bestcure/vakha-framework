package vk.framework.spring.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.vo.GenericHierarchyVo;

public class GenericHierarchyService<O extends GenericHierarchyVo<O>> {
	private static final String PATH_SEPARATOR = "/";
	private List<O> list;
	private O rootObject;
	private List<O> hierarchyList;

	public GenericHierarchyService(List<O> list, O rootObject) {
		this.list = list;
		this.rootObject = rootObject;
	}

	public void makeHierarchy(boolean isSort) {
		for (GenericHierarchyVo object : this.list) {
			String[] strPaths = StringUtils.split((String) object.getPath(), (String) "/");
			Integer[] paths = new Integer[strPaths.length];
			object.setType("page");
			for (int i = 0; i < strPaths.length; ++i) {
				paths[i] = Integer.parseInt(strPaths[i]);
			}
			this.recursive(this.rootObject, paths, 0);
		}
		if (isSort) {
			this.sortHierarchy(this.rootObject);
		}
	}

	public void sortHierarchy(O childObject) {
		if (childObject.getChildren() != null) {
			OrderComparator comparator = new OrderComparator();
			Collections.sort(childObject.getChildren(), comparator);
			for (GenericHierarchyVo tempChild : childObject.getChildren()) {
				this.sortHierarchy((O) tempChild);
			}
		} else {
			return;
		}
	}

	private O findObjectById(List<O> list, Integer id) {
		GenericHierarchyVo returnObject = null;
		for (GenericHierarchyVo object : list) {
			if (!id.equals(object.getId()))
				continue;
			returnObject = object;
			break;
		}
		return (O) returnObject;
	}

	private void recursive(O childObject, Integer[] paths, int index) {
		if (index < paths.length) {
			O currentObject = this.findObjectById(this.list, paths[index]);
			if (currentObject != null) {
				O tmpMenu;
				if (childObject.getChildren() == null) {
					childObject.setType("folder");
					childObject.setChildren(new ArrayList());
				}
				if ((tmpMenu = this.findObjectById(childObject.getChildren(), currentObject.getId())) == null) {
					childObject.getChildren().add(currentObject);
				}
				currentObject.setLevel(Integer.valueOf(++index));
				this.recursive(currentObject, paths, index);
			}
		} else {
			return;
		}
	}

	public List<O> getList(boolean isSort) {
		this.hierarchyList = new ArrayList<O>();
		this.makeHierarchy(isSort);
		this.convertObjectToList(this.rootObject);
		return this.hierarchyList;
	}

	private void convertObjectToList(O childObject) {
		if (childObject.getId() != null) {
			this.hierarchyList.add(childObject);
		}
		if (childObject.getChildren() != null) {
			OrderComparator comparator = new OrderComparator();
			Collections.sort(childObject.getChildren(), comparator);
			for (GenericHierarchyVo tempChild : childObject.getChildren()) {
				this.convertObjectToList((O) tempChild);
			}
		} else {
			return;
		}
	}

	public class OrderComparator<O extends GenericHierarchyVo<O>> implements Comparator<O> {
		@Override
		public int compare(O obj1, O obj2) {
			if (obj1.getOrder() == null) {
				obj1.setOrder(Integer.valueOf(1));
			}
			if (obj2.getOrder() == null) {
				obj2.setOrder(Integer.valueOf(1));
			}
			return obj1.getOrder() - obj2.getOrder();
		}
	}

}
