package vk.framework.spring.dataaccess;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

import vk.framework.spring.dataaccess.Mapper;

public class MapperConfigurer extends MapperScannerConfigurer {
	public MapperConfigurer() {
		super.setAnnotationClass(Mapper.class);
		super.setSqlSessionFactoryBeanName("sqlSession");
	}
}