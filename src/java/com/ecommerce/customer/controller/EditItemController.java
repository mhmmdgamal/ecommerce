package com.ecommerce.customer.controller;

import com.ecommerce.bean.Category;
import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.helper.CookieHelper;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditItemController", urlPatterns = {"/edit-item"})
public class EditItemController extends HttpServlet {

    ServletContext servletContext = null;
    String customerJspPath = null;
    Item item = null;
    List<Category> categories = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        customerJspPath = servletContext.getInitParameter("customerJspPath");
    }

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Edit Item");

        // get itemId param from the request
        String itemId = request.getParameter("itemid");

        // return the itemId if number or return 0
        long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // get approve item
        item = new ItemDaoImpl(servletContext).getItemById(id);

        if (item != null) {
            // set item to request
            request.setAttribute("item", item);

            // get all categories from database with assending order
            categories = new CategoryDaoImpl(servletContext).getAllCategories("ASC");

            // set categories to the request
            request.setAttribute("categories", categories);

            Helper.forwardRequest(request, response, customerJspPath + "edit_item.jsp");

        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get form params from the request
        int id = Integer.parseInt(request.getParameter("itemid"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String countryMade = request.getParameter("country");
        String status = request.getParameter("status");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        String tags = request.getParameter("tags");

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

        // set errors to the request
        request.setAttribute("errors", formErrors);

        //set item and categories because when we click on (edit Item button) 
        //we can write data in boxes again 
        request.setAttribute("item", item);
        request.setAttribute("categories", categories);

        // check if no errors
        if (formErrors.size() > 0) {
            // forword to add page
            Helper.forwardRequest(request, response, customerJspPath + "edit_item.jsp");
        } else {

            Long userId;
            
            if (CookieHelper.isCookie("userId", request, response)) {
                userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
            } else {
                // get userId from session
                userId = (Long) request.getSession().getAttribute("userId");
            }

            // make new user and set info to it
            User user = new User.Builder()
                    .id(userId)
                    .build();

            // make new category and set info to it
            Category category = new Category.Builder()
                    .id(categoryId)
                    .build();

            // make new item and set info to it 
            item = new Item.Builder()
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

            // add item 
            boolean itemUpdated = new ItemDaoImpl(servletContext).updateItem(item);

            if (!itemUpdated) {
                // add new error to errors if item not added
                formErrors.add("can not update this item");
            } else {
                // set success message if item added
                request.setAttribute("success", "Item Has Been Updated");
            }

            // set item to request
            request.setAttribute("item", item);

            // forword to add page
            Helper.forwardRequest(request, response, customerJspPath + "edit_item.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
