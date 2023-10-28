package com.togarpic.Interceptor;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
	 private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggerInterceptor.class);

		
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 String requestURI = request.getRequestURI();
	        return true;
	    }
	
	    private boolean isAdmin(HttpServletRequest request) {
	    	 if(request !=null) {
	    		 String roles = (String) request.getSession().getAttribute("roles");
	    		 if (roles != null && roles.equals("ADMIN")) {
		             return true;
		         }
	    		 
	    	 } 
	        return false;
	    }
}
