package com.ecommerce.admin.controller.category;

import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteCategoryController", urlPatterns = {"/admin/delete-category"})
public class DeleteCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Delete Category");

        // get categoryId param from the request
        String categoryId = request.getParameter("categoryid");

        // return the categoryId if number or return 0
        long Id = categoryId != null && Helper.isNumber(categoryId) ? Long.parseLong(categoryId) : 0;

        // delete category depending on the categoryId
        boolean categoryDeleted = new CategoryDaoImpl(getServletContext()).deleteCategory(Id);
       
        if (categoryDeleted) {
            // redirect to the previous page with deleted message
            Helper.redriectToPrevPage(request, response, "category deleted", false);
        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "There`s No Such ID", true);
        }

    }

}
