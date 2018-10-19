package com.ecommerce.admin.item;

import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.item.ItemDaoImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

@WebServlet(name = "DeleteItemController", urlPatterns = {"/admin/delete-item"})
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

        JSONObject obj = new JSONObject();
        obj.put("success", itemDeleted);
        response.setContentType("application/json");
        response.getWriter().print(obj.toJSONString());
    }

}
