//<editor-fold>
package com.ecommerce.admin.controller;

import com.ecommerce.bean.User;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//</editor-fold>
public class UserController extends HttpServlet {

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

        // get registered session
        HttpSession session = request.getSession();

        // check if the username exists in session 
        if (session.getAttribute("username") != null) {
            // set page title
            Helper.setTitle(request, "Users");

            // get the action param value if exists or return manage
            String action = request.getParameter("action") != null ? request.getParameter("action") : "Manage";
            if (action.equals("Manage")) {
                List<User> users;

                // check if the page param value if exists and equal Pending
                if (request.getParameter("page") != null && request.getParameter("page").equals("Pending")) {
                    // get pendings users
                    users = new UserDaoImpl(servletContext).getAllUsers(true);
                } else {
                    // get users
                    users = new UserDaoImpl(servletContext).getAllUsers(false);
                }
                // set users to the request
                request.setAttribute("users", users);

                // forword to manage page
                Helper.forwardRequest(request, response, adminJspPath + "manage_users.jsp");
            } else if (action.equals("Add")) {
                // forword to add page
                Helper.forwardRequest(request, response, adminJspPath + "add_user.jsp");
            } else if (action.equals("Edit")) {
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
                    Helper.forwardRequest(request, response, adminJspPath + "edit_user.jsp");
                } else {
                    // redirect to the previous page with error message
                    Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
                }
            } else if (action.equals("Delete")) {
                // get userId param from the request
                String userId = request.getParameter("userid");

                // return the userId if number or return 0
                long Id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

                // delete user depending on userId
                boolean userDeleted = new UserDaoImpl(servletContext).deleteUser(Id);
                if (userDeleted) {
                    // redirect to the previous page with deleted message
                    Helper.redriectToPrevPage(request, response, "user deleted", false);
                } else {
                    // redirect to the previous page with error message
                    Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
                }
            } else if (action.equals("Activate")) {

                // get userId param from the request
                String userId = request.getParameter("userid");

                // return the userId if number or return 0
                long id = userId != null && Helper.isNumber(userId) ? Long.parseLong(userId) : 0;

                // activate user depending on userId
                boolean userActivated = new UserDaoImpl(servletContext).activeUser(id);
                if (userActivated) {
                    // redirect to the previous page with deleted message
                    Helper.redriectToPrevPage(request, response, "user approved", false);
                } else {
                    // redirect to the previous page with error message
                    Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
                }
            }
        } else {
            // redirect to the login page if the username does not exists in session
            response.sendRedirect("login");
        }
    }

/////////////////////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get registered session
        HttpSession session = request.getSession();

        // check if the username exists in session
        if (session.getAttribute("username") != null) {
            // set page title
            Helper.setTitle(request, "Users");

            // get the action param value
            String action = request.getParameter("action");

            if (action.equals("Add")) {
                // get form params from the request
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String fullName = request.getParameter("full");

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

//				if (! empty($avatarName) && ! in_array($avatarExtension, $avatarAllowedExtension)) {
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
                    Helper.forwardRequest(request, response, adminJspPath + "add_user.jsp");
                } else {
                    // make new user and set info to it
                    User user = new User();
                    user.setName(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setFullName(fullName);

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
                    Helper.forwardRequest(request, response, adminJspPath + "add_user.jsp");

//					$avatar = rand(0, 10000000000) . '_' . $avatarName;
//
//					move_uploaded_file($avatarTmp, "uploads\avatars\\" . $avatar);
                }
            } else if (action.equals("Edit")) {
                // get form params from the request
                long id = Long.parseLong(request.getParameter("userid"));
                String username = request.getParameter("username");
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String email = request.getParameter("email");
                String fullName = request.getParameter("full");

                // make empty list to errors
                List<String> formErrors = new ArrayList();

                // validate the form params
                if (username != null) {
                    if (username.length() < 4) {
                        formErrors.add("Username Cant Be Less Than <strong>4 Characters</strong>");
                    }
                }

                if (username != null) {
                    if (username.length() > 20) {
                        formErrors.add("Username Cant Be More Than <strong>20 Characters</strong>");
                    }
                }

                String password = newPassword == null ? oldPassword : newPassword;

                if (username == null) {
                    formErrors.add("Username Cant Be <strong>Empty</strong>");
                }

                if (fullName == null) {
                    formErrors.add("Full Name Cant Be <strong>Empty</strong>");
                }

                if (email == null) {
                    formErrors.add("Email Cant Be <strong>Empty</strong>");
                }

                // set errors to the request
                request.setAttribute("errors", formErrors);

                // check if no errors
                if (formErrors.size() > 0) {
                    // forword to edit page
                    Helper.forwardRequest(request, response, adminJspPath + "edit_user.jsp");
                } else {
                    // make new user and set info to it
                    User user = new User();
                    user.setId(id);
                    user.setName(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setFullName(fullName);

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
                    Helper.forwardRequest(request, response, adminJspPath + "edit_user.jsp");
                }
            } else {
                // redirect to the login page if the username does not exists in session
                response.sendRedirect("login");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
