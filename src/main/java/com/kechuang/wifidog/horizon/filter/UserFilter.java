package com.kechuang.wifidog.horizon.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kechuang.wifidog.horizon.model.User;

public class UserFilter extends HttpServlet implements Filter {

	/**
   * 
   */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UserFilter() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		HttpServletResponse rep = (HttpServletResponse) response;

		User user = (User) session.getAttribute("user");

		if (user != null) {
			chain.doFilter(request, response);
		} else {
			/*rep.sendRedirect(req.getContextPath() + "/login.html");*/
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("window.top.location.href='" + req.getContextPath() + "/login.html'");
			out.println("</script");
			out.flush();
			return;
			// request.getRequestDispatcher("/index.jsp").forward(request,
			// response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
