/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.Comment;
import com.ecommerce.bean.Item;
import java.util.List;

public interface ItemDao {

    public boolean updateItem(Item item);

    public boolean addItem(Item item);

    public boolean deleteItem(long id);

    public List<Comment> getItemComments(long id, String sort);
    
    public List<Item> getAllItems(String sort);

    public Item getItemById(long id);

    public int getNumItems();

    public List<Item> getLatestItems(int num);
    
    public boolean approveItem(long id);
}
