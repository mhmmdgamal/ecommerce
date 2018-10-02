// <editor-fold >
package com.ecommerce.login;

import com.ecommerce.helper.CookieHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// </editor-fold >

        // get session if exists .. false mean if it not exist don't create it
        HttpSession session = request.getSession(false);

        if (session != null) {
            // invaldate the session to logout
            session.invalidate();
        }
        //destroy cookies 
        CookieHelper.deleteCookies(request, response);

        // redirect to home page to new login
        response.sendRedirect("");
    }
}
