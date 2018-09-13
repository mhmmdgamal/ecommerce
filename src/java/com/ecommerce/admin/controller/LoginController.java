/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.admin.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends HttpServlet {
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // forword the requset to the login page
        Helper.forwardRequest(request, response, "/WEB-INF/views/admin/login.jsp", "Login");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get form params from the request
        String userName = request.getParameter("user");
        String password = request.getParameter("pass");

        // get logging user
        User user = new UserDaoImpl(getServletContext()).getLoginUser(userName, password);

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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
