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
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if ((session.getAttribute("user") != null) || (CookieHelper.isCookie("user", request, response))) {
            if ((session.getAttribute("groupId") != null) || (CookieHelper.isCookie("groupId", request, response))) {
                // go to dashboard
                response.sendRedirect("admin/dashboard");
            } else {
                // go to home 
                response.sendRedirect("");
            }
        } else {
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
        String remember = request.getParameter("remember");
        // hash password
        String passwordHashed = HashHelper.stringHash(password);
        //get param previous page from url
        String previous = request.getParameter("previous");

        // get USER logging from DB
        User user = new UserDaoImpl(getServletContext()).getLoginUser(username, passwordHashed);

        if (user != null) {//if user existed in DB 

            //if user Click Remember Me 
            if (remember != null && remember.equalsIgnoreCase("y")) {
                setUserCookies(user, response);//set Cookies

            } else {//if user ignore remember Me 
                SetUserSession(user, request.getSession()); //set Session 
            }
            //get Redirect page based on: <previous> or <group id> 
            redirect = getRedirect(previous, user.getGroupId());
            success = "login success";

        } else {//if user Not existed in DB
            errors.add("user not Existed! try again ");
            redirect = ViewPath.login_register;
        }
        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("redirect", redirect);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }

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

    public String getRedirect(String previous, int groupId) {

        String redirect;
        //check previous parameter existed in URL
        if (previous != null && !previous.equals("")) {
            redirect = previous;

        } else {//if No previous page
            redirect = (groupId == 1) ? "admin/dashboard" : "home";
        }
        return redirect;
    }
}
