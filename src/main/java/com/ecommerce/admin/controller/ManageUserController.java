//<editor-fold>
package com.ecommerce.admin.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

//</editor-fold>
@WebServlet("/admin/manage-users")
public class ManageUserController extends HttpServlet {

    String adminJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        adminJspPath = servletContext.getInitParameter("adminJspPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Users");

        List<User> users;

        // check if the page param value if exists and equal Pending
        if (request.getParameter("page") != null && request.getParameter("page").equals("Pending")) {
            // get pendings users
            users = new UserDaoImpl(servletContext).getAllUsers(true);
        } else {
            // get users
            users = new UserDaoImpl(servletContext).getAllUsers(false);
        }
        // set users to the request
        request.setAttribute("users", users);

        // forword to manage page
        Helper.forwardRequest(request, response, adminJspPath + "manage_users.jsp");
    }

}
