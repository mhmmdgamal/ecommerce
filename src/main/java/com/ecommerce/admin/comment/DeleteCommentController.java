package com.ecommerce.admin.comment;

import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

@WebServlet(name = "DeleteCommentController", urlPatterns = {"/admin/delete-comment"})
public class DeleteCommentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get commentId param from the request
        String commentId = request.getParameter("commentid");

        // return the commentId if number or return 0
        long id = commentId != null && Helper.isNumber(commentId) ? Long.parseLong(commentId) : 0;

        // delete comment depending on the commentId
        boolean commentDeleted = new CommentDaoImpl(getServletContext()).deleteComment(id);
        
        JSONObject obj = new JSONObject();
        obj.put("success", commentDeleted);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());

    }

}
