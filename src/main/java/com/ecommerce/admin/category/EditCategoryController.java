package com.ecommerce.admin.category;

import com.ecommerce.general.category.Category;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.enumiration.ViewParent;
import com.ecommerce.general.enumiration.ViewType;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.helper.PathsHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditCategoryController", urlPatterns = {"/admin/edit-category"})
public class EditCategoryController extends HttpServlet {

    ServletContext servletContext = null;

    @Override
    public void init() throws ServletException {
        servletContext = getServletContext();
    }

    // <editor-fold >
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Edit Category");

        // get categoryId param from the request
        String categoryId = request.getParameter("categoryid");

        // return the categoryId if number or return 0
        long Id = categoryId != null && Helper.isNumber(categoryId) ? Long.parseLong(categoryId) : 0;

        // get category depending on categoryId
        Category categoryFounded = new CategoryDaoImpl(servletContext).getCategoryById(Id);
        if (categoryFounded != null) {
            // set the found category to the request
            request.setAttribute("category", categoryFounded);
            
            //get all super categories
            List<Category> supCategories = new CategoryDaoImpl(servletContext).getAllSupCategories("ASC");

            // set all categories to request
            request.setAttribute("supCategories", supCategories);

            // forword request to edit page
            Helper.forwardRequest(request, response, PathsHelper.getAdminCategory("edit_category"));
        } else {
            // redirect to the previous page with error message
            Helper.redriectToPrevPage(request, response, "There`s No Such ID", true);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set page title
        Helper.setTitle(request, "Edit Category");

        // get form params from the request
        long id = Long.parseLong(request.getParameter("categoryid"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int parent = Integer.parseInt(request.getParameter("parent"));
        int ordering = Integer.parseInt(request.getParameter("ordering"));
        int visibility = Integer.parseInt(request.getParameter("visibility"));
        int commenting = Integer.parseInt(request.getParameter("commenting"));
        int ads = Integer.parseInt(request.getParameter("ads"));

        // make new category and set info to it 
        Category category = Category.builder()
                .id(id)
                .name(name)
                .description(description)
                .parent(parent)
                .ordering(ordering)
                .visibility(visibility)
                .allowComments(commenting)
                .allowAds(ads)
                .build();

        // update category
        boolean categoryUpdated = new CategoryDaoImpl(servletContext).updateCategory(category);

        if (!categoryUpdated) {
            String error = "error in update";
            // set error message to request if category does not update successfully
            request.setAttribute("error", error);
        } else {
            // set success message to request if category updated successfully
            request.setAttribute("success", "category updated");
        }

        // set category to request
        request.setAttribute("category", category);

        // forword request to the edit page
        Helper.forwardRequest(request, response, PathsHelper.getAdminCategory("edit_category"));

    }// </editor-fold>

}
