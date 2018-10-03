//<editor-fold >
package com.ecommerce.general.login;

import com.ecommerce.general.helper.CookieHelper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//</editor-fold >

@WebFilter(urlPatterns = {"/new-item", "/edit-item", "/profile", "/edit-profile"})
public class UserLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // check if existed session or cookies for user login
        if ((session.getAttribute("user") == null) && (!CookieHelper.isCookie("user", req, res))) {
            String prevUrl = req.getRequestURI();
            
            if (req.getQueryString()!= null) {
                prevUrl = "?" + req.getQueryString();
            }

            // redirect to login page
            res.sendRedirect("login?previous=" + prevUrl);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
