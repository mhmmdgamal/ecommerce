package com.ecommerce.helper;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieHelper {

    public static void addCookies(String user_name, String pass_word, HttpServletRequest request, HttpServletResponse response)
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

    public static boolean readCookies(HttpServletRequest request)
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

    public static void deleteCookie(String username, HttpServletRequest request, HttpServletResponse response)
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
}
