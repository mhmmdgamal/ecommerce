package com.ecommerce.admin.comment;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/admin/manage-comments")
public class ManageCommentController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Manage Comments");

        // get all users with out pendings users
        List<User> users = new UserDaoImpl(servletContext).getAllUsers(false);
//        System.out.println(users);
        // get all items with assending order
        List<Item> items = new ItemDaoImpl(servletContext).getAllItems("ASC");

        // get all comments with assending order
        List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments("ASC");

        // set users to request
        request.setAttribute("users", users);
        // set items to request
        request.setAttribute("items", items);
        // set comments to request
        request.setAttribute("comments", comments);

        // forword request to manage page
        Helper.forwardRequest(request, response, ViewPath.manage_comment_admin);
    }
}
