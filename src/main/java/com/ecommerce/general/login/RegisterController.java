//<editor-fold >
package com.ecommerce.general.login;

import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.HashHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
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
public class RegisterController extends HttpServlet implements I.Register {

    String dashboard;
    String home;
    String redirect = null;

    @Override
    public void init() throws ServletException {
        super.init();
        dashboard = ViewPath.getUrlPatternAdmin(ViewPath.dashboard_admin);
        home = ViewPath.getUrlPattern(ViewPath.home);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //attributes
        JSONArray errors = new JSONArray();
        String success = null;
        //create user
        User user = createUser(errors, request);

        boolean userAdded = (user == null) ? false
                //add user to DB
                : new UserDaoImpl(getServletContext()).addUser(user);

        if (userAdded) {//if user created successfully  

            success = "Congrats You Are Now Registerd User";
            //save user data Session or Cookies 
            saveUserData(user, request, response);
            //get Success Redirect 
            redirect = getSuccessRedirect(request);

        } else {//if user not created 
            errors.add("Sorry This User Is Exist");
            //get Redirect 
            redirect = getErrorRedirect(request);
        }
        //send response by JSON 
        responseJSON(redirect, success, errors, response);
    }

    @Override
    public JSONArray validateParams(String username, String password,
            String confirmPassword, String email) {

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

    @Override
    public User createUser(JSONArray errors, HttpServletRequest request) {

        // get FORM params from the request
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String confirmPassword = request.getParameter("pass2");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full_name");

        // validate the FORM params
        errors = validateParams(username, password, confirmPassword, email);

        if (errors.size() > 0) {
            return null;

        } else {//if no errors
            // hash password
            String passwordHashed = HashHelper.stringHash(password);
            // make new user and set info to it
            User user = User.builder()
                    .name(username)
                    .password(passwordHashed)
                    .email(email)
                    .fullName(fullName)
                    .build();
            return user;
        }
    }

    @Override
    public void SetUserSession(User user, HttpSession session) {

        //set session for user if remember me not checked 
        session.setAttribute("user", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("fullName", (user.getFullName().split(" ")[0]));
    }

    @Override
    public void setUserCookies(User user, HttpServletResponse response)
            throws ServletException, IOException {

        // set user data to cookies 
        CookieHelper.addCookie("user", user.getName(), response);
        CookieHelper.addCookie("userId", "" + user.getId(), response);
        CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);
    }

    @Override
    public void saveUserData(User user, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String remember = request.getParameter("remember");
        //if user Click Remember Me 
        if (remember != null && remember.equalsIgnoreCase("y")) {
            //<set Cookies>
            setUserCookies(user, response);

        } else {
            //<set Session>
            SetUserSession(user, request.getSession());
        }
    }

    public String getSuccessRedirect(HttpServletRequest request) {
        return getSuccessRedirect(request, -1);//-1 mean group id not used
    }

    @Override
    public String getSuccessRedirect(HttpServletRequest request, int groupId) {

        //get param previous page from url
        String previous = request.getParameter("previous");

        //if user come to login from other page 
        if (previous != null && !previous.equals("")) {
            redirect = previous;
        } else {
            redirect = home;
        }
        return redirect;

    }

    @Override
    public String getErrorRedirect(HttpServletRequest request) {

        //get param previous page from url
        String previous = request.getParameter("previous");

        //if user come to login from other page 
        if (previous != null && !previous.equals("")) {
            //set parameter <previous> in url again 
            redirect = ViewPath.login_register + "?previous=" + previous;
        } else {
            redirect = ViewPath.login_register;
        }
        return redirect;
    }

    @Override
    public void responseJSON(String redirect, String success, JSONArray errors,
            HttpServletResponse response) throws IOException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("redirect", redirect);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }

}
