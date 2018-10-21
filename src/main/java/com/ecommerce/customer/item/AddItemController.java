// <editor-fold>
package com.ecommerce.customer.item;

import com.ecommerce.general.category.Category;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ControllerPath;
import com.ecommerce.general.path.ResourcePath;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
// </editor-fold>

@WebServlet(name = "AddItemControllerC", urlPatterns = {"/add-item"})

public class AddItemController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        super.init();
        servletContext = getServletContext();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get all categories from database with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllCategories("ASC");

        // set categories to the request
        request.setAttribute("categories", categories);

        // forward to new item page
        Helper.forwardRequest(request, response, ViewPath.add_item);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray errors = new JSONArray();
        boolean success = false;

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

        // add error if existed
        errors.addAll(
                //vildate params of FORM 
                vildateParams(name, description, price, countryMade, status, categoryId));

        if (!(errors.size() > 0)) {//if no errors

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

            // add item to DB 
            boolean itemAdded = new ItemDaoImpl(servletContext).addItem(item);

            if (itemAdded) {
                success = true;
                data.put("id", item.getId());
                data.put("name", name);
                data.put("description", description);
                data.put("price", price);
                data.put("date", "" + item.getAddDate());
                data.put("show_item", "" + ControllerPath.SHOW__ITEM);
                data.put("img", ResourcePath.img+"img.png");

            }
        }

        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("data", data);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
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
}
