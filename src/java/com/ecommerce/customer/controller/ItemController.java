// <editor-fold>
package com.ecommerce.customer.controller;

import com.ecommerce.bean.Comment;
import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import com.ecommerce.dao.CommentDaoImpl;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.helper.CookieHelper;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

@WebServlet("/items")
public class ItemController extends HttpServlet {

    String customerJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        customerJspPath = servletContext.getInitParameter("customerJspPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Items");

        // get itemId param from the request
        String itemId = request.getParameter("itemid");

        // return the itemId if number or return 0
        long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // get item
        Item itemFounded = new ItemDaoImpl(servletContext).getItemById(id);

        if (itemFounded != null) {
            // check if item is approved
            if (itemFounded.getApprove() == 1) {
                // set item to request
                request.setAttribute("item", itemFounded);
            } else {
                Long userId;

                if (CookieHelper.isCookie("userId", request, response)) {
                    // get user id from cookie
                    userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
                } else {
                    // get userId from session
                    userId = (Long) request.getSession().getAttribute("userId");
                }

                // check if user ow this item                
                if (itemFounded.getUser().getId() == userId) {
                    // set item to request
                    request.setAttribute("item", itemFounded);
                }
            }
            // get all comments with descinding order links with item id
            List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments(id, "DESC");

            // set comments to request
            request.setAttribute("comments", comments);

            // forword request to manage page
            Helper.forwardRequest(request, response, customerJspPath + "show_item.jsp");
        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Items");

        // get form param
        String comment = request.getParameter("comment");

        // make new list of errors
        List<String> formErrors = new ArrayList();

        // set message errors to the request
        request.setAttribute("formErrors", formErrors);

        // get itemId param from the request
        String itemId = request.getParameter("itemid");

        // return the itemId if number or return 0
        long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // get approve item
        Item item = new ItemDaoImpl(servletContext).getApprovedItemById(id);

        // set item to request
        request.setAttribute("item", item);

        // get all comments with descinding order links with item id
        List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments(id, "DESC");

        // set comments to request
        request.setAttribute("comments", comments);

        // check if there is no comment
        if (comment == null || comment.isEmpty()) {
            // add error to form errors
            formErrors.add("You Must Add Comment");

            // forward to show items page
            Helper.forwardRequest(request, response, customerJspPath + "show_item.jsp");
        } else {
            Long userId;

            if (CookieHelper.isCookie("userId", request, response)) {
                userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
            } else {
                // get userId from session
                userId = (Long) request.getSession().getAttribute("userId");
            }

            // make new user and set info to it
            User user = new User.Builder()
                    .id(userId)
                    .build();

            // make new comment and set info to it
            Comment com = new Comment.Builder()
                    .comment(comment)
                    .item(item)
                    .user(user)
                    .build();

            // add comment
            boolean commentAdded = new CommentDaoImpl(servletContext).addComment(com);

            if (!commentAdded) {
                String error = "error in add";

                // set error message to request if comment does not update successfully
                request.setAttribute("error", error);
            } else {
                // set success message to request if comment updated successfully
                request.setAttribute("success", "comment added");
            }

            // forword request to manage page
            Helper.forwardRequest(request, response, customerJspPath + "show_item.jsp");
        }

//        long userId = Long.parseLong(request.getSession().getAttribute("userId") + "");
//        if (userId == item.getUser().getId() || true) {
//            request.getSession().setAttribute("myItem", true);
//        }
    }

    @Override
    public String getServletInfo() {
        return " this controller show one item for one Category by itemId get from url";
    }

}
