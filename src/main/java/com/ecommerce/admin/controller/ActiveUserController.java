package com.ecommerce.admin.controller;

import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ActiveUserController", urlPatterns = {"/admin/active-user"})
public class ActiveUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get userId param from the request
        String userId = request.getParameter("userid");

        // return the userId if number or return 0
        long id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

        // activate user depending on userId
        boolean userActivated = new UserDaoImpl(getServletContext()).activeUser(id);
        if (userActivated) {
            // redirect to the previous page with deleted message
            Helper.redriectToPrevPage(request, response, "user approved", false);
        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

}
