package com.togarpic.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Autowired
	private LoggerInterceptor myInterceptor;
	
	 @Autowired
	    private AuthorizationInterceptor authorizationInterceptor;
		//-->Sau khi đã tạo và đăng ký interceptor,nó áp dụng với mọi yêu cầu xử lý của controller 
		@Override 
		public void addInterceptors(InterceptorRegistry registry) {
			
			registry.addInterceptor(myInterceptor);
			
			registry.addInterceptor(new  LoggerInterceptor())
			.addPathPatterns("/login");
			
			 registry.addInterceptor(authorizationInterceptor)
	         .addPathPatterns("/admin/dashboard")
	         .addPathPatterns("/login/alltable")
	         .addPathPatterns("/home/");

		}
}
