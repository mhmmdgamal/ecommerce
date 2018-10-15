package com.ecommerce.customer.comment;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.comment.CommentDaoImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

@WebServlet(name = "EditCommentControlllerC", urlPatterns = {"/edit-comment"})
public class EditCommentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get FORM params from the request
        int id = Integer.parseInt(request.getParameter("commentid"));
        String com = request.getParameter("comment");

        // make new comment and set info to it
        Comment comment = Comment.builder()
                .id(id)
                .comment(com)
                .build();

        // update comment
        boolean commentUpdated
                = new CommentDaoImpl(getServletContext()).updateComment(comment);

        JSONObject obj = new JSONObject();
        obj.put("success", commentUpdated);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }
}
