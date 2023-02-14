package com.douzone.jblog.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:com/douzone/jblog/web/fileupload.properties")
public class FileuploadConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;
	/* Multipart Resolver */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));
		
		return multipartResolver;
	}

	
	/* url-resource mapping : images로 들어오는 경로를 실제 경로로 연결(가상 URL)  */
	/* /images/202302071405.png -> Users/yoon/fileupload-files/202302071405.png 이렇게 매핑되게 설정하고 코딩해야함 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("fileupload.resourceUrl") + "/**")
			.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocations") + "/");
	}
}
