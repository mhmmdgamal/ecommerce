package com.ecommerce.customer.item;

import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteItemControlller", urlPatterns = {"/delete-item"})
public class DeleteItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get itemId param from the request
        String itemId = request.getParameter("itemid");

        // return the itemId if number or return 0
        long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // delete item depending on the itemId
        boolean itemDeleted = new ItemDaoImpl(getServletContext()).deleteItem(id);

        if (itemDeleted) {
            // set success message if User added
            request.setAttribute("success", "Item Has Been Deleted");
            // forword request to manage page
            Helper.forwardRequest(request, response, ViewPath.show_profile);

        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID! Not Deleted", true);
        }
    }
}
