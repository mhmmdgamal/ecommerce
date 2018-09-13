/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.admin.controller;

import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import com.ecommerce.bean.Category;
import com.ecommerce.bean.Comment;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.dao.CommentDaoImpl;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.dao.UserDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ItemController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get registered session
        HttpSession session = request.getSession();

        // check if the username exists in session 
        if (session.getAttribute("username") != null) {
            // set page title
            Helper.setTitle(request, "Items");

            // get the action param value if exists or return manage
            String action = request.getParameter("action") != null ? request.getParameter("action") : "Manage";

            // get all users with out pendings users
            List<User> users = new UserDaoImpl(getServletContext()).getAllUsers(false);

            // get all categories with assending order
            List<Category> categories = new CategoryDaoImpl(getServletContext()).getAllCategories("ASC");

            // get all comments with assending order
            List<Comment> comments = new CommentDaoImpl(getServletContext()).getAllComments("ASC");

            // set users to request
            request.setAttribute("users", users);

            // set categories to request
            request.setAttribute("categories", categories);

            // set comments to request
            request.setAttribute("comments", comments);

            if (action.equals("Manage")) {
                // get all items with assending 
                List<Item> items = new ItemDaoImpl(getServletContext()).getAllItems("ASC");

                // set items to request
                request.setAttribute("items", items);

                // forword request to manage page
                Helper.forwardRequest(request, response, "/WEB-INF/views/admin/manage_items.jsp");
            } else if (action.equals("Add")) {
                // forword request to add page
                Helper.forwardRequest(request, response, "/WEB-INF/views/admin/add_item.jsp");
            } else if (action.equals("Edit")) {
                // get itemId param from the request
                String itemId = request.getParameter("itemid");

                // return the itemId if number or return 0
                long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

                // get itemComments with assending order
                List<Comment> itemComments = new ItemDaoImpl(getServletContext()).getItemComments(id, "ASC");

                // set the itemComments to request
                request.setAttribute("itemComments", itemComments);

                // get item depending on commentId
                Item itemFounded = new ItemDaoImpl(getServletContext()).getItemById(id);
                if (itemFounded != null) {
                    // set the found item to request
                    request.setAttribute("item", itemFounded);

                    // forword request to edit page
                    Helper.forwardRequest(request, response, "/WEB-INF/views/admin/edit_item.jsp");
                } else {
                    // redirect to the previous page with error message
                    Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
                }
            } else if (action.equals("Delete")) {
                // get itemId param from the request
                String itemId = request.getParameter("itemid");

                // return the itemId if number or return 0
                long Id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

                // delete item depending on the itemId
                boolean itemDeleted = new ItemDaoImpl(getServletContext()).deleteItem(Id);

                if (itemDeleted) {
                    // redirect to the previous page with deleted message
                    Helper.redriectToPrevPage(request, response, "item deleted", false);
                } else {
                    // redirect to the previous page with error message
                    Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
                }
            } else if (action.equals("Approve")) {
                // get itemId param from the request
                String itemId = request.getParameter("itemid");

                // return the itemIdif number or return 0
                long Id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

                // approve item depending on the itemId
                boolean itemActivated = new ItemDaoImpl(getServletContext()).approveItem(Id);

                if (itemActivated) {
                    // redirect to the previous page with deleted message
                    Helper.redriectToPrevPage(request, response, "item approved", false);
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get registered session
        HttpSession session = request.getSession();

        // check if the username exists in session
        if (session.getAttribute("username") != null) {
            // set page title
            Helper.setTitle(request, "Items");

            // get the action param value
            String action = request.getParameter("action");

            // get all users with out pendings users
            List<User> users = new UserDaoImpl(getServletContext()).getAllUsers(false);

            // get all categories with assending order
            List<Category> categories = new CategoryDaoImpl(getServletContext()).getAllCategories("ASC");

            // get all comments with assending order
            List<Comment> comments = new CommentDaoImpl(getServletContext()).getAllComments("ASC");

            // set users to request
            request.setAttribute("users", users);

            // set categories to request
            request.setAttribute("categories", categories);

            // set comments to request
            request.setAttribute("comments", comments);

            if (action.equals("Add")) {
                // get form params from the request
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String price = request.getParameter("price");
                String countryMade = request.getParameter("country");
                String status = request.getParameter("status");
                int userId = Integer.parseInt(request.getParameter("user"));
                int categoryId = Integer.parseInt(request.getParameter("category"));
                String tags = request.getParameter("tags");

                // make empty list to errors
                List<String> formErrors = new ArrayList();

                // validate the form params
                if (name == null) {
                    formErrors.add("Name Can't be <strong>Empty</strong>");
                }

                if (description == null) {
                    formErrors.add("Description Can't be <strong>Empty</strong>");
                }

                if (price == null) {
                    formErrors.add("Price Can't Be <strong>Empty</strong>");
                }

                if (countryMade == null) {
                    formErrors.add("Country Can't Be <strong>Empty</strong>");
                }

                if (status.equals("0")) {
                    formErrors.add("You Must Choose the <strong>Status</strong>");
                }

                if (userId == 0) {
                    formErrors.add("You Must Choose the <strong>User</strong>");
                }

                if (categoryId == 0) {
                    formErrors.add("You Must Choose the <strong>Category</strong>");
                }

                // set errors to the request
                request.setAttribute("errors", formErrors);

                // check if no errors
                if (formErrors.size() > 0) {
                    // forword to add page
                    Helper.forwardRequest(request, response, "/WEB-INF/views/admin/add_item.jsp");
                } else {
                    // make new user and set info to it
                    User user = new User();
                    user.setId(userId);

                    // make new category and set info to it
                    Category category = new Category();
                    category.setId(categoryId);

                    // make new item and set info to it 
                    Item item = new Item();
                    item.setName(name);
                    item.setDescription(description);
                    item.setPrice(price);
                    item.setCountryMade(countryMade);
                    item.setStatus(status);
                    item.setUser(user);
                    item.setCategory(category);
                    item.setTags(tags);

                    // add item 
                    boolean itemAdded = new ItemDaoImpl(getServletContext()).addItem(item);

                    if (!itemAdded) {
                        // add new error to errors if item not added
                        formErrors.add("Sorry This Item Is Exist");
                    } else {
                        // set success message if item added
                        request.setAttribute("success", "item added");
                    }
                    // forword to add page
                    Helper.forwardRequest(request, response, "/WEB-INF/views/admin/add_item.jsp");
                }
            } else if (action.equals("Edit")) {
                // get itemid param from the request
                int id = Integer.parseInt(request.getParameter("itemid"));

                // get itemComments depending on id with assending order
                List<Comment> itemComments = new ItemDaoImpl(getServletContext()).getItemComments(id, "ASC");

                // set itemComments to requset
                request.setAttribute("itemComments", itemComments);

                // get form params from the request
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String price = request.getParameter("price");
                String countryMade = request.getParameter("country");
                String status = request.getParameter("status");
                int userId = Integer.parseInt(request.getParameter("user"));
                int categoryId = Integer.parseInt(request.getParameter("category"));
                String tags = request.getParameter("tags");

                // make empty list to errors
                List<String> formErrors = new ArrayList();

                // validate the form params
                if (name == null) {
                    formErrors.add("Name Can't be <strong>Empty</strong>");
                }

                if (description == null) {
                    formErrors.add("Description Can't be <strong>Empty</strong>");
                }

                if (price == null) {
                    formErrors.add("Price Can't Be <strong>Empty</strong>");
                }

                if (countryMade == null) {
                    formErrors.add("Country Can't Be <strong>Empty</strong>");
                }

                if (status.equals("0")) {
                    formErrors.add("You Must Choose the <strong>Status</strong>");
                }

                if (userId == 0) {
                    formErrors.add("You Must Choose the <strong>User</strong>");
                }

                if (categoryId == 0) {
                    formErrors.add("You Must Choose the <strong>Category</strong>");
                }

                // set errors to the request
                request.setAttribute("errors", formErrors);

                if (formErrors.size() > 0) {
                    Helper.forwardRequest(request, response, "/WEB-INF/views/admin/edit_item.jsp");
                } else {
                    // make new item 
                    Item item = new Item();

                    // make new user
                    User user = new User();

                    // make new category 
                    Category category = new Category();

                    // set info to user
                    user.setId(userId);
                    // set info to category
                    category.setId(categoryId);

                    // set info to item
                    item.setId(id);
                    item.setName(name);
                    item.setDescription(description);
                    item.setPrice(price);
                    item.setCountryMade(countryMade);
                    item.setStatus(status);
                    item.setUser(user);
                    item.setCategory(category);
                    item.setTags(tags);

                    // update item
                    boolean itemUpdated = new ItemDaoImpl(getServletContext()).updateItem(item);

                    if (!itemUpdated) {
                        // add new error to errors if item not updated
                        formErrors.add("error in update");
                    } else {
                        // set success message if item updated
                        request.setAttribute("success", "item updated");
                    }

                    // set item to request
                    request.setAttribute("item", item);

                    // forword to edit page
                    Helper.forwardRequest(request, response, "/WEB-INF/views/admin/edit_item.jsp");
                }
            } else {
                // redirect to the login page if the username does not exists in session 
                response.sendRedirect("login");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}