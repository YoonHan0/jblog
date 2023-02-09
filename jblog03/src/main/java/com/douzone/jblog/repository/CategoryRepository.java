package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	
	@Autowired
	private SqlSession sqlSession;
	
	
	public void insert(CategoryVo categoryvo) {
		sqlSession.insert("category.insert", categoryvo);
	}


	public List<CategoryVo> getCategoryList(String id) {
		return sqlSession.selectList("category.categoryListById", id);
	}


	public Long getRecentlyCategoryList(String id) {
		return sqlSession.selectOne("category.getRecentlyCategoryList", id);
	}


	public List<CategoryVo> getListWithPostNo(String id) {			// no, name, id, posting Count
		return sqlSession.selectList("category.getListWithPostNo", id);
	}

}
