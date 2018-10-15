package com.ecommerce.customer.comment;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.user.User;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "AddCommentControllerForCustomer", urlPatterns = {"/add-comment"})
public class AddCommentController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    /**
     * doPost method created to receive Comment for item :
     * <1> receive id and comment of this item
     * <2> get item from DB
     * <3> with use ajax we won't need to: set item in request to show it again
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        JSONArray errors = new JSONArray();
        JSONObject data = new JSONObject();
        String success = null;

        // get comment param from FORM 
        String comment = request.getParameter("comment");
        // get itemId param from request
        String itemId = request.getParameter("itemid");

        // return the itemId if number or return 0
        long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // get approve item
        Item item = new ItemDaoImpl(servletContext).getApprovedItemById(id);

        // set item to request
//        request.setAttribute("item", item);

        // check if there is no comment
        if (comment == null || comment.isEmpty()) {
            errors.add("You Must Add Comment");

        } else {// if no errors in textfield Comment
            //get user id
            Long userId = getCurrentUserId(request, response);

            // make new user and set info to it
            User user = User.builder()
                    .id(userId)
                    .build();

            // make new comment and set info to it
            Comment com = Comment.builder()
                    .comment(comment)
                    .item(item)
                    .user(user)
                    .build();

            data.put("comment", comment);
            data.put("user", getCurrentUsername(request, response));

            // add comment to DB 
            boolean commentAdded = new CommentDaoImpl(servletContext).addComment(com);

            //if comment added
            if (commentAdded) {
                success = "comment added";
            } else {
                errors.add("comment NOT added");
            }
        }

        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("data", data);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }

    public long getCurrentUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long userId;
        if (CookieHelper.isCookie("userId", request, response)) {
            // get user id from cookie
            userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
        } else {
            // get userId from session
            userId = (Long) request.getSession().getAttribute("userId");
        }
        return userId;
    }
    
    public String getCurrentUsername(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user;
        if (CookieHelper.isCookie("user", request, response)) {
            // get user id from cookie
            user = CookieHelper.getCookie("user", request, response);
        } else {
            // get userId from session
            user = request.getSession().getAttribute("user").toString();
        }
        return user;
    }
}
