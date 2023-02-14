package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;


public class BlogMainInterceptor implements HandlerInterceptor {

	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		BlogVo blogVo = (BlogVo)request.getServletContext().getAttribute("blogvo");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(blogVo == null) {
			BlogVo blogvo = blogService.getBlogInfoById(authUser.getId());
			request.getServletContext().setAttribute("blogvo", blogVo);
		}
		return true;		
	}



}
