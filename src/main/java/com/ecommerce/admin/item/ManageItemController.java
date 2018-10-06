// <editor-fold>
package com.ecommerce.admin.item;

import com.ecommerce.general.item.Item;
import com.ecommerce.general.category.Category;
import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
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

    
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        
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
        Helper.forwardRequest(request, response, ViewPath.manage_item_admin);
    }
}
