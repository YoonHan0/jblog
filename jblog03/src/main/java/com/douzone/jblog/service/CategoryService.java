package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public void join(UserVo vo) {
		CategoryVo categoryvo = new CategoryVo();
		
		categoryvo.setId(vo.getId());
		categoryvo.setName("기본");
		
		categoryRepository.insert(categoryvo);
	}

	public List<CategoryVo> getCategoryList(String id) {
		return categoryRepository.getCategoryList(id);
	}

	public Long getRecentlyCategoryList(String id) {
		return categoryRepository.getRecentlyCategoryList(id);
	}

	public List<CategoryVo> categoryListWithPostNo(String id) {
		
		return categoryRepository.getListWithPostNo(id);
	}

}
