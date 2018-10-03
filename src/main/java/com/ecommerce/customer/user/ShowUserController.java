package com.ecommerce.customer.user;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ShowUserControllerForCustomer", urlPatterns = {"/users"})
public class ShowUserController extends HttpServlet {

    ServletContext servletContext = null;
    String customerJspPath = null;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        servletContext = getServletContext();
        customerJspPath = servletContext.getInitParameter("customerJspPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //get param user id 
        String userid = request.getParameter("userid");
        long id = userid != null && Helper.isNumber(userid) ? Long.parseLong(userid) : 0;

        // get user with id 
        User user = new UserDaoImpl(servletContext).getUserById(id);

        // set page title
        Helper.setTitle(request, user.getName());

        // set user to request
        request.setAttribute("user", user);

        // get user items with user id 
        List<Item> userItems = new UserDaoImpl(servletContext).getUserItems(id, "ASC");

        // set user items to request
        request.setAttribute("userItems", userItems);

        // get user Comments with user id 
        List<Comment> userComments = new UserDaoImpl(servletContext).getUserComments(id, "ASC");

        // set user Comments to request
        request.setAttribute("userComments", userComments);

        // forward to profile page
        Helper.forwardRequest(request, response, customerJspPath + "user_views/show_user.jsp");
    }
}
