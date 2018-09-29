package com.ecommerce.admin.controller;

import com.ecommerce.bean.Category;
import com.ecommerce.dao.CategoryDaoImpl;
import com.ecommerce.helper.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddCategoryController", urlPatterns = {"/admin/add-category"})
public class AddCategoryController extends HttpServlet {

    String adminJspPath = null;
    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
        adminJspPath = servletContext.getInitParameter("adminJspPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Add Category");

        List<Category> allCategories = new CategoryDaoImpl(servletContext).getAllSupCategories("ASC");

        // set all categories to request
        request.setAttribute("AllCategories", allCategories);

        // forword request to add page
        Helper.forwardRequest(request, response, adminJspPath + "add_category.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Add Category");

        // get form params from the request
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int ordering = Integer.parseInt(request.getParameter("ordering"));
        int parent = Integer.parseInt(request.getParameter("parent"));
        int visibility = Integer.parseInt(request.getParameter("visibility"));
        int commenting = Integer.parseInt(request.getParameter("commenting"));
        int ads = Integer.parseInt(request.getParameter("ads"));

        // make new category and set info to it 
        Category category = Category.builder()
                .name(name)
                .description(description)
                .ordering(ordering)
                .parent(parent)
                .visibility(visibility)
                .allowComments(commenting)
                .allowAds(ads)
                .build();

        // add new category 
        boolean categoryAdded = new CategoryDaoImpl(servletContext).addCategory(category);
        if (!categoryAdded) {
            String error = "Sorry This Category Is Exist";
            // set error message to request if category does not add successfully
            request.setAttribute("error", error);
        } else {
            // set success message to request if category added successfully
            request.setAttribute("success", "category added");
        }

        // forword request to the add page
        Helper.forwardRequest(request, response, adminJspPath + "add_category.jsp");

    }

}
