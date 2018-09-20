//

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
import javax.xml.ws.Response;

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

        //get param login to know if user do login or do register 
        String login = request.getParameter("login");

        if (login != null) {//if user login 

            // check parameter username and password if are existed in database 
            //and if true setSession 
            SetSessionForExistedUser(request, response);

        } else {//if user registered 

            // get form params from the request
            String username = request.getParameter("user");
            String password = request.getParameter("pass");
            String confirmPassword = request.getParameter("pass2");
            String email = request.getParameter("email");

            // validate the form params
            List<String> formErrors = validateParams(username, password, confirmPassword, email);

            if (formErrors.size() > 0) {// if there is errors
                // set errors to the request
                request.setAttribute("errors", formErrors);
                // forword to add page
                Helper.forwardRequest(request, response, customerJspPath + "login_register.jsp");

            } else {//if there is no errors
                //creat new user in Database
                boolean userCreated = creatNewUser(username, password, email);

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

    public void SetSessionForExistedUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get form params from the request
        String username = request.getParameter("user");
        String password = request.getParameter("pass");

        // get logging user
        User user = new UserDaoImpl(servletContext).getLoginUser(username, password, false);

        // check if user exists
        if (user != null) {
            //set session for user
            // if remember me not checked 
            if (true) {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                session.setAttribute("userId", user.getId());
                session.setAttribute("fullName", (user.getFullName().split(" ")[0]));

            } //hide this now <editor-fold >
            else {
                //make 3 objects and store 3 up set Attribute 
                Cookie user1 = new Cookie("user", username);
                Cookie userId = new Cookie("userId", user.getId() + "");
                Cookie fullName = new Cookie("user", (user.getFullName().split(" ")[0]));

                //response.addCookie
                response.addCookie(user1);
                response.addCookie(userId);
                response.addCookie(fullName);
            }//</editor-fold >

            Helper.setTitle(request, "Home");
            response.sendRedirect("/home");

        } else {//user not exist in DB 
            // redirect to login page if user not exits
            response.sendRedirect("login");
        }
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

    public boolean creatNewUser(String username, String password, String email) {

        // make new user and set info to it
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);

        // add user
        boolean userAdded = new UserDaoImpl(servletContext).addUser(user);
        if (!userAdded) {
            return false;

        } else {
            return true;
        }
    }

    public void addCookies(String user_name, String pass_word, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create cookies for first and last names.      
        Cookie username = new Cookie("username", user_name);
        Cookie password = new Cookie("password", pass_word);

        // Set expiry date after 24 Hrs for both the cookies.
        username.setMaxAge(60 * 60 * 24);
        password.setMaxAge(60 * 60 * 24);

        // Add both the cookies in the response header.
        response.addCookie(username);
        response.addCookie(password);
    }

    public boolean readCookies(HttpServletRequest request)
            throws ServletException, IOException {

        // Get an array of Cookies associated with this domain
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {//if cookie exists 

            HttpSession session = request.getSession();
            session.setAttribute("username", cookies[0]);
            session.setAttribute("password", cookies[1]);
            return true;

        } else {
            //there is no cookies 
            return false;
        }
    }

    public void deleteCookie(String username, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie cookie = null;
        Cookie[] cookies = null;

        // Get an array of Cookies associated with this domain
        cookies = request.getCookies();

        // Set response content type
        response.setContentType("text/html");

        if (cookies != null) {// if cookie founded 
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                //deleted cookie of user by setMaxAge = 0
                if ((cookie.getName()).compareTo(username) == 0) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        } else {
            // cookie not found
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
