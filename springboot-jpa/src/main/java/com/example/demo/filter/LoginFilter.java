package com.example.demo.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/room/*", "/rooms/*"}) // 需要登入才能訪問的路徑
public class LoginFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 檢查 session 中是否有 userCert
		HttpSession session = request.getSession();
		if(session.getAttribute("userCert") == null) { // 沒有憑證
			response.sendRedirect("/login"); // 重導到登入頁面
			return;
		}
		// 放行
		chain.doFilter(request, response);
	}
	
}
