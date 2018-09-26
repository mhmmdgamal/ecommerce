//<editor-fold >
package com.ecommerce.admin.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
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

        if (session.getAttribute("username") != null) {
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
        // get form params from the request
        String userName = request.getParameter("user");
        String password = request.getParameter("pass");
        String passwordHashed = HashHelper.stringHash(password);

        // get logging user
        User user = new UserDaoImpl(servletContext).getLoginUser(userName, passwordHashed, true);

        // check if user exists
        if (user != null) {

            // get session and set user data to it
            HttpSession session = request.getSession();
            session.setAttribute("username", userName);
            session.setAttribute("id", user.getId());

            // set the first name of user to the session
            session.setAttribute("fullName", (user.getFullName().split(" ")[0]));

            Helper.setTitle(request, "Dashboard");
            response.sendRedirect("dashboard");
        } else {

            // redirect to login page if user not exits
            response.sendRedirect("login");
        }
    }// </editor-fold>

}
