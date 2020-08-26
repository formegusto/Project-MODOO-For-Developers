package com.wrk.mfd.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	/*
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	*/
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		response.sendRedirect("/authCheck");
	}

}
