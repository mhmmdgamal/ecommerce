/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.Category;
import com.ecommerce.bean.Item;
import java.util.List;

public interface CategoryDao {

    public boolean updateCategory(Category category);
    
    public List<Item> getCategoryItems(long id, String sort);

    public boolean addCategory(Category category);

    public boolean deleteCategory(long id);

    public List<Category> getAllCategories(String sort);

    public Category getCategoryById(long id);

    public int getNumCategories();

    public List<Category> getLatestCategories(int num);
}
