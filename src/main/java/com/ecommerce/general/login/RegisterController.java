//<editor-fold >
package com.ecommerce.general.login;

import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.HashHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//</editor-fold >

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

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
            Helper.forwardRequest(request, response, ViewPath.login_register + previous, "Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        JSONArray errors = new JSONArray();
        String success = null;
        String redirect = null;

        // get FORM params from the request
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String confirmPassword = request.getParameter("pass2");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full_name");
        String remember = request.getParameter("remember");
        //get param previous page from url
        String previous = request.getParameter("previous");

        // validate the FORM params
        errors = validateParams(username, password, confirmPassword, email);

        if (errors.size() > 0) {//if there's errors
            
            if (previous != null) {//if user come to login from other page 
                //set parameter <previous> in url again 
                redirect = ViewPath.login_register + "?previous=" + previous;
            } else {
                redirect = ViewPath.login_register;
            }

        } else {//if there's NO errors
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
            boolean userCreated = new UserDaoImpl(getServletContext()).addUser(user);

            if (userCreated) {//if user created successfully  
                // set success message 
                success = "Congrats You Are Now Registerd User";
                //if user Click Remember Me 
                if (remember != null && remember.equalsIgnoreCase("y")) {
                    //<set Cookies>
                    setUserCookies(user, response);

                } else {
                    //<set Session>
                    SetUserSession(user, request.getSession());
                }

                //System.out.println(previous);
                if (previous != null && !previous.equals("")) {//previousPage == null <improve>
                    redirect = previous;
                } else {
                    redirect = "home";
                }

            } else {//if user not created 
                // add new error to errors 
                errors.add("Sorry This User Is Exist");

                //forword to login again 
//                Helper.setTitle(request, "Login");
                if (previous != null) {
//                    Helper.forwardRequest(request, response, ViewPath.login_register + "?previous=" + previous);
                    redirect = ViewPath.login_register + "?previous=" + previous;
                } else {
                    
//                    Helper.forwardRequest(request, response, ViewPath.login_register);
                    redirect = ViewPath.login_register;
                }
            }
        }

        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("redirect", redirect);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());

    }

    public JSONArray validateParams(String username, String password, String confirmPassword, String email) {

        // make empty list to errors
        JSONArray formErrors = new JSONArray();

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

    public void setUserCookies(User user, HttpServletResponse response)
            throws ServletException, IOException {

        // set user data to cookies 
        CookieHelper.addCookie("user", user.getName(), response);
        CookieHelper.addCookie("userId", "" + user.getId(), response);
        CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);
    }

}
