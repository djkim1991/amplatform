package io.anymobi.repositories.mybatis;

import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 *
 */
public interface CommonSqlRepository {
	
    /**
     * 특정 쿼리문 실행 : Delete
     * @param qName : 쿼리문 네임스페이스
     * @param parameter
     * @return
     * @throws SQLException
     */
	int delete(String qName, Object parameter);
	int delete(String qName);
	
    /**
     * 특정 쿼리문 실행 : Insert
     * @param qName : 쿼리문 네임스페이스
     * @param parameter
     * @return
     * @throws SQLException
     */
	int insert(String qName, Object parameter);
	int insert(String qName);
	
	/**
	 * 틀정 쿼리문 실행 : Update
	 * @param qName
	 * @param parameter
	 * @return
	 * @throws SQLException
	 */
	int update(String qName, Object parameter);
	int update(String qName);
	
    /**
     * 특정 쿼리문 실행 : SELECT - ONE
     * @param qName
     * @param parameter
     * @return Object
     * @throws SQLException
     */
	Object selectOne(String qName, Object parameter);
	Object selectOne(String qName);
    
	
	/**
	 * 특정쿼리문 실행 : SELECT - LIST
	 * @param qName
	 * @param parameter
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	List selectList(String qName, Object parameter);
	
	@SuppressWarnings("rawtypes")
	List selectList(String qName);
	
	public SqlSession getSqlSession();
}
