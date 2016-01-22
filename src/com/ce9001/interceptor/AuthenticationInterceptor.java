package com.ce9001.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ce9001.viewBean.UserBean;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
		public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		 

 

		if(
				    !request.getRequestURI().equals("/OnlineQuizSpring/login")&&
				    !request.getRequestURI().equals("/OnlineQuizSpring/home.html")&&
				    !request.getRequestURI().equals("/OnlineQuizSpring/home"))

{
	
				  UserBean userData = (UserBean) request.getSession().getAttribute("user");
		   if(userData == null)
			   
		   {
			   
			   		    response.sendRedirect("/OnlineQuizSpring/login");
		    return false;
		    
		   }   
		   
}

		  return true;
	}

	@Override

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,

	ModelAndView modelAndView) throws Exception {


	}

	@Override

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)

	throws Exception {


	}


}
