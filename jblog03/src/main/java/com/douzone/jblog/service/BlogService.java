package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	public void join(UserVo vo) {
		BlogVo blogVo = new BlogVo();
		
		blogVo.setId(vo.getId());
		blogVo.setTitle(vo.getName() + "님의 블로그");
		blogVo.setProfile("/assets/images/spring-logo.jpg");	// 기본 이미지?
				
		blogRepository.insert(blogVo);
	}

	public BlogVo getBlogInfoById(String id) {
		return blogRepository.getBlogInfoById(id);
	}

	public void updateBlogInfo(BlogVo blogVo) {
		blogRepository.update(blogVo);
		
	}

	

}
