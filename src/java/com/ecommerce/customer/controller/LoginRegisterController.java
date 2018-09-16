/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.customer.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mohamed
 */
public class LoginRegisterController extends HttpServlet {

    String customerJspPath = null;
    private ServletContext servletContext;
    
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        customerJspPath = getServletContext().getInitParameter("customerJspPath");
        servletContext = getServletContext();
    }

    
    
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

        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            // redirect to home if session exists
            response.sendRedirect("");
        } else {
            // forword the requset to the login page
            Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp", "Login");
        }

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
        String login = (String) request.getParameter("login");
        if (login != null) {
            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");

            // get logging user
            User user = new UserDaoImpl(servletContext).getLoginUser(username, password, false);

            // check if user exists
            if (user != null) {

                // get session and set user data to it
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                session.setAttribute("userId", user.getId());
                session.setAttribute("fullName", (user.getFullName().split(" ")[0]));

                Helper.setTitle(request, "Home");
                response.sendRedirect("");
            } else {
                // redirect to login page if user not exits
                response.sendRedirect("login");
            }
        } else {
            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");
            String confirmPassword = request.getParameter("pass2");
            String email = request.getParameter("email");

            // make empty list to errors
            List<String> formErrors = new ArrayList();

            // validate the form params
            if (username != null) {
                if (username.length() < 4) {
                    formErrors.add("Username Cant Be Less Than <strong>4 Characters</strong>");
                }
            } else {
                formErrors.add("Username Cant Be <strong>Empty</strong>");
            }

            if (password == null) {
                formErrors.add("Password Cant Be <strong>Empty</strong>");
            }

            if (!confirmPassword.equals(password)) {
                formErrors.add("Sorry Password Is Not Match");
            }

            if (email == null) {
                formErrors.add("Email Cant Be <strong>Empty</strong>");
            }

            // set errors to the request
            request.setAttribute("errors", formErrors);

            // check if no errors
            if (formErrors.size() > 0) {
                // forword to add page
                Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");
            } else {
                // make new user and set info to it
                User user = new User();
                user.setName(username);
                user.setPassword(password);
                user.setEmail(email);

                // add user
                boolean userAdded = new UserDaoImpl(servletContext).addUser(user);
                if (!userAdded) {
                    // add new error to errors if user not added
                    formErrors.add("Sorry This User Is Exist");
                } else {
                    // set success message if user added
                    request.setAttribute("success", "Congrats You Are Now Registerd User");
                }
                // forword to add page
                Helper.forwardRequest(request, response, customerJspPath + "home.jsp");

            }

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
