package com.ecommerce.helper;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Helper {

    /**
     * check if the string is number or not
     *
     * @param value
     * @return boolean
     */
    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * explode string to array
     *
     * @param string
     * @param split
     * @return tokens
     */
    public static String[] explode(String string, String split) {
        String[] tokens = string.split(split);
        return tokens;
    }

    /**
     * redirect to the previous page
     *
     * @param request
     * @param response
     * @param message
     * @param error
     * @throws ServletException
     * @throws IOException
     */
    public static void redriectToPrevPage(HttpServletRequest request, HttpServletResponse response, String message, boolean error) throws ServletException, IOException {
        if (!error) {
            request.getSession().setAttribute("success", message);
        } else {
            request.getSession().setAttribute("error", message);
        }

        String link = request.getHeader("referer");
        if (link == null) {
            setTitle(request, "Home");
            response.sendRedirect("");
        } else {
            setTitle(request, getTitleFromRefererLink(link));
            response.sendRedirect(link);
        }
    }

    //get page name from url (link)
    public static String getPageName(String link) {

        String pageName = link.split("/")[4].split("\\?")[0];
        return pageName;
    }

    /**
     * forward request to specific page and set title of page
     *
     * @param request
     * @param response
     * @param page
     * @param title
     * @throws ServletException
     * @throws IOException
     */
    public static void forwardRequest(HttpServletRequest request, HttpServletResponse response, String page, String title) throws ServletException, IOException {
        setTitle(request, title);
        forwardRequest(request, response, page);
    }

    /**
     * forward request to specific page
     *
     * @param request
     * @param response
     * @param page
     * @throws ServletException
     * @throws IOException
     */
    public static void forwardRequest(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        request.getRequestDispatcher(page).forward(request, response);
    }

    /**
     * set title of page
     *
     * @param request
     * @param title
     */
    public static void setTitle(HttpServletRequest request, String title) {
        request.getServletContext().setAttribute("title", title);
    }

    /**
     * get title page from referer link
     *
     * @param link
     * @return title
     */
    public static String getTitleFromRefererLink(String link) {
        /**
         * link like: http://localhost:8084/ecommerce/items?action=Add get
         * items?action=Add
         */
        // String query = link.split("/")[4];

        /**
         * items?action=Add get items
         */
        // String pageName = query.split("\\?")[0];
        /**
         * get i
         */
        // String firstCharToUC = String.valueOf(pageName.charAt(0)).toUpperCase();
        /**
         * merge all to be Items
         */
        // String title = firstCharToUC + pageName.substring(1);
//        String title = String.valueOf("http://localhost:8084/ecommerce/items?action=Add".split("/")[4].split("\\?")[0].charAt(0)).toUpperCase() + "http://localhost:8084/ecommerce/items?action=Add".split("/")[4].split("\\?")[0].substring(1);
        String title = String.valueOf(
                link.split("/")[4].split("\\?")[0].charAt(0)).toUpperCase()
                + link.split("/")[4].split("\\?")[0].substring(1);

        return title;
    }

    /**
     * remove last characters from string
     *
     * @param str
     * @param chars
     * @return
     */
    public static String rTrim(String str, String chars) {
        Pattern pattern = Pattern.compile(chars + "$");
        return pattern.matcher(str).replaceAll("");
    }

    /**
     * check if the object is blank
     *
     * @param obj
     * @return boolean
     */
    public static boolean isBlank(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            return (str.isEmpty());
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return (map.isEmpty());
        } else {
            List list = (List) obj;
            return (list == null || list.isEmpty());
        }
    }

    /**
     * check if the object is not blank
     *
     * @param obj
     * @return boolean
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * replace any white space in element of list with blank string
     *
     * @param str
     * @return Array List
     */
    public static ArrayList replaceSpacesWithBlanck(ArrayList str) {
        for (int i = 0; i < str.size(); i++) {
            String newELement = ((String) str.get(i)).replaceAll("[ ]+", "");
            str.set(i, newELement);
        }
        return str;
    }

    /**
     * get current date
     *
     * @return current date
     */
    public static Date getCurrentDate() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return date;
    }
}
