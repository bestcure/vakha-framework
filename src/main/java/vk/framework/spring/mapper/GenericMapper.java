package vk.framework.spring.mapper;

import java.util.List;

public interface GenericMapper<R, P, S> {
	List<R> selectAllList() throws Exception;

	List<R> selectAllList(S arg0) throws Exception;

	List<R> selectList(S arg0) throws Exception;

	R select(P arg0) throws Exception;

	int selectTotalCount(S arg0) throws Exception;

	void insert(P arg0) throws Exception;

	void update(P arg0) throws Exception;

	void delete(P arg0) throws Exception;
}