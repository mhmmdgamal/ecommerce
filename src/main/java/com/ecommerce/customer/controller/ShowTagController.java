// <editor-fold>
package com.ecommerce.customer.controller;

import com.ecommerce.bean.Item;
import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

@WebServlet("/tags")
public class ShowTagController extends HttpServlet {

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

        // get tag name param
        String tag = request.getParameter("name");

        // set title page
        Helper.setTitle(request, tag);

        // check if tag name exists
        if (tag == null || tag.isEmpty()) {

            String error = "You Must Enter Tag Name";

            // set error to request
            request.setAttribute("error", error);
        } else {
            // get tag items from database depending on tag name
            List<Item> tagItems = new ItemDaoImpl(servletContext).getTagItems(tag, "ASC");

            if (tagItems.size() > 0) {
                // set tag items to request
                request.setAttribute("tagItems", tagItems);

                // set tag to request
                request.setAttribute("tag", tag);

            } else {
                String error = "There's no item with tag: " + tag;

                // set error to request
                request.setAttribute("error", error);
            }
        }

        Helper.forwardRequest(request, response, customerJspPath
                + "show_items_of_tag.jsp");
    }
}
