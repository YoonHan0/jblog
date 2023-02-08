package com.douzone.jblog.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

public class BlogMainInterceptor implements HandlerInterceptor{

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 블로그 메인은 어차피 로그인해야 올 수 있음 정상적으로는..
		// if(no == null)이면 no의 값은 로그인한 id의 카테고리 중 가장 작은 no / 아니면 넘겨주는 값
				
		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo)session.getAttribute("authUser");	// Session값 받아오기
		
		List<CategoryVo> categoryList = categoryService.getCategoryList(vo.getId());		//카테고리 리스트 받아오기
		Long no = categoryService.getRecentlyCategoryList(vo.getId());		// 가장 먼저 작성된 카테고리의 no값
		
		
		List<PostVo> postList = postService.getPostList(no);
		PostVo postVo = postService.getRecentlyPost(no);
		
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("postList", postList);
		request.setAttribute("postVo", postVo);
		request.getRequestDispatcher("/WEB-INF/views/blog/main.jsp").forward(request, response);
		
		return false;
	}

}
