package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileuploadService fileuploadService;

	@RequestMapping(value = {"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String main(Model model, 
			@PathVariable("id") String id, 
			@PathVariable("categoryNo") Optional<Long> categoryNo, 
			@PathVariable("postNo") Optional<Long> postNo) {
		
		System.out.println(id + " : " + categoryNo + " : "  + postNo);
		
//		if(!postNo.get().getClass().equals(Long.class) || !categoryNo.get().getClass().equals(Long.class)) {
//			return "redirect:/";
//		}
		
		Long categoryno = 0L;
		Long postno = 0L;
		PostVo post = new PostVo();
		
		if(categoryNo.isEmpty()) {
			System.out.println("카테고리 없음");
			categoryno = categoryService.getRecentlyCategoryList(id);		// 가장 먼저 작성된 카테고리의 no값
		} else {
			System.out.println("카테고리 있음");
			categoryno = categoryNo.get();
		}
		
		if(postNo.isEmpty()) {
			System.out.println("포스트 넘버 없음");
			post = postService.getRecentlyPost(categoryno);				// 기본값을 위한 PostVo
		} else {
			System.out.println("포스트 넘버 있음");
			postno = postNo.get();
			post = postService.getPostByNo(postno, categoryno);	// 해당 카테고리의 선택된 포스트를 출력
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
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(Model model, @PathVariable("id") String id) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		model.addAttribute("blogvo", blogvo);
		
		return "blog/admin-basic";
	}
	@Auth
	@RequestMapping(value = "/admin/category", method=RequestMethod.GET)
	public String adminCategory(Model model, @PathVariable("id") String id) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		// 카테고리 no, name, id, (PostVo)
		List<CategoryVo> categoryList = categoryService.getCategoryList(id);		//카테고리 리스트 받아오기
		model.addAttribute("blogvo", blogvo);		// id, title, profile
		model.addAttribute("categoryList", categoryList);	
		
		return "blog/admin-category";
	}
	
	@Auth
	@RequestMapping(value = "/admin/category", method=RequestMethod.POST)
	public String adminCategory(Model model, @PathVariable("id") String id, CategoryVo vo) {	// vo = name
		
		vo.setId(id);
		categoryService.addCategoryList(vo);
		
		BlogVo blogvo = blogService.getBlogInfoById(id);
		// 카테고리 no, name, id, (PostVo)
		List<CategoryVo> categoryList = categoryService.getCategoryList(id);
		
		model.addAttribute("blogvo", blogvo);		// id, title, profile
		model.addAttribute("categoryList", categoryList);
		
		return "blog/admin-category";
	}
	@Auth
	@RequestMapping("/admin/delete")		// 카테고리 리스트 삭제 redirect!
	public String adminDelete(Model model, @PathVariable("id") String id, Long no) {
		
		categoryService.delete(no);		
		return "redirect:/" + id + "/admin/category";
	}
	@Auth
	@RequestMapping(value = "/admin/write", method=RequestMethod.GET)
	public String adminWrite(Model model, @PathVariable("id") String id) {
		
		BlogVo blogvo = blogService.getBlogInfoById(id);		//blog id, title, profile
		List<CategoryVo> categoryList = categoryService.getCategoryList(id); // category no, id, name, postingNo
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogvo", blogvo);
		
		return "blog/admin-write";
	}
	@Auth
	@RequestMapping(value = "/admin/write", method=RequestMethod.POST)		
	// PostVo로 값(title, contents)
	public String adminWrite(Model model, 
			@PathVariable("id") String id, PostVo postVo, 
			@RequestParam("category") Long categoryNo) {
		
		postVo.setCategoryNo(categoryNo);		// 글 작성하면서 선택한 카테고리의 no값 set
		postService.insert(postVo);
		PostVo vo = postService.getRecentlyPost(categoryNo);	// 가장 최근 post
		
		return "redirect:/" + id + "/" + categoryNo + "/" + vo.getNo();		// optional하면 될거임
	}


	@Auth
	@RequestMapping("/update")
	public String adminUpdate(
			BlogVo blogVo,					// title, id
			@RequestParam("file") MultipartFile file,
			Model model) {
		
		String url = fileuploadService.restore(file);		
		blogVo.setProfile(url);
		
		blogService.updateBlogInfo(blogVo);
		BlogVo vo = blogService.getBlogInfoById(blogVo.getId());
		
		model.addAttribute("blogvo", vo);
		return "blog/admin-basic";
	}
}
	
	