package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setPassword(password);
		
		UserVo authUser = userService.getUser(vo);		// id, password
		
		if(authUser == null) {		// 올바르지 않는 아이디 비번
			request.setAttribute("id", vo.getId());	
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			
			return false;		// 아이디 request에 담아서 보내버림
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath());		// 로그인 성공 시 메인 페이지로
		
		return false;
	}

}
