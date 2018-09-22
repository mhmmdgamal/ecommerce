package com.ecommerce.helper;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

    public static void addCookie(String name, String value, HttpServletResponse response)
            throws ServletException, IOException {

        // Create cookies for first and last names.      
        Cookie cookie = new Cookie(name, value);

        // Set expiry date after 24 Hrs for both the cookies.
        cookie.setMaxAge(60 * 60 * 24);

        // Add both the cookies in the response header.
        response.addCookie(cookie);
    }

    public static void deleteCookies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get an array of Cookies associated with this domain
        Cookie[] cookies = request.getCookies();

        // Set response content type
        response.setContentType("text/html");

        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public static boolean isCookie(String name, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get an array of Cookies associated with this domain
        Cookie[] cookies = request.getCookies();

        // Set response content type
        response.setContentType("text/html");

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public static String getCookie(String name, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get an array of Cookies associated with this domain
        Cookie[] cookies = request.getCookies();

        // Set response content type
        response.setContentType("text/html");

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
