/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.Category;
import com.ecommerce.helper.MySQLDatabaseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class CategoryDaoImpl implements CategoryDao {

    private final MySQLDatabaseHelper db;
    private final String table = "categories";

    public CategoryDaoImpl(ServletContext sc) {
        // get database instance from app context
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
    }

    /**
     * update category
     * 
     * @param category
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateCategory(Category category) {
        return db.update(category, table);
    }

    /**
     * add category
     * 
     * @param category
     * @return true if added false otherwise
     */
    @Override
    public boolean addCategory(Category category) {
        return db.insert(category, table);
    }

    /**
     * delete category
     * 
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteCategory(long id) {
        return db.delete(table, id);
    }

    /**
     * get all categories data from database
     * 
     * @param sort
     * @return found categories
     */
    @Override
    public List<Category> getAllCategories(String sort) {
        List<Category> categories = new ArrayList();

        try (ResultSet rs = db.findAll(new String[]{"*"}, table, null, "ordering", sort, null)) {
            while (rs.next()) {
                Category category = new Category();

                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setOrdering(rs.getInt("ordering"));
                category.setVisibility(rs.getInt("visibility"));
                category.setAllowComments(rs.getInt("allow_comments"));
                category.setAllowAds(rs.getInt("allow_ads"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * get latest categories data from database depending on number
     * 
     * @param num
     * @return latest num categories
     */
    @Override
    public List<Category> getLatestCategories(int num) {
        List<Category> categories = new ArrayList();

        try (ResultSet rs = db.findLatest(new String[]{"*"}, table, null,"id", "DESC", num + "")) {
            while (rs.next()) {
                Category category = new Category();

                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setOrdering(rs.getInt("ordering"));
                category.setVisibility(rs.getInt("visibility"));
                category.setAllowComments(rs.getInt("allow_comments"));
                category.setAllowAds(rs.getInt("allow_ads"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * get number of categories
     * 
     * @return categories number or 0
     */
    @Override
    public int getNumCategories() {
        int count = 0;
        try {
            return db.count(table, null);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }

    /**
     * get specific category with id
     * 
     * @param id
     * @return found category
     */
    @Override
    public Category getCategoryById(long id) {
        Category category = null;

        try (ResultSet rs = db.findOne(new String[]{"*"}, table, "`id`=?", id)) {
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setOrdering(rs.getInt("ordering"));
                category.setVisibility(rs.getInt("visibility"));
                category.setAllowComments(rs.getInt("allow_comments"));
                category.setAllowAds(rs.getInt("allow_ads"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return category;
    }
}
