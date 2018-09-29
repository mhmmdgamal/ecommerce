// <editor-fold >
package com.ecommerce.customer.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.CookieHelper;
import com.ecommerce.helper.HashHelper;
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

        if ((session.getAttribute("user") != null) || (CookieHelper.isCookie("user", request, response))) {
            // go to home 
            response.sendRedirect("");

        } else {
            String previous = request.getParameter("previous");
            
            if (previous != null) {
                previous = "?previous=" + previous;
            } else {
                previous = "";
            }
            
            // forword the requset to the login page
            Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp" + previous, "Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> formErrors = null;
        HttpSession session = request.getSession();
        //get param login to know if user do login or do register 
        String login = request.getParameter("login");

        String previous = request.getParameter("previous");

        if (login != null) {//if user login 
            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");
            String remember = request.getParameter("remember");

            // hash password
            String passwordHashed = HashHelper.stringHash(password);

            // get logging user
            User user = new UserDaoImpl(servletContext).getLoginUser(username, passwordHashed, false);

            if (user != null) {//if user existed in DB 
                if (remember != null && remember.equalsIgnoreCase("y")) {
                    // set user data to cookies 
                    CookieHelper.addCookie("user", username, response);
                    CookieHelper.addCookie("userId", "" + user.getId(), response);
                    CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);

                } else {
                    // set session for User 
                    SetUserSession(user, session);

                }

                if (previous != null) {//previousPage == null <improve>
                    response.sendRedirect(previous);
                } else {
                    Helper.setTitle(request, "Home");
                    response.sendRedirect("home");
                }

            } else {//user not exist in DB 
                formErrors = new ArrayList();
                formErrors.add("user not Existed! try again ");

                request.setAttribute("errors", formErrors);
                // redirect to login page 
                Helper.setTitle(request, "Login");
                if (previous != null) {
                    Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp?previous=" + previous);
                } else {
                    Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");
                }
            }
            //////////////////////////////////////////////////////////////////

        } else {//if user registered 

            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");
            String confirmPassword = request.getParameter("pass2");
            String email = request.getParameter("email");
            String fullName = request.getParameter("full_name");
            String remember = request.getParameter("remember");

            // validate the form params
            formErrors = validateParams(username, password, confirmPassword, email);

            // set errors to the request
            request.setAttribute("errors", formErrors);

            if (formErrors.size() > 0) {// if there is errors

                // forword to login page
                Helper.setTitle(request, "Login");

                if (previous != null) {
                    Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp?previous=" + previous);
                } else {
                    Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");
                }

            } else {//if there is no errors

                // hash password
                String passwordHashed = HashHelper.stringHash(password);

                // make new user and set info to it
                User user = User.builder()
                        .name(username)
                        .password(passwordHashed)
                        .email(email)
                        .fullName(fullName)
                        .build();

                //creat new user in Database
                boolean userCreated = new UserDaoImpl(servletContext).addUser(user);

                //check if user created 
                if (userCreated) {//if user created successfully 
                    // set success message if user added
                    request.setAttribute("success", "Congrats You Are Now Registerd User");
                    // if user doing remember Me 
                    if (remember != null && remember.equalsIgnoreCase("y")) {
                        // set user data to cookies 
                        CookieHelper.addCookie("user", username, response);
                        CookieHelper.addCookie("userId", "" + user.getId(), response);
                        CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);

                    } else {//if user ignore remember Me 
                        SetUserSession(user, session);
                    }
                    
                    System.out.println(previous);
                    if (previous != null) {//previousPage == null <improve>
                        response.sendRedirect(previous);
                    } else {
                        response.sendRedirect("home");
                    }

                } else {//if user not created 
                    // add new error to errors 
                    formErrors.add("Sorry This User Is Exist");

                    //forword to login again 
                    Helper.setTitle(request, "Login");
                    if (previous != null) {
                        Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp?previous=" + previous);
                    } else {
                        Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");
                    }
                }

            }
        }
    }

    public void SetUserSession(User user, HttpSession session) {

        //set session for user if remember me not checked 
        session.setAttribute("user", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("fullName", (user.getFullName().split(" ")[0]));
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
