package com.bank.filter;

import com.bank.model.Account;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Required for older Servlet API
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        Account account = (session != null) ? (Account) session.getAttribute("account") : null;
        String path = httpRequest.getServletPath();

        if (account == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            return;
        }

        // Role-based access control
        if (path.startsWith("/admin") && !"ADMIN".equals(account.getRole())) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Admin role required.");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Required for older Servlet API
    }
}
