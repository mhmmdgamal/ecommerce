package com.ecommerce.admin.user;

import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddUserConroller", urlPatterns = {"/admin/add-user"})
public class AddUserConroller extends HttpServlet {

    
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Add User");
        // forword to add page
        Helper.forwardRequest(request, response, ViewPath.add_user_admin);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Add User");

        // get form params from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full");

        // make empty list to errors
        List<String> formErrors
                //vildate FORM Params 
                = vildateFormParams(username, password, fullName, email);

//		    <improve>if (! empty($avatarName) && ! in_array($avatarExtension, $avatarAllowedExtension)) {
//					formErrors.add("This Extension Is Not <strong>Allowed</strong>");
//				}
//				if (empty($avatarName)) {
//					formErrors.add("Avatar Is <strong>Required</strong>");
//				}
//				if ($avatarSize > 4194304) {
//					formErrors.add("Avatar Cant Be Larger Than <strong>4MB</strong>");
//				}
        // set errors to the request
        request.setAttribute("errors", formErrors);

        // check if no errors
        if (formErrors.size() > 0) {
            // forword to add page
            Helper.forwardRequest(request, response, ViewPath.add_user_admin);
        } else {
            // make new user and set info to it
            User user = User.builder()
                    .name(username)
                    .password(password)
                    .email(email)
                    .fullName(fullName)
                    .build();

            // add user
            boolean userAdded = new UserDaoImpl(servletContext).addUser(user);
            if (!userAdded) {
                // add new error to errors if user not added
                formErrors.add("Sorry This User Is Exist");
            } else {
                // set success message if user added
                request.setAttribute("success", "user added successfully");
            }
            // forword to add page
            Helper.forwardRequest(request, response, ViewPath.add_user_admin);

//			<improve>  $avatar = rand(0, 10000000000) . '_' . $avatarName;
//
//					move_uploaded_file($avatarTmp, "uploads\avatars\\" . $avatar);
        }

    }

    public List<String> vildateFormParams(String username, String password,
            String fullName, String email) {

        // make empty list to errors
        List<String> formErrors = new ArrayList();

        // validate the form params
        if (username != null) {
            if (username.length() < 4) {
                formErrors.add("Username Cant Be Less Than <strong>4 Characters</strong>");
            }

            if (username.length() > 20) {
                formErrors.add("Username Cant Be More Than <strong>20 Characters</strong>");
            }
        } else {
            formErrors.add("Username Cant Be <strong>Empty</strong>");
        }

        if (password == null) {
            formErrors.add("Password Cant Be <strong>Empty</strong>");
        }

        if (fullName == null) {
            formErrors.add("Full Name Cant Be <strong>Empty</strong>");
        }

        if (email == null) {
            formErrors.add("Email Cant Be <strong>Empty</strong>");
        }
        return formErrors;
    }

}
