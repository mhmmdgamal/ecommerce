package com.ecommerce.admin.controller;

import com.ecommerce.dao.CommentDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteCommentController", urlPatterns = {"/admin/delete-comment"})
public class DeleteCommentController extends HttpServlet {

    String adminJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        adminJspPath = servletContext.getInitParameter("adminJspPath");
    }

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
            // get commentId param from the request
            String commentId = request.getParameter("commentid");

            // return the commentId if number or return 0
            long id = commentId != null && Helper.isNumber(commentId) ? Long.parseLong(commentId) : 0;

            // delete comment depending on the commentId
            boolean commentDeleted = new CommentDaoImpl(servletContext).deleteComment(id);
            if (commentDeleted) {
                // redirect to the previous page with deleted message
                Helper.redriectToPrevPage(request, response, "comment deleted", false);
            } else {
                // redirect to the previous page with error message
                Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
            }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Users");

    }// </editor-fold>

}