// <editor-fold>
package com.ecommerce.customer.item;

import com.ecommerce.general.item.Item;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

@WebServlet(name = "showTagControllerForCustomer", urlPatterns = {"/show-tag"})
public class ShowTagController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get tag name parameter
        String tag = request.getParameter("name");
        // set title page
        Helper.setTitle(request, tag);

        // if tag name Not existed
        if (tag == null || tag.isEmpty()) {
            // set error to request
            request.setAttribute("error", "You Must Enter Tag Name");

        } else {//if tag name exists
            // get tag items from database depending on tag name
            List<Item> tagItems = new ItemDaoImpl(getServletContext()).getTagItems(tag, "ASC");

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
        Helper.forwardRequest(request, response, ViewPath.show_tag);
    }
}
