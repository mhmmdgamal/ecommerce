//<editor-fold>
package com.ecommerce.admin.user;

import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

//</editor-fold>
@WebServlet("/admin/manage-users")
public class ManageUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Users");

        List<User> users;

        // check if the page param value if exists and equal Pending
        if (request.getParameter("page") != null && request.getParameter("page").equals("Pending")) {
            // get pendings users
            users = new UserDaoImpl(getServletContext()).getAllUsers(true);
        } else {
            // get users
            users = new UserDaoImpl(getServletContext()).getAllUsers(false);
        }
        // set users to the request
        request.setAttribute("users", users);

        // forword to manage page
        Helper.forwardRequest(request, response, ViewPath.manage_user_admin);
    }

}
