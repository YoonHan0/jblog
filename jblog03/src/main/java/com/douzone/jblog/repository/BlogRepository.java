package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class BlogRepository {

	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(BlogVo blogvo) {
		sqlSession.insert("blog.insert", blogvo);
		
	}

	public BlogVo getBlogInfoById(String id) {
		return sqlSession.selectOne("blog.getBlogInfo", id);
	}

	

}
