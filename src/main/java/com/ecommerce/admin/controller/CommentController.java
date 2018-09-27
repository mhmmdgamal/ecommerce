package com.ecommerce.admin.controller;

import com.ecommerce.bean.Comment;
import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import com.ecommerce.dao.CommentDaoImpl;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/admin/comments")
public class CommentController extends HttpServlet {

    String adminJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        adminJspPath = servletContext.getInitParameter("adminJspPath");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Comments");

        // get the action param value if exists or return manage 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "Manage";

        // get all users with out pendings users
        List<User> users = new UserDaoImpl(servletContext).getAllUsers(false);
        System.out.println(users);
        // get all items with assending order
        List<Item> items = new ItemDaoImpl(servletContext).getAllItems("ASC");

        // set users to request
        request.setAttribute("users", users);

        // set items to request
        request.setAttribute("items", items);

        if (action.equals("Manage")) {

            // get all comments with assending order
            List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments("ASC");

            // set comments to request
            request.setAttribute("comments", comments);

            // forword request to manage page
            Helper.forwardRequest(request, response, adminJspPath + "manage_comments.jsp");
        } else if (action.equals("Edit")) {
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
                Helper.forwardRequest(request, response, adminJspPath + "edit_comment.jsp");
            } else {
                // redirect to the previous page with error message
                Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
            }
        } else if (action.equals("Delete")) {
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
        } else if (action.equals("Approve")) {
            // get commentId param from the request
            String commentId = request.getParameter("commentid");

            // return the commentId if number or return 0
            long id = commentId != null && Helper.isNumber(commentId) ? Long.parseLong(commentId) : 0;

            // approve comment depending on the commentId
            boolean commentApproved = new CommentDaoImpl(servletContext).approveComment(id);
            if (commentApproved) {
                // redirect to the previous page with deleted message
                Helper.redriectToPrevPage(request, response, "comment approved", false);
            } else {
                // redirect to the previous page with error message
                Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Comments");

        // get the action param value
        String action = request.getParameter("action");

        if (action.equals("Edit")) {

            // get form params from the request
            int id = Integer.parseInt(request.getParameter("commentid"));
            String com = request.getParameter("comment");

            // make new comment and set info to it
            Comment comment = new Comment.Builder()
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
            Helper.forwardRequest(request, response, adminJspPath + "edit_comment.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}