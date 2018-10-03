package com.ecommerce.admin.user;

import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditUserController", urlPatterns = {"/admin/edit-user"})
public class EditUserController extends HttpServlet {

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

        // set page title
        Helper.setTitle(request, "Edit Users");

        // get userId param from the request
        String userId = request.getParameter("userid");

        // return the userId if number or return 0
        long Id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

        // get user depending on userId
        User userFounded = new UserDaoImpl(servletContext).getUserById(Id);
        if (userFounded != null) {
            // set the found user to the request
            request.setAttribute("user", userFounded);

            // forword to edit page
            Helper.forwardRequest(request, response, adminJspPath + "user_views/edit_user.jsp");
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

        // get the action param value if exists or return manage
        // get form params from the request
        long id = Long.parseLong(request.getParameter("userid"));
        String username = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full");

        // make empty list to errors
        List<String> formErrors
                // vildate parameter of FORM 
                = vildateFormParams(username, fullName, email);
        //<improve> write **** in text field password  
        String password = newPassword == null ? oldPassword : newPassword;

        // set errors to the request
        request.setAttribute("errors", formErrors);

        // check if no errors
        if (formErrors.size() > 0) {
            // forword to edit page
            Helper.forwardRequest(request, response, adminJspPath + "user_views/edit_user.jsp");
        } else {
            // make new user and set info to it
            User user = User.builder()
                    .id(id)
                    .name(username)
                    .password(password)
                    .email(email)
                    .fullName(fullName)
                    .build();

            // add user to the request
            request.setAttribute("user", user);

            // update user
            boolean userUpdated = new UserDaoImpl(servletContext).updateUser(user);

            if (!userUpdated) {
                // add new error to errors if user not updated
                formErrors.add("error in update");
            } else {
                // set success message if user updated
                request.setAttribute("success", "user updated");
            }
            // forword to edit page
            Helper.forwardRequest(request, response, adminJspPath + "user_views/edit_user.jsp");
        }
    }// </editor-fold>

    public List<String> vildateFormParams(String username, String fullName,
            String email) {

        // make empty list to errors
        List<String> formErrors = new ArrayList();

        // validate the form params
        if (username != null) {
            if (username.length() < 4) {
                formErrors.add("Username can not Be Less Than <strong>4 Characters</strong>");
            }
        }

        if (username != null) {
            if (username.length() > 20) {
                formErrors.add("Username can not Be More Than <strong>20 Characters</strong>");
            }
        }

        if (username == null) {
            formErrors.add("Username can not Be <strong>Empty</strong>");
        }

        if (fullName == null) {
            formErrors.add("Full Name can not Be <strong>Empty</strong>");
        }

        if (email == null) {
            formErrors.add("Email can not Be <strong>Empty</strong>");
        }
        return formErrors;
    }

}
