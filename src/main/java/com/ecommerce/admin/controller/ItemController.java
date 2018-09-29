// <editor-fold>
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
// </editor-fold>

@WebServlet("/admin/items")
public class ItemController extends HttpServlet {

    String adminJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        adminJspPath = servletContext.getInitParameter("adminJspPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Items");

        // get the action param value if exists or return manage
        String action = request.getParameter("action") != null ? request.getParameter("action") : "Manage";

        // get all users with out pendings users
        List<User> users = new UserDaoImpl(servletContext).getAllUsers(false);

        // get all categories with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllSupCategories("ASC");

        // get all comments with assending order
        List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments("ASC");

        // set users to request
        request.setAttribute("users", users);

        // set categories to request
        request.setAttribute("categories", categories);

        // set comments to request
        request.setAttribute("comments", comments);

        if (action.equals("Manage")) {
            // get all items with assending order
            List<Item> items = new ItemDaoImpl(servletContext).getAllItems("ASC");

            // set items to request
            request.setAttribute("items", items);

            // forword request to manage page
            Helper.forwardRequest(request, response, adminJspPath + "manage_items.jsp");
        } else if (action.equals("Add")) {
            // forword request to add page
            Helper.forwardRequest(request, response, adminJspPath + "add_item.jsp");
        } else if (action.equals("Edit")) {
            // get itemId param from the request
            String itemId = request.getParameter("itemid");

            // return the itemId if number or return 0
            long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

            // get itemComments with assending order
            List<Comment> itemComments = new ItemDaoImpl(servletContext).getItemComments(id, "ASC");

            // set the itemComments to request
            request.setAttribute("itemComments", itemComments);

            // get item depending on commentId
            Item itemFounded = new ItemDaoImpl(servletContext).getItemById(id);
            if (itemFounded != null) {
                // set the found item to request
                request.setAttribute("item", itemFounded);

                // forword request to edit page
                Helper.forwardRequest(request, response, adminJspPath + "edit_item.jsp");
            } else {
                // redirect to the previous page with error message
                Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
            }
        } else if (action.equals("Delete")) {
            // get itemId param from the request
            String itemId = request.getParameter("itemid");

            // return the itemId if number or return 0
            long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

            // delete item depending on the itemId
            boolean itemDeleted = new ItemDaoImpl(servletContext).deleteItem(id);

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
            boolean itemActivated = new ItemDaoImpl(servletContext).approveItem(Id);

            if (itemActivated) {
                // redirect to the previous page with deleted message
                Helper.redriectToPrevPage(request, response, "item approved", false);
            } else {
                // redirect to the previous page with error message
                Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Items");

        // get the action param value
        String action = request.getParameter("action");

        // get all users with out pendings users
        List<User> users = new UserDaoImpl(servletContext).getAllUsers(false);

        // get all categories with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllSupCategories("ASC");

        // get all comments with assending order
        List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments("ASC");

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
                Helper.forwardRequest(request, response, adminJspPath + "add_item.jsp");
            } else {
                // make new user and set info to it
                User user = User.builder()
                        .id(userId)
                        .build();

                // make new category and set info to it
                Category category = Category.builder()
                        .id(userId)
                        .build();

                // make new item and set info to it 
                Item item = Item.builder()
                        .name(name)
                        .description(description)
                        .price(price)
                        .countryMade(countryMade)
                        .status(status)
                        .user(user)
                        .category(category)
                        .tags(tags)
                        .build();

                // add item 
                boolean itemAdded = new ItemDaoImpl(servletContext).addItem(item);

                if (!itemAdded) {
                    // add new error to errors if item not added
                    formErrors.add("Sorry This Item Is Exist");
                } else {
                    // set success message if item added
                    request.setAttribute("success", "item added");
                }
                // forword to add page
                Helper.forwardRequest(request, response, adminJspPath + "add_item.jsp");
            }
        } else if (action.equals("Edit")) {
            // get itemid param from the request
            int id = Integer.parseInt(request.getParameter("itemid"));

            // get itemComments depending on id with assending order
            List<Comment> itemComments = new ItemDaoImpl(servletContext).getItemComments(id, "ASC");

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
                Helper.forwardRequest(request, response, adminJspPath + "edit_item.jsp");
            } else {
               // make new user and set info to it
                User user = User.builder()
                        .id(userId)
                        .build();

                // make new category and set info to it
                Category category = Category.builder()
                        .id(userId)
                        .build();

                // make new item and set info to it 
                Item item = Item.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .price(price)
                        .countryMade(countryMade)
                        .status(status)
                        .user(user)
                        .category(category)
                        .tags(tags)
                        .build();

                // update item
                boolean itemUpdated = new ItemDaoImpl(servletContext).updateItem(item);

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
                Helper.forwardRequest(request, response, adminJspPath + "edit_item.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
