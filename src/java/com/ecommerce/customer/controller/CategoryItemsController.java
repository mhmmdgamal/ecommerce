/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.customer.controller;

import com.ecommerce.bean.Item;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mohamed
 */
public class CategoryItemsController extends HttpServlet {

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
        // get pageId param from the request
        String pageId = request.getParameter("pageid");

        // return the pageId if number or return 0
        long id = pageId != null && Helper.isNumber(pageId) ? Long.parseLong(pageId) : 0;

        if (id != 0) {
            // get categoryItems with dessending order
            List<Item> categoryItems = new CategoryDaoImpl(getServletContext()).getCategoryItems(id, "DESC");

            // set categoryItems to request
            request.setAttribute("categoryItems", categoryItems);

            // forword request to show category items page page
            Helper.forwardRequest(request, response, getServletContext().getInitParameter("customerJspPath") + "show_category_items.jsp");
        } else {
            Helper.redriectToPrevPage(request, response, "You Must Add Page ID", true);
        }
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
