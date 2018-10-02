// <editor-fold>
package com.ecommerce.admin.controller.item;

import com.ecommerce.bean.Item;
import com.ecommerce.bean.Category;
import com.ecommerce.bean.Comment;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.dao.CommentDaoImpl;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
// </editor-fold>

@WebServlet("/admin/manage-items")
public class ManageItemController extends HttpServlet {

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
        Helper.setTitle(request, "Manage Items");

        // get all categories with assending order
        List<Category> categories = new CategoryDaoImpl(servletContext).getAllSupCategories("ASC");
        // get all comments with assending order
        List<Comment> comments = new CommentDaoImpl(servletContext).getAllComments("ASC");
        // get all items with assending order
        List<Item> items = new ItemDaoImpl(servletContext).getAllItems("ASC");

        // set items to request
        request.setAttribute("items", items);
        // set categories to request
        request.setAttribute("categories", categories);
        // set comments to request
        request.setAttribute("comments", comments);

        // forword request to manage page
        Helper.forwardRequest(request, response, adminJspPath + "item_views/manage_items.jsp");
    }
}
