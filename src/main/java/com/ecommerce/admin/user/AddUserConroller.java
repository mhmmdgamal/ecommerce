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
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

        // forword to add page
        Helper.forwardRequest(request, response, ViewPath.add_user_admin);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray errors = new JSONArray();
        String success = null;

        // get form params from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full");

        // hash password
        String passwordHashed = HashHelper.stringHash(password);

        //vildate FORM Params 
        errors.addAll(vildateFormParams(username, password, fullName, email));

        if (!(errors.size() > 0)) {

            // make new user and set info to it
            User user = User.builder()
                    .name(username)
                    .password(passwordHashed)
                    .email(email)
                    .fullName(fullName)
                    .date(Helper.getCurrentDate())
                    .build();

            UserDaoImpl userDao = new UserDaoImpl(servletContext);
            // add user
            boolean userAdded = userDao.addUser(user);
            if (!userAdded) {
                // add new error to errors if user not added
                errors.add("Sorry This User Is Exist");
            } else {
                success = "user added successfully";
                data.put("id", userDao.getLastUserId());
                data.put("name", user.getName());
                data.put("email", user.getEmail());
                data.put("fullName", user.getFullName());
                data.put("date", "" + user.getDate());
            }
        }

        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("data", data);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());

    }

    public JSONArray vildateFormParams(String username, String password,
            String fullName, String email) {

        // make empty list to errors
        JSONArray errors = new JSONArray();

        // validate the form params
        if (StringUtils.isEmpty(username)) {
            errors.add("Username Cant Be <strong>Empty</strong>");
        } else {
            if (username.length() < 4) {
                errors.add("Username Cant Be Less Than <strong>4 Characters</strong>");
            }

            if (username.length() > 20) {
                errors.add("Username Cant Be More Than <strong>20 Characters</strong>");
            }
        }

        if (StringUtils.isEmpty(password)) {
            errors.add("Password Cant Be <strong>Empty</strong>");
        }

        if (StringUtils.isEmpty(fullName)) {
            errors.add("Full Name Cant Be <strong>Empty</strong>");
        }

        if (StringUtils.isEmpty(email)) {
            errors.add("Email Cant Be <strong>Empty</strong>");
        }
        return errors;
    }

}
