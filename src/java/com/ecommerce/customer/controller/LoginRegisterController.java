// <editor-fold >
package com.ecommerce.customer.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;

@WebServlet("/login") // == @WebServlet( urlPatterns = {"/login"} ) 
public class LoginRegisterController extends HttpServlet {

    String customerJspPath = null;
    private ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        servletContext = getServletContext();
        customerJspPath = servletContext.getInitParameter("customerJspPath");
    }
// </editor-fold >

    // <editor-fold >
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> formErrors = null;
        HttpSession session = request.getSession();
        //get param login to know if user do login or do register 
        String login = request.getParameter("login");

        if (login != null) {//if user login 
            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");

            // get logging user
            User user = new UserDaoImpl(servletContext).getLoginUser(username, password, false);

            if (user != null) {// check if user existed in DB 
                // set session for User 
                SetSession(user, session);
                Helper.setTitle(request, "Home");
                response.sendRedirect("home");

            } else {//user not exist in DB 
                // redirect to login page 
                Helper.setTitle(request, "Login");
                response.sendRedirect("login");
                formErrors.add("user not Existed! try again ");
            }
            //////////////////////////////////////////////////////////////////

        } else {//if user registered 

            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");
            String confirmPassword = request.getParameter("pass2");
            String email = request.getParameter("email");

            // validate the form params
            formErrors = validateParams(username, password, confirmPassword, email);

            if (formErrors.size() > 0) {// if there is errors
                // set errors to the request
                request.setAttribute("errors", formErrors);
                // forword to add page
                Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");

            } else {//if there is no errors
                //creat new user in Database
                boolean userCreated = creatNewUser(username, password, email, session);

                //check if user created 
                if (!userCreated) {//if user not created 
                    // add new error to errors 
                    formErrors.add("Sorry This User Is Exist");
                    //forword to login again 
                    Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");

                } else {//if user created successfully 
                    // set success message if user added
                    request.setAttribute("success", "Congrats You Are Now Registerd User");
                    // forword to home page
                    Helper.forwardRequest(request, response, customerJspPath + "home.jsp");
                }
            }
        }
    }

    public void SetSession(User user, HttpSession session) {

        //set session for user if remember me not checked 
        session.setAttribute("user", user.getName());
        session.setAttribute("userId", user.getId());
//        session.setAttribute("fullName", (user.getFullName().split(" ")[0]));
        session.setAttribute("fullName", user.getName());
    }

    public List<String> validateParams(String username, String password, String confirmPassword, String email) {

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
        return formErrors;
    }

    public boolean creatNewUser(String username, String password, String email, HttpSession session) {

        // make new user and set info to it
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);
        SetSession(user, session);
        // add user 
        return new UserDaoImpl(servletContext).addUser(user);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
