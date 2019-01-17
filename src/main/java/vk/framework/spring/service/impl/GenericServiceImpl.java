package vk.framework.spring.service.impl;

import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import vk.framework.spring.mapper.GenericMapper;
import vk.framework.spring.service.GenericService;

public class GenericServiceImpl<M extends GenericMapper<R, P, S>, R, P, S> implements GenericService<R, P, S> {
	protected Log log = LogFactory.getLog(this.getClass());
	protected M mapper;
	@Autowired
	@Resource(name = "envProp")
	protected Properties envProp;
	@Autowired
	@Resource(name = "fileProp")
	protected Properties fileProp;

	@Autowired
	protected void setMapper(M mapper) {
		this.mapper = mapper;
	}

	public List<R> selectAllList() throws Exception {
		return this.mapper.selectAllList();
	}

	public List<R> selectAllList(S searchVo) throws Exception {
		return this.mapper.selectAllList(searchVo);
	}

	public List<R> selectList(S searchVo) throws Exception {
		return this.mapper.selectList(searchVo);
	}

	public R select(P paramVo) throws Exception {
		return this.mapper.select(paramVo);
	}

	public int selectTotalCount(S searchVo) throws Exception {
		return this.mapper.selectTotalCount(searchVo);
	}

	public void insert(P paramVo) throws Exception {
		this.mapper.insert(paramVo);
	}

	public void update(P paramVo) throws Exception {
		this.mapper.update(paramVo);
	}

	public void delete(P paramVo) throws Exception {
		this.mapper.delete(paramVo);
	}
}