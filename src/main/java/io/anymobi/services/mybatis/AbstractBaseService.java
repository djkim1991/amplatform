package io.anymobi.services.mybatis;

import io.anymobi.repositories.mybatis.CommonSqlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 *
 */
@Slf4j
public abstract class AbstractBaseService<Mapper> {

	@Autowired
	protected CommonSqlRepository commonSqlRepository;
	
	protected Mapper getMapper(Class<Mapper> clz) {
		Mapper mapper = (Mapper) commonSqlRepository.getSqlSession().getMapper(clz);
		return mapper;
	}
}
