package com.ecommerce.general.controller;

import com.ecommerce.general.comment.CommentDaoImpl;
import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.path.ViewPath;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/admin/dashboard")
public class DashboardController extends HttpServlet {

    
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Dashboard");

        // get user dao to do operation on user
        UserDaoImpl userDao = new UserDaoImpl(servletContext);

        // get item dao to do operation on item
        ItemDaoImpl itemDao = new ItemDaoImpl(servletContext);

        // get comment dao to do operation on comment
        CommentDaoImpl commentDao = new CommentDaoImpl(servletContext);

        // set the number of users to the request
        request.setAttribute("numUsers", userDao.getNumUsers());

        // set the number of pending users to the request
        request.setAttribute("numPendingUsers", userDao.getNumPendingUsers());

        // set the latest five users to the request
        request.setAttribute("latestUsers", userDao.getLatestUsers(5));

        // set the number of items to the request
        request.setAttribute("numItems", itemDao.getNumItems());

        // set the latest five items to the request
        request.setAttribute("latestItems", itemDao.getLatestItems(5));

        // set the number of comments to the request
        request.setAttribute("numComments", commentDao.getNumComments());

        // set the latest five comments to the request
        request.setAttribute("latestComments", commentDao.getLatestComments(5));

        // forword the requset to the dashboard page
        Helper.forwardRequest(request, response, ViewPath.dashboard_admin);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
