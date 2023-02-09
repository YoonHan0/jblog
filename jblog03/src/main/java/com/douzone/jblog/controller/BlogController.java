package com.douzone.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileuploadService;
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
	
	@Autowired
	private FileuploadService fileuploadService;

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
	
	@RequestMapping("/admin/basic")
	public String adminBasic(Model model, @PathVariable("id") String id) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		model.addAttribute("blogvo", blogvo);
		
		return "blog/admin-basic";
	}
	
	@RequestMapping("/update")
	public String adminUpdate(
			BlogVo blogVo,					// title, id
			@RequestParam("file") MultipartFile file,
			Model model) {
		
		System.out.println("여긴 들ㄹ어오나??????");
		String url = fileuploadService.restore(file);		
		blogVo.setProfile(url);
		
		blogService.updateBlogInfo(blogVo);
		BlogVo vo = blogService.getBlogInfoById(blogVo.getId());
		
		model.addAttribute("blogvo", vo);
		return "blog/admin-basic";
	}
	
	@RequestMapping(value = "/admin/category", method=RequestMethod.GET)
	public String adminCategory(Model model, @PathVariable("id") String id) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		model.addAttribute("blogvo", blogvo);		// id, title, profile
		
		// 카테고리 no, name, id, (PostVo)
		List<CategoryVo> categoryList = categoryService.getCategoryList(id);		//카테고리 리스트 받아오기
		
		model.addAttribute("categoryList", categoryList);		
		return "blog/admin-category";
	}
	
	/*===== 여기 =======*/
	@RequestMapping(value = "/admin/category", method=RequestMethod.POST)
	public String adminCategory(Model model, @PathVariable("id") String id, CategoryVo vo) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		model.addAttribute("blogvo", blogvo);		// id, title, profile
		
		// 카테고리 no, name, id, (PostVo)
		List<CategoryVo> categoryList = categoryService.getCategoryList(id);		//카테고리 리스트 받아오기
		
		model.addAttribute("categoryList", categoryList);		
		return "blog/admin-category";
	}
	
	
	@RequestMapping("/admin/write")
	public String adminWrite(Model model, @PathVariable("id") String id) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		model.addAttribute("blogvo", blogvo);
		
		return "blog/admin-write";
	}
}
