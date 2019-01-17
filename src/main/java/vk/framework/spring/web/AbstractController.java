package vk.framework.spring.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import vk.framework.spring.message.BaseMessageSource;
import vk.framework.spring.vo.PaginationVo;
import vk.framework.spring.web.tags.ui.PaginationInfo;

public class AbstractController {
	protected static final String CMD_ATTRIBUTE_NAME = "cmd";
	protected Log log;
	@Resource(name = "baseMessageSource")
	protected BaseMessageSource baseMessageSource;
	@Autowired
	@Resource(name = "envProp")
	protected Properties envProp;
	@Autowired
	@Resource(name = "fileProp")
	protected Properties fileProp;
	@Autowired
	protected HttpServletRequest requestContext;

	public AbstractController() {
		this.log = LogFactory.getLog(this.getClass());
	}

	public PaginationInfo getPaginationInfo(Object objPagination) {
		int pageUnit = Integer.parseInt(this.envProp.getProperty("pageUnit").toString());
		int pageSize = Integer.parseInt(this.envProp.getProperty("pageSize").toString());
		if (this.requestContext.getParameter("pageUnit") != null) {
			pageUnit = Integer.parseInt(this.requestContext.getParameter("pageUnit").toString());
		}
		return this.getPaginationInfo(objPagination, pageUnit, pageSize);
	}

	public PaginationInfo getPaginationInfo(Object objPagination, int pageUnit, int pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		if (objPagination instanceof PaginationVo) {
			PaginationVo paginationVo = (PaginationVo) objPagination;
			paginationVo.setPageUnit(pageUnit);
			paginationVo.setPageSize(pageSize);
			paginationInfo.setCurrentPageNo(paginationVo.getPageIndex().intValue());
			paginationInfo.setRecordCountPerPage(paginationVo.getPageUnit().intValue());
			paginationInfo.setPageSize(paginationVo.getPageSize().intValue());
			paginationVo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			paginationVo.setLastIndex(paginationInfo.getLastRecordIndex());
			paginationVo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			paginationVo.setPagination(true);
		} else {
			try {
				Method method = null;
				method = objPagination.getClass().getMethod("setPageUnit", Integer.TYPE);
				method.invoke(objPagination, pageUnit);
				method = objPagination.getClass().getMethod("setPageSize", Integer.TYPE);
				method.invoke(objPagination, pageSize);
				method = objPagination.getClass().getMethod("getPageIndex", new Class[0]);
				paginationInfo.setCurrentPageNo(((Integer) method.invoke(objPagination, new Object[0])).intValue());
				paginationInfo.setRecordCountPerPage(pageUnit);
				paginationInfo.setPageSize(pageSize);
				method = objPagination.getClass().getMethod("setFirstIndex", Integer.TYPE);
				method.invoke(objPagination, paginationInfo.getFirstRecordIndex());
				method = objPagination.getClass().getMethod("setLastIndex", Integer.TYPE);
				method.invoke(objPagination, paginationInfo.getLastRecordIndex());
				method = objPagination.getClass().getMethod("setRecordCountPerPage", Integer.TYPE);
				method.invoke(objPagination, paginationInfo.getRecordCountPerPage());
				method = objPagination.getClass().getMethod("setPagination", Boolean.TYPE);
				method.invoke(objPagination, true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return paginationInfo;
	}

	protected void setCmd(ModelMap model, Cmd cmd) {
		this.setCmd(model, cmd.toString());
	}

	protected void setCmd(ModelMap model, String cmd) {
		model.addAttribute("cmd", (Object) cmd);
	}

	public static enum Cmd {
		Insert, Update, Save, Delete;

		private Cmd() {
		}
	}

}