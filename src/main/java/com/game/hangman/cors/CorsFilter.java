package com.game.hangman.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	private final String ALLOW_ORIGIN = "http://localhost:4200";
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Acces-Control-Allow-Origin", ALLOW_ORIGIN);
		response.setHeader("Acces-Control-Allow-Credetials", "true");
		
		if ("OPTIONS".equals(request.getMethod()) && ALLOW_ORIGIN.equals(request.getHeader("Origin"))) {
			response.setHeader("Acces-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
			response.setHeader("Acces-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			response.setHeader("Acces-Control-Allow-Max-Age", "3600");
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}	
	}
}

