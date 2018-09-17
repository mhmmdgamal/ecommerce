/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.Comment;
import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import java.util.List;

public interface UserDao {

    public boolean updateUser(User user);

    public boolean addUser(User user);

    public boolean deleteUser(long id);

    public boolean activeUser(long id);

    public List<User> getAllUsers(boolean pendings);

    public List<Item> getUserItems(long id, String sort);
    
    public List<Comment> getUserComments(Long id, String sort);
    
    public User getUserById(long id);

    public User getLoginUser(String userName, String password, boolean admin);

    public int getNumUsers();

    public int getNumPendingUsers();

    public List<User> getLatestUsers(int num);
}
