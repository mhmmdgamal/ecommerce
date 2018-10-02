package com.ecommerce.admin.controller.item;

import com.ecommerce.dao.ItemDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ApproveItemController", urlPatterns = {"/admin/approve-item"})
public class ApproveItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get itemId param from the request
        String itemId = request.getParameter("itemid");

        // return the itemIdif number or return 0
        long Id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // approve item depending on the itemId
        boolean itemActivated = new ItemDaoImpl(getServletContext()).approveItem(Id);

        if (itemActivated) {
            // redirect to the previous page with deleted message
            Helper.redriectToPrevPage(request, response, "item approved", false);
        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

}
