package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHanlder가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Hanlder Method의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. Hanlder Method에 @Auth가 없으면 Type(class)에 붙어 있는지 확인 -> TEST : 로그인을 하지 않고 admin페이지에 들어갈 수 없게 됨
		if(auth == null) {
//			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		
		// 5. Type이나 Method에 @Auth가 없는 경우 -> 권한 제어가 아무것도 없는 경우
		if(auth == null) {
			return true;
		}
		
		//6. @Auth가 붙어 있기 때문에 인증(Authenfication) 여부 확인
		// 로그인된 아이디
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {	// 로그인을 안 하고 admin에 접근할 때
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;			// 로그인 안 해도 접근 가능하게 해야댐ㅇㅇ
		}
		// 로그인은 되어 있다 ->
			
		// 여기서 url에 있는 id값을 가지고 오자
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String uri = requestURI.substring(contextPath.length() + 1);
		
		System.out.println(requestURI+ " : " + contextPath+ " : " + uri + " : " + authUser.getId());
		
		// url로 작성한 id
		String id = uri.split("/")[0];

		// 로그인 id랑 url로 친 id가 다를 때
		if(!id.equals(authUser.getId())) {	
			response.sendRedirect(request.getContextPath());
			return false;
		}		
		// 동일한 계정인 경우 ->
		return true;
	}

}