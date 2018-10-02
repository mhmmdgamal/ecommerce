//<editor-fold >
package com.ecommerce.filter;

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
//</editor-fold >

@WebFilter(urlPatterns = {"/*"})
public class UrlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // get requestUri from request
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        // check if requestUri have path and end with / slash
        if (requestURI.length() > 0 && requestURI.endsWith("/") && !requestURI.equals("/ecommerce/")) {
            // get requestUri without the end / slash
            requestURI = requestURI.substring(0, requestURI.length() - 1);

            // redirect to the new requestUri
            ((HttpServletResponse) response).sendRedirect(requestURI);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
