package com.ecommerce.admin.user;

import com.ecommerce.general.helper.HashHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "EditUserController", urlPatterns = {"/admin/edit-user"})
public class EditUserController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get userId param from the request
        String userId = request.getParameter("userid");

        // return the userId if number or return 0
        long Id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

        // get user depending on userId
        User userFounded = new UserDaoImpl(servletContext).getUserById(Id);
//        if (userFounded != null) {
        // set the found user to the request
        request.setAttribute("user", userFounded);

        // forword to edit page
        Helper.forwardRequest(request, response, ViewPath.edit_user_admin);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray errors = new JSONArray();
        String success = null;

        // get the action param value if exists or return manage
        // get form params from the request
        long id = Long.parseLong(request.getParameter("userid"));
        String username = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full");

        //<improve> write **** in text field password  
        String password = (newPassword == null || newPassword.isEmpty()) ? oldPassword : HashHelper.stringHash(newPassword);
        
        // vildate parameter of FORM 
        errors.addAll(vildateFormParams(username, fullName, email));

        if (!(errors.size() > 0)) {
            // make new user and set info to it
            User user = User.builder()
                    .id(id)
                    .name(username)
                    .password(password)
                    .email(email)
                    .fullName(fullName)
                    .date(Helper.getCurrentDate())
                    .build();

            // update user
            boolean userUpdated = new UserDaoImpl(servletContext).updateUser(user);

            if (!userUpdated) {
                // add new error to errors if user not updated
                errors.add("error in update");
            } else {
                // set success message if user updated
                success = "user updated";
                data.put("id", id);
                data.put("name", user.getName());
                data.put("email", user.getEmail());
                data.put("fullName", user.getFullName());
            }
        }

        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("data", data);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
        
    }

    public JSONArray vildateFormParams(String username, String fullName,
            String email) {

        // make empty list to errors
        JSONArray errors = new JSONArray();

        // validate the form params
        if (username == null || username.isEmpty()) {
            errors.add("Username Cant Be <strong>Empty</strong>");
        } else {
            if (username.length() < 4) {
                errors.add("Username Cant Be Less Than <strong>4 Characters</strong>");
            }

            if (username.length() > 20) {
                errors.add("Username Cant Be More Than <strong>20 Characters</strong>");
            }
        }

        if (fullName == null || fullName.isEmpty()) {
            errors.add("Full Name Cant Be <strong>Empty</strong>");
        }

        if (email == null || email.isEmpty()) {
            errors.add("Email Cant Be <strong>Empty</strong>");
        }
        return errors;
    }

}
