// <editor-fold>
package com.ecommerce.customer.item;

import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.helper.CookieHelper;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

@WebServlet(name = "ShowItemControllerForCustomer", urlPatterns = {"/show-item"})
public class ShowItemController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    /**
     * doGet method created to (show one item) about way :
     * <1> receive parameter id of item
     * <2> get item from DB
     * <3> get id of user(owner of item) from session or cookies
     * <4>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Items");

        // get itemId param from the request
        String itemId = request.getParameter("itemid");

        // return the itemId if number or return 0
        long id = itemId != null && Helper.isNumber(itemId) ? Long.parseLong(itemId) : 0;

        // get item
        Item itemFounded = new ItemDaoImpl(servletContext).getItemById(id);

        if (itemFounded != null) {
            // check if item is approved
            if (itemFounded.getApprove() == 1) {
                // set item to request
                request.setAttribute("item", itemFounded);
            } else {
                Long userId = getCurrentUserId(request, response);

                // check if user owner of this item                
                if (itemFounded.getUser().getId() == userId) {
                    // set item to request
                    request.setAttribute("item", itemFounded);
                }
            }
            // get all comments with descinding order links with item id
            List<Comment> itemComments = new CommentDaoImpl(servletContext).getItemComments(id, "DESC");

            // set comments to request
            request.setAttribute("itemComments", itemComments);

            // forword request to manage page
            Helper.forwardRequest(request, response, ViewPath.show_item);
        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "Theres No Such ID", true);
        }
    }

    public long getCurrentUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long userId;
        if (CookieHelper.isCookie("userId", request, response)) {
            // get user id from cookie
            userId = Long.parseLong(CookieHelper.getCookie("userId", request, response));
        } else {
            // get userId from session
            userId = (Long) request.getSession().getAttribute("userId");
        }
        return userId;
    }
}
