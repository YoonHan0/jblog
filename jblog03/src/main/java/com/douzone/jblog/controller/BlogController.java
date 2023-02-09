package com.douzone.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id}")
public class BlogController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private BlogService blogService;

	@RequestMapping("")	// 진짜 오네;;
	public String main(Model model, @PathVariable("id") String id, 
			@RequestParam("categoryNo") String categoryNo, 
			@RequestParam("postNo") String postNo) {
		
		System.out.println(id + " : " + categoryNo + " : "  + postNo);
		
		Long categoryno;
		PostVo post = new PostVo();
		
		if(categoryNo.equals("")) {
			System.out.println("카테고리 없음");
			categoryno = categoryService.getRecentlyCategoryList(id);		// 가장 먼저 작성된 카테고리의 no값
		} else {
			System.out.println("카테고리 있음");
			categoryno = Long.parseLong(categoryNo);
		}
		
		if(postNo.equals("")) {
			System.out.println("포스트 넘버 없음");
			post = postService.getRecentlyPost(categoryno);				// 기본값을 위한 PostVo
		} else {
			System.out.println("포스트 넘버 있음");
			post = postService.getPostByNo(Long.parseLong(postNo), categoryno);	// 해당 카테고리의 선택된 포스트를 출력
		}
		
		
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		
		List<CategoryVo> categoryList = categoryService.getCategoryList(id);		//카테고리 리스트 받아오기
		List<PostVo> postList = postService.getPostList(categoryno);						// 포스팅된 글 리스트 받아오기
		
		model.addAttribute("blogvo", blogvo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postvo", post);
		model.addAttribute("postList", postList);
		
		return "blog/main";
	}
}
