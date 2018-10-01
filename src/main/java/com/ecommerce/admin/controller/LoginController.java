//<editor-fold >
package com.ecommerce.admin.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.CookieHelper;
import com.ecommerce.helper.HashHelper;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

//</editor-fold >
@WebServlet("/admin/login")
public class LoginController extends HttpServlet {

    String adminJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        adminJspPath = servletContext.getInitParameter("adminJspPath");

    }

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            // redirect to dashboard if session exists
            response.sendRedirect("dashboard");
        } else {
            // forword the requset to the login page
            Helper.forwardRequest(request, response, adminJspPath + "login.jsp", "Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get Session 
        HttpSession session = request.getSession();
        // get form params from the request
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String passwordHashed = HashHelper.stringHash(password);
        String remember = request.getParameter("remember");

        // get logging user
        User user = new UserDaoImpl(servletContext).getLoginUser(username, passwordHashed, true);

        // check if user exists
        if (user != null) {
            if (remember != null && remember.equalsIgnoreCase("y")) {
                // set user data to cookies 
                CookieHelper.addCookie("admin", username, response);
                CookieHelper.addCookie("adminId", "" + user.getId(), response);
                CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);

            } else {
                // set session for User 
                SetUserSession(user, session);
            }
            Helper.setTitle(request, "Dashboard");
            response.sendRedirect("dashboard");

        } else {
            // redirect to login page if user not exits
            response.sendRedirect("login");
        }
    }// </editor-fold>

    public void SetUserSession(User user, HttpSession session) {

        //set session for user if remember me not checked 
        session.setAttribute("admin", user.getName());
        session.setAttribute("adminId", user.getId());
        session.setAttribute("fullName", (user.getFullName().split(" ")[0]));
    }

}
