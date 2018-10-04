//<editor-fold >
package com.ecommerce.general.login;

import com.ecommerce.general.enumiration.ViewParent;
import com.ecommerce.general.enumiration.ViewType;
import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.HashHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.helper.PathsHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        servletContext = getServletContext();

    }//</editor-fold >

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get Session
        HttpSession session = request.getSession();
        //if existed session or cookies  
        if ((session.getAttribute("user") != null) || (CookieHelper.isCookie("user", request, response))) {
            if ((session.getAttribute("groupId") != null) || (CookieHelper.isCookie("groupId", request, response))) {
                // go to dashboard
                response.sendRedirect("admin/dashboard");
            } else {
                // go to home 
                response.sendRedirect("");
            }

        } else {//if Not existed session or cookies 
            //set param previous in url again 
            String previous = request.getParameter("previous");
            if (previous != null) {
                previous = "?previous=" + previous;
            } else {
                previous = "";
            }
            // forword the requset to the login page 
            Helper.forwardRequest(request, response, PathsHelper.getPublicLogin("login_register") + previous, "Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get FORM params from the request
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String confirmPassword = request.getParameter("pass2");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full_name");
        String remember = request.getParameter("remember");
        //get param previous page from url
        String previous = request.getParameter("previous");

        //create list for errors
        List<String> formErrors
                // validate the FORM params
                = validateParams(username, password, confirmPassword, email);
        // set errors to the request
        request.setAttribute("errors", formErrors);

        ///////////////Start if there's errors /////////////////////////////////
        if (formErrors.size() > 0) {
            //if user come to login from other page 
            if (previous != null) {
                //set parameter <previous> in url again 
                Helper.forwardRequest(request, response, PathsHelper.getPublicLogin("login_register") + "?previous=" + previous);
            } else {
                Helper.forwardRequest(request, response, PathsHelper.getPublicLogin("login_register"));
            }
            ///////////////End if there's errors ///////////////////////////////
            ///////////////Start if there's NO errors //////////////////////////
        } else {
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

            //if user created successfully  
            if (userCreated) {
                // set success message if user added
                request.setAttribute("success", "Congrats You Are Now Registerd User");

                //<set Cookies>if user doing Remember Me 
                if (remember != null && remember.equalsIgnoreCase("y")) {
                    // set user data to cookies 
                    CookieHelper.addCookie("user", username, response);
                    CookieHelper.addCookie("userId", "" + user.getId(), response);
                    CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);

                } else {
                    //get session
                    HttpSession session = request.getSession();
                    //<set Session>if user ignore remember Me 
                    SetUserSession(user, session);
                }

                //System.out.println(previous);
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
                    Helper.forwardRequest(request, response, PathsHelper.getPublicLogin("login_register") + "?previous=" + previous);
                } else {
                    Helper.forwardRequest(request, response, PathsHelper.getPublicLogin("login_register"));
                }
            }
        }
        ///////////////End if there's NO errors /////////////////////////////////
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

    public void SetUserSession(User user, HttpSession session) {

        //set session for user if remember me not checked 
        session.setAttribute("user", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("fullName", (user.getFullName().split(" ")[0]));
    }

}
