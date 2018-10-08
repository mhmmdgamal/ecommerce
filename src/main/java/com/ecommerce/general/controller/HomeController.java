// <editor-fold >
package com.ecommerce.general.controller;

import com.ecommerce.general.item.Item;
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

// </editor-fold >
@WebServlet(urlPatterns = {"/home", ""})
public class HomeController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Home");
        // get all items with assending 
        List<Item> items = new ItemDaoImpl(servletContext).getAllApprovedItems("ASC");
        // set items to request
        request.setAttribute("allItems", items);
        // forword request to manage page
        Helper.forwardRequest(request, response, ViewPath.home);
    }
}
