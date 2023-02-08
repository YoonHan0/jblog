package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {			// 회원가입 화면으로 이동만
		return "/user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {			
		userService.join(vo);
		blogService.join(vo);
		categoryService.join(vo);
		return "redirect:/user/joinsuccess";			// 성공화면으로 이동
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping("/login")		
	public String login() {
		return "user/login";
	}
	
	@RequestMapping("/logout")		
	public String logout() {		
		return "redirect:/main/index";
	}
	
	
}
