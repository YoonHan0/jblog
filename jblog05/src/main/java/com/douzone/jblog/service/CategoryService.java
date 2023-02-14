package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	public void join(UserVo vo) {
		CategoryVo categoryvo = new CategoryVo();
		
		categoryvo.setId(vo.getId());
		categoryvo.setName("기본");
		
		categoryRepository.insert(categoryvo);
	}
	
	public void addCategoryList(CategoryVo vo) {			// 카테고리명 입력 시 추가되는 함수
		categoryRepository.insert(vo);
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

	public void delete(Long no) {
		postRepository.delete(no);			// 삭제하려는 카테고리에 있는 글 먼저 모두 삭제하고
		categoryRepository.delete(no);		// 그 다음에 카테고리 삭제
	}


}
