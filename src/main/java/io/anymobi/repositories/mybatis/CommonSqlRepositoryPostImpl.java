package io.anymobi.repositories.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 *
 */
@Repository("commonSqlRepositoryPost")
public class CommonSqlRepositoryPostImpl implements CommonSqlRepository {

	private SqlSession sqlSession;

    private boolean externalSqlSession;

    @Autowired(required = false)
    public final void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory2) {
        if (!this.externalSqlSession) {
            this.sqlSession = new SqlSessionTemplate(sqlSessionFactory2);
        }
    }

    @Autowired(required = false)
    public final void setSqlSessionTemplate(SqlSessionTemplate sqlSession2) {
        this.sqlSession = sqlSession2;
        this.externalSqlSession = true;
    }

	public int delete(String qName, Object parameter)  {
		return this.sqlSession.delete(qName, parameter);
	}

	public int insert(String qName, Object parameter)  {
		return this.sqlSession.insert(qName, parameter);
	}

	public int update(String qName, Object parameter)  {
		return this.sqlSession.update(qName, parameter);
	}

	public Object selectOne(String qName, Object parameter)  {
		return this.sqlSession.selectOne(qName, parameter);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String qName, Object parameter)
			 {
		return this.sqlSession.selectList(qName, parameter);
	}

	public int delete(String qName)  {
		return delete(qName, null);
	}

	public int insert(String qName)  {
		return insert(qName, null);
	}

	public int update(String qName)  {
		return update(qName, null);
	}

	public Object selectOne(String qName)  {
		return selectOne(qName, null);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String qName)  {
		return selectList(qName, null);
	}

	@Override
	public SqlSession getSqlSession() {
		return null;
	}

}
