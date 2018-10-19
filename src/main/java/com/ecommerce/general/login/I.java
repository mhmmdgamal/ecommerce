package com.ecommerce.general.login;

import com.ecommerce.general.user.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

public interface I {

    public void SetUserSession(User user, HttpSession session);

    public void setUserCookies(User user, HttpServletResponse response)
            throws ServletException, IOException;

    public void saveUserData(User user, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    public String getSuccessRedirect(HttpServletRequest request, int groupId);

    public String getErrorRedirect(HttpServletRequest request);

    public void responseJSON(String redirect, String success, JSONArray errors,
            HttpServletResponse response) throws IOException, IOException;

    interface Login extends I {

        public User getUserLogining(HttpServletRequest request);

    }

    interface Register extends I {

        public JSONArray validateParams(String username, String password,
                String confirmPassword, String email);

        public User createUser(JSONArray errors, HttpServletRequest request);
    }
}
