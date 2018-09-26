// <editor-fold>
package com.ecommerce.customer.controller;

import com.ecommerce.bean.Category;
import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.helper.CookieHelper;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

@WebServlet("/new-item")
public class AddNewItemController extends HttpServlet {

    ServletContext servletContext = null;
    String customerJspPath = null;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        servletContext = getServletContext();
        customerJspPath = servletContext.getInitParameter("customerJspPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "New Item");

        // get all categories from database with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllCategories("ASC");

        // set categories to the request
        request.setAttribute("categories", categories);

        // forward to new item page
        Helper.forwardRequest(request, response, customerJspPath + "add_new_item.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "New Item");

        // get all categories from database with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllCategories("ASC");

        // set categories to the request
        request.setAttribute("categories", categories);

        // get FORM params from the request
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String countryMade = request.getParameter("country");
        String status = request.getParameter("status");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        String tags = request.getParameter("tags");

        // make empty list to errors
        List<String> formErrors
                //vildate params of FORM 
                = vildateParams(name, description, price, countryMade, status, categoryId);
        // set errors to the request
        request.setAttribute("errors", formErrors);

        // check if no errors
        if (formErrors.size() > 0) {
            // forword to add page (to show error for user)
            Helper.forwardRequest(request, response, customerJspPath + "add_new_item.jsp");

        } else {
            //get id of current user 
            Long userId = getCurrentUserId(request, response);
            //get item of new data 
            Item item = createNewItem(name, description, price, countryMade, status, tags, userId, categoryId);

            // add item to DB 
            boolean itemAdded = new ItemDaoImpl(servletContext).addItem(item);
            //set message success or error
            setMessageAlert(itemAdded, request);
            // forword to add page
            Helper.forwardRequest(request, response, customerJspPath + "add_new_item.jsp");
        }
    }

    public List<String> vildateParams(String name, String description,
            String price, String countryMade, String status, long categoryId) {

        // make empty list to errors
        List<String> formErrors = new ArrayList();

        // validate the form params
        if (name == null || name.length() < 4) {
            formErrors.add("Item Title Must Be At Least 4 Characters");
        }

        if (description == null || description.length() < 10) {
            formErrors.add("Item Description Must Be At Least 10 Characters");
        }

        if (price == null) {
            formErrors.add("Item Price Cant Be Empty");
        }

        if (countryMade == null || countryMade.length() < 2) {
            formErrors.add("Item Title Must Be At Least 2 Characters");
        }

        if (status.equals("0")) {
            formErrors.add("Item Status Cant Be Empty");
        }

        if (categoryId == 0) {
            formErrors.add("Item Category Cant Be Empty");
        }
        return formErrors;
    }

    public long getCurrentUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long userId;

        if (CookieHelper.isCookie("userId", request, response)) {
            userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
        } else {
            // get userId from session
            userId = (Long) request.getSession().getAttribute("userId");
        }
        return userId;
    }

    public Item createNewItem(String name, String description, String price,
            String countryMade, String status, String tags, long userId, long categoryId) {

        // make new user and set info to it
        User user = new User.Builder()
                .id(userId)
                .build();

        // make new category and set info to it
        Category category = new Category.Builder()
                .id(categoryId)
                .build();

        // make new item and set info to it 
        Item item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .countryMade(countryMade)
                .status(status)
                .user(user)
                .category(category)
                .tags(tags)
                .build();
        return item;
    }

    public void setMessageAlert(boolean itemAdded, HttpServletRequest request) {

        if (!itemAdded) {
            // add new error to errors if item not added
            //<improve>formErrors.add("Sorry This Item Is Exist");
            request.setAttribute("error", "can not add this item! try again!");

        } else {
            // set success message if item added
            request.setAttribute("success", "Item Has Been Added");
        }
    }

}