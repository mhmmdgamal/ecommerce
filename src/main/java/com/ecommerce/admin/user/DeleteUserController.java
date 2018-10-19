package com.ecommerce.admin.user;

import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.user.UserDaoImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

@WebServlet(name = "DeleteUserController", urlPatterns = {"/admin/delete-user"})
public class DeleteUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get userId param from the request
        String userId = request.getParameter("userid");

        // return the userId if number or return 0
        long Id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

        // delete user depending on userId
        boolean userDeleted = new UserDaoImpl(getServletContext()).deleteUser(Id);

        JSONObject obj = new JSONObject();
        obj.put("success", userDeleted);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
        
    }

}
