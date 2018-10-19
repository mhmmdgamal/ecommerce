package com.ecommerce.admin.item;

import com.ecommerce.general.category.Category;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.path.ViewPath;
import com.ecommerce.general.user.User;
import com.ecommerce.general.user.UserDaoImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray errors = new JSONArray();
        String success = null;

        // get form params from the request
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String countryMade = request.getParameter("country");
        String status = request.getParameter("status");
        int userId = Integer.parseInt(request.getParameter("user"));
        int categoryId = Integer.parseInt(request.getParameter("category"));
        String tags = request.getParameter("tags");

        //vildate parameter of FORM 
        errors.addAll(vildateFormParams(name, description, price, countryMade, status, userId, categoryId));

        System.out.println(errors);

        if (!(errors.size() > 0)) {
            // make new user and set info to it
            User user = new UserDaoImpl(servletContext).getUserById(userId);

            // make new category and set info to it
            Category category = new CategoryDaoImpl(servletContext).getCategoryById(categoryId);

            // make new item and set info to it 
            Item item = Item.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .countryMade(countryMade)
                    .status(status)
                    .user(user)
                    .addDate(Helper.getCurrentDate())
                    .category(category)
                    .tags(tags)
                    .build();

            ItemDaoImpl itemDao = new ItemDaoImpl(servletContext);

            // add item 
            boolean itemAdded = itemDao.addItem(item);

            if (!itemAdded) {
                // add new error to errors if item not added
                errors.add("Sorry This Item Is Exist");
            } else {
                // set success message if item added
                success = "item added";
                data.put("id", itemDao.getLastItemId());
                data.put("name", name);
                data.put("description", description);
                data.put("price", price);
                data.put("countryMade", countryMade);
                data.put("status", status);
                data.put("userId", userId);
                data.put("username", user.getName());
                data.put("categoryId", categoryId);
                data.put("categoryName", category.getName());
                data.put("tags", tags);
                data.put("date", "" + item.getAddDate());
            }

        }
        obj.put("success", success);
        obj.put("errors", errors);
        obj.put("data", data);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }// </editor-fold>

    public JSONArray vildateFormParams(String name, String description, String price,
            String countryMade, String status, int userId, int categoryId) {

        // make empty list to errors
        JSONArray errors = new JSONArray();

        // validate the form params
        if (StringUtils.isEmpty(name)) {
            errors.add("Name Can't be <strong>Empty</strong>");
        }

        if (StringUtils.isEmpty(description)) {
            errors.add("Description Can't be <strong>Empty</strong>");
        }

        if (StringUtils.isEmpty(price)) {
            errors.add("Price Can't Be <strong>Empty</strong>");
        }

        if (StringUtils.isEmpty(countryMade)) {
            errors.add("Country Can't Be <strong>Empty</strong>");
        }

        if (status.equals("0")) {
            errors.add("You Must Choose the <strong>Status</strong>");
        }

        if (userId == 0) {
            errors.add("You Must Choose the <strong>User</strong>");
        }

        if (categoryId == 0) {
            errors.add("You Must Choose the <strong>Category</strong>");
        }
        return errors;
    }
}
