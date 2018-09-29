package com.ecommerce.admin.controller;

import com.ecommerce.helper.CookieHelper;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/admin/logout")
public class LogoutController extends HttpServlet {

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get session if exists
        HttpSession session = request.getSession(false);

        if (session != null) {
            // invaldate the session to logout
            session.invalidate();
        }
        //destroy cookies 
        CookieHelper.deleteCookies(request, response);

//        String customerJspPath = getServletContext().getInitParameter("customerJspPath");

        // redirect to login page to new login
        response.sendRedirect("login");
//        Helper.forwardRequest(request, response, customerJspPath + "home.jsp", "Login");

    }// </editor-fold>

}
