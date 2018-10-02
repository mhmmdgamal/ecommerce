//<editor-fold >
package com.ecommerce.filter;

import com.ecommerce.helper.CookieHelper;
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

//<editor-fold >
@WebFilter(urlPatterns = {
    //filter User 
    "/admin/manage-users",
    "/admin/add-user",
    "/admin/edit-user",
    "/admin/active-user",
    "/admin/delete-user",
    //filter categories 
    "/admin/manage-categories",
    "/admin/add-category",
    "/admin/edit-category",
    "/admin/delete-category",
    //filter Items 
    "/admin/manage-items",
    "/admin/edit-item",
    "/admin/approve-item",
    "/admin/delete-item",
    //filter Comments
    "/admin/manage-comments",
    "/admin/edit-comment",
    "/admin/delete-comment",
    "/admin/approve-comment",
    //filter Dashboard
    "/admin/dashboard"})
//</editor-fold >

public class AdminLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // check if the user session is exists or cookies
        if ((session.getAttribute("groupId") == null) && (!CookieHelper.isCookie("groupId", req, res))) {
            // redirect to login page
            res.sendRedirect("/ecommerce/login");

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
