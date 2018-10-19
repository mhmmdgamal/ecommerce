//<editor-fold >
package com.ecommerce.general.login;

import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.HashHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
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

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet implements I.Login {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        //if user is logining 
        if ((session.getAttribute("user") != null) || (CookieHelper.isCookie("user", request, response))) {
            // get group id of admin
            boolean userIsAdmin = (session.getAttribute("groupId") != null)
                    || (CookieHelper.isCookie("groupId", request, response));

            redirect = userIsAdmin ? dashboard : home;
            response.sendRedirect(redirect);

        } else {//if Not existed session or cookies

            redirect = getErrorRedirect(request);
            // forword the requset to the login page 
            Helper.forwardRequest(request, response, redirect, "Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONArray errors = new JSONArray();
        String success = null;
        redirect = null;

        //get User logining from DB ..
        User user = getUserLogining(request);

        if (user != null) {//if user existed in DB 

            //save user data in Session or Cookies
            saveUserData(user, request, response);

            //get Redirect page
            redirect = getSuccessRedirect(request, user.getGroupId());
            success = "login success";

        } else {//if user Not existed in DB
            errors = new JSONArray();
            errors.add("user not Existed! try again ");
            redirect = ViewPath.login_register;
        }
        responseJSON(redirect, success, errors, response);
    }

    @Override
    public User getUserLogining(HttpServletRequest request) {
        // get FORM params from the request
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        // hash password
        String passwordHashed = HashHelper.stringHash(password);

        // get USER logging from DB
        User user = new UserDaoImpl(getServletContext())
                .getLoginUser(username, passwordHashed);

        return user;
    }

    @Override
    public void SetUserSession(User user, HttpSession session) {

        //set session for user if remember me not checked 
        session.setAttribute("user", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("fullName", (user.getFullName().split(" ")[0]));

        // set group id to session if admin
        if (user.getGroupId() == 1) {
            session.setAttribute("groupId", 1);
        }
    }

    @Override
    public void setUserCookies(User user, HttpServletResponse response)
            throws ServletException, IOException {

        //<set Cookies>if user doing Remember Me 
        CookieHelper.addCookie("user", user.getName(), response);
        CookieHelper.addCookie("userId", "" + user.getId(), response);
        CookieHelper.addCookie("fullName", (user.getFullName().split(" ")[0]), response);

        // set group id to cookie if admin
        if (user.getGroupId() == 1) {
            CookieHelper.addCookie("groupId", 1 + "", response);
        }
    }

    @Override
    public void saveUserData(User user, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String remember = request.getParameter("remember");
        //if user Click Remember Me 
        if (remember != null && remember.equalsIgnoreCase("y")) {
            setUserCookies(user, response);//set Cookies

        } else {//if user ignore remember Me 
            SetUserSession(user, request.getSession()); //set Session 
        }
    }

    @Override
    public String getSuccessRedirect(HttpServletRequest request, int groupId) {

        //get param previous page from url
        String previous = request.getParameter("previous");

        //check previous parameter existed in URL
        if (previous != null && !previous.equals("")) {
            redirect = previous;

        } else {//if No previous page
            redirect = (groupId == 1) ? dashboard : home;
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
