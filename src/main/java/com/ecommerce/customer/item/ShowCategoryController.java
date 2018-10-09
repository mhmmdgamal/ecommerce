// <editor-fold >
package com.ecommerce.customer.item;

import com.ecommerce.general.item.Item;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
// </editor-fold >

@WebServlet(name = "CategoryItemControllerForCustomer", urlPatterns = {"/show-category"})

public class ShowCategoryController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get pageId param from the request
        String pageId = request.getParameter("pageid");

        // return the pageId if number or return 0
        long id = pageId != null && Helper.isNumber(pageId) ? Long.parseLong(pageId) : 0;

        if (id != 0) {
            // get categoryItems with dessending order
            List<Item> categoryItems = new CategoryDaoImpl(getServletContext()).getCategoryItems(id, "DESC");

            // set categoryItems to request
            request.setAttribute("categoryItems", categoryItems);

            // forword request to show category items page
            Helper.forwardRequest(request, response, ViewPath.show_category);
        } else {
            Helper.redriectToPrevPage(request, response, "You Must Add Page ID", true);
        }
    }

    @Override
    public String getServletInfo() {
        return " this controller show all Item for one category by pageID get from url";
    }

}
