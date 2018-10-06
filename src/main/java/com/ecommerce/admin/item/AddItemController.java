package com.ecommerce.admin.item;

import com.ecommerce.general.category.Category;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.helper.PathsHelper;
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

@WebServlet(name = "AddItemController", urlPatterns = {"/admin/add-item"})
public class AddItemController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();

    }

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Add Item");

        // get all users with out pendings users
        List<User> users = new UserDaoImpl(servletContext).getAllUsers(false);
        // get all categories with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllCategories("ASC");

        // set users to request
        request.setAttribute("users", users);
        // set categories to request
        request.setAttribute("categories", categories);

        // forword request to add page
        Helper.forwardRequest(request, response, ViewPath.add_item_admin);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Add Item");

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
        List<String> formErrors
                //vildate parameter of FORM 
                = vildateFormParams(name, description, price, countryMade, status, userId, categoryId);
        // set errors to the request
        request.setAttribute("errors", formErrors);

        // check if no errors
        if (formErrors.size() > 0) {
            // forword to add page
            Helper.forwardRequest(request, response, ViewPath.add_item_admin);
        } else {
            // make new user and set info to it
            User user = User.builder()
                    .id(userId)
                    .build();

            // make new category and set info to it
            Category category = Category.builder()
                    .id(categoryId)
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
            Helper.forwardRequest(request, response, ViewPath.add_item_admin);
        }
    }// </editor-fold>

    public List<String> vildateFormParams(String name, String description, String price,
            String countryMade, String status, int userId, int categoryId) {

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
        return formErrors;
    }
}
