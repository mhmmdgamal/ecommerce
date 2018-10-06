// <editor-fold>
package com.ecommerce.customer.user;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ShowProfileController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        servletContext = getServletContext();
    }// </editor-fold>

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Profile");

        Long userId = null;

        if (CookieHelper.isCookie("userId", request, response)) {
            userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
        } else {
            // get userId from session
            userId = (Long) request.getSession().getAttribute("userId");
        }

        // get user with id 
        User user = new UserDaoImpl(servletContext).getUserById(userId);

        // set user to request
        request.setAttribute("user", user);

        // get user items with user id 
        List<Item> userItems = new UserDaoImpl(servletContext).getUserItems(userId, "ASC");

        // set user items to request
        request.setAttribute("userItems", userItems);

        // get user Comments with user id 
        List<Comment> userComments = new UserDaoImpl(servletContext).getUserComments(userId, "ASC");

        // set user Comments to request
        request.setAttribute("userComments", userComments);

        // forward to profile page
        Helper.forwardRequest(request, response, ViewPath.show_profile);
    }
}
