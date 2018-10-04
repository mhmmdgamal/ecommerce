package com.ecommerce.admin.user;

import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteUserController", urlPatterns = {"/admin/delete-user"})
public class DeleteUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Delete User");

        // get userId param from the request
        String userId = request.getParameter("userid");

        // return the userId if number or return 0
        long Id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

        // delete user depending on userId
        boolean userDeleted = new UserDaoImpl(getServletContext()).deleteUser(Id);
        if (userDeleted) {
            // redirect to the previous page with deleted message
            Helper.redriectToPrevPage(request, response, "user deleted", false);
            //<error> not found Helper.forwardRequest(request, response, PathsHelper.getAdminCategory("add_category")  "manage_user.jsp");

        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

}
