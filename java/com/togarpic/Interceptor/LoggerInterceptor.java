package com.togarpic.Interceptor;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggerInterceptor implements HandlerInterceptor{
	 private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggerInterceptor.class);

	 
	 
	 @Override
	  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 	logger.info("Pre-handle 2023 : "+request.getRequestURI());
		 
	 	
		 	return true;

	  }
	 @Override
	 public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	     // Xử lý sau khi yêu cầu được xử lý bởi controller, trước khi trả về kết quả
		 logger.info("Post-handle 2023: " + request.getRequestURI());
		 
		 

		 
	 }
	 
	 @Override
	 public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	     // Xử lý sau khi kết quả đã được trả về cho client
		  logger.info("After-completion 2023: " + request.getRequestURI());

	 }
}
