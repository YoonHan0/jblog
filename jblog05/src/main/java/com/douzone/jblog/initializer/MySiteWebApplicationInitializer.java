package com.douzone.jblog.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.douzone.jblog.config.AppConfig;
import com.douzone.jblog.config.WebConfig;

public class MySiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* <!-- Context Load Listener --> */
	@Override
	protected Class<?>[] getRootConfigClasses() {

		// Root Application Context's Configuration Class
		return new Class<?>[] {AppConfig.class};
	}

	/* <!-- Dispatcher Servlet --> */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	/* <!-- Encoding Filter --> */
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("utf-8", false)};
	}

//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		super.onStartup(servletContext);
//		
//		DispatcherServlet dispatcherServlet = new DispatcherServlet(new GenericWebApplicationContext());
//		ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("spring", dispatcherServlet);	// "spring"이라는 이름은 상관없음. 기존에 있던 spring-servlet 역할
//		servletRegistration.setLoadOnStartup(1);
//		
//		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
//	}
	
	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		
		DispatcherServlet servlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
		servlet.setThrowExceptionIfNoHandlerFound(true);
		
		return servlet;
	}	
	
	

}
