package com.ecommerce.customer.comment;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "EditCommentControlllerForCustomer", urlPatterns = {"/edit-comment"})
public class EditCommentController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();

    }

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Edit Comment");

        // get commentId param from the request
        String commentId = request.getParameter("commentid");
        // return the commentId if number or return 0
        long id = commentId != null && Helper.isNumber(commentId) ? Long.parseLong(commentId) : 0;
        // get comment depending on commentId
        Comment commentFounded = new CommentDaoImpl(servletContext).getCommentById(id);

        if (commentFounded != null) {
            // set the found comment to the request
            request.setAttribute("comment", commentFounded);

            // forword request to edit page
            Helper.forwardRequest(request, response, ViewPath.edit_comment);

        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        JSONArray errors = new JSONArray();
        JSONObject data = new JSONObject();
        String success = null;
        // set page title
//        Helper.setTitle(request, "Edit Comment");

        // get form params from the request
        int id = Integer.parseInt(request.getParameter("commentid"));
        String com = request.getParameter("comment");

        // make new comment and set info to it
        Comment comment = Comment.builder()
                .id(id)
                .comment(com)
                .build();

        // update comment
        boolean commentUpdated
                = new CommentDaoImpl(servletContext).updateComment(comment);

        if (!commentUpdated) {
//            String error = "error in update";
            // set error message to request if comment does not update successfully
//            request.setAttribute("error", error);
            errors.add("comment NOT added");
        } else {
            // set success message to request if comment updated successfully
//            request.setAttribute("success", "comment updated");
            success = "comment added";
            data.put("comment", comment.getComment());
            data.put("commentid", comment.getId());

        }

        // set comment to request
//        request.setAttribute("comment", comment);
        // forword request to the edit page
//        Helper.forwardRequest(request, response, ViewPath.edit_comment);
        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("data", data);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }
}
