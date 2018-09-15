// <editor-fold >
package com.ecommerce.customer.controller;

import com.ecommerce.bean.Item;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author mohamed
 */
// </editor-fold >
public class ItemOfCategoryController extends HttpServlet {
    // <editor-fold >
    ServletContext servletContext;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get pageId param from the request
        String pageId = request.getParameter("pageid");

        // return the pageId if number or return 0
        long id = pageId != null && Helper.isNumber(pageId) ? Long.parseLong(pageId) : 0;

        if (id != 0) {
            // get categoryItems with dessending order
            List<Item> categoryItems = new CategoryDaoImpl(servletContext).getCategoryItems(id, "DESC");

            // set categoryItems to request
            request.setAttribute("categoryItems", categoryItems);

            // forword request to show category items page
            Helper.forwardRequest(request, response, servletContext.getInitParameter("customerJspPath") + "show_item_of_category.jsp");
        } else {
            Helper.redriectToPrevPage(request, response, "You Must Add Page ID", true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
