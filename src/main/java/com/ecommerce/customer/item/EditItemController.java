//<editor-fold >
package com.ecommerce.customer.item;

import com.ecommerce.general.category.Category;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
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

@WebServlet(name = "EditItemControllerForCustomer", urlPatterns = {"/edit-item"})
public class EditItemController extends HttpServlet {

    ServletContext servletContext = null;
    Item item = null;
    List<Category> categories = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        
    }//</editor-fold >

    /**
     * doGet method do :
     * <1> receive parameter id of item from URL
     * <2> get item from DB
     * <3> set data in request to fill text fields
     */
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
            categories = new CategoryDaoImpl(servletContext).getAllSupCategories("ASC");

            // set categories to the request 
            request.setAttribute("categories", categories);
            // forward to edit item
            Helper.forwardRequest(request, response, ViewPath.edit_item);

        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

    /**
     * doPost method do :
     * <1> receive parameter from FORM
     * <2> check errors
     * <3> if no errors update item in DB
     * <4> set new item in request to fill text field again
     */
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

        // create empty list for errors
        List<String> formErrors
                // validate the form params 
                = vildateFormParams(name, description, price, countryMade, status, categoryId);

        // set errors in request
        request.setAttribute("errors", formErrors);
        //set item in request
        request.setAttribute("item", item);
        //set categories in request, for written old data in <select>tag again 
        request.setAttribute("categories", categories);

        // check errors
        if (formErrors.size() > 0) {//if error Existed in params
            // forword to edit_item page again
            Helper.forwardRequest(request, response, ViewPath.edit_item);

        } else {//if no errors in params 

            //get id of current user 
            Long userId = getCurrentUserId(request, response);

            // make new user and set info to it
            User user = User.builder()
                    .id(userId)
                    .build();

            // make new category and set info to it
            Category category = Category.builder()
                    .id(categoryId)
                    .build();

            // make new item and set info to it 
            item = Item.builder()
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

            // Update item 
            boolean itemUpdated = new ItemDaoImpl(servletContext).updateItem(item);

            //set message success or error 
            if (!itemUpdated) {
                // add new error to errors if item not added
                //<improve>formErrors.add("can not update this item");                 request.setAttribute("success", "Item Has Been Updated");
                request.setAttribute("error", "can not update this item");

            } else {
                // set success message if item added
                request.setAttribute("success", "Item Has Been Updated");
            }

            // set item to request
            request.setAttribute("item", item);
            // forword to add page
            Helper.forwardRequest(request, response, ViewPath.edit_item);
        }
    }

    public List<String> vildateFormParams(String name, String description, String price,
            String countryMade, String status, int categoryId) {
        List<String> formErrors = new ArrayList();

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

}
