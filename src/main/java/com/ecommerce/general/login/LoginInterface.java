//package com.ecommerce.general.login;
//
//import com.ecommerce.bean.User;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.json.simple.JSONArray;
//
//public interface LoginInterface {
//
//    public User getUser(String username, String passwordHashed);
//
//    public void SetUserSession(User user, HttpSession session);
//
//    public void setUserCookies(User user, HttpServletResponse response);
//
//    public String getRedirect(String previous, int groupId);
//
//    public String printJSON(boolean success, JSONArray errors, String redirect);
//}
