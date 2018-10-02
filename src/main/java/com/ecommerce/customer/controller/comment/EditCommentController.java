package com.ecommerce.customer.controller.comment;

import com.ecommerce.bean.Comment;
import com.ecommerce.dao.CommentDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditCommentController", urlPatterns = {"/edit-comment"})
public class EditCommentController extends HttpServlet {

    String customerJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        customerJspPath = servletContext.getInitParameter("customerJspPath");
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
            Helper.forwardRequest(request, response, customerJspPath + "comment/edit_comment.jsp");

        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Edit Comment");

        // get form params from the request
        int id = Integer.parseInt(request.getParameter("commentid"));
        String com = request.getParameter("comment");

        // make new comment and set info to it
        Comment comment = Comment.builder()
                .id(id)
                .comment(com)
                .build();

        // update comment
        boolean commentUpdated = new CommentDaoImpl(servletContext).updateComment(comment);

        if (!commentUpdated) {
            String error = "error in update";
            // set error message to request if comment does not update successfully
            request.setAttribute("error", error);
        } else {
            // set success message to request if comment updated successfully
            request.setAttribute("success", "comment updated");
        }

        // set comment to request
        request.setAttribute("comment", comment);

        // forword request to the edit page
        Helper.forwardRequest(request, response, customerJspPath + "comment/edit_comment.jsp");

    }// </editor-fold>

}
