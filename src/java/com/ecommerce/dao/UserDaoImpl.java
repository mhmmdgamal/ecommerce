/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.User;
import com.ecommerce.helper.MySQLDatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class UserDaoImpl implements UserDao {

    private final Connection connection;
    private final MySQLDatabaseHelper db;
    private final String table = "users";

    public UserDaoImpl(ServletContext sc) {
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
        this.connection = db.getConnection();
    }

    /**
     * get login user 
     * 
     * @param userName
     * @param password
     * @return found user
     */
    @Override
    public User getLoginUser(String userName, String password) {

        String sql = "SELECT `id`, `name`, `password`, `full_name` FROM `users` WHERE `name` = ? AND `password` = ? AND `group_id` = 1 LIMIT 1";
        User user = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    /**
     * get number of pending users
     *
     * @return users number or 0
     */
    @Override
    public int getNumPendingUsers() {
        int count = 0;
        try {
            return db.count(table, "`register_status`=0");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return count;
    }

     /**
     * active user depending on id
     *
     * @param id
     * @return true if activated false otherwise
     */
    @Override
    public boolean activeUser(long id) {

        String sql = "UPDATE `users` SET `register_status` = 1 WHERE `id` = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     * update user
     *
     * @param user
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateUser(User user) {
        return db.update(user, table);
    }

     /**
     * add user
     *
     * @param user
     * @return true if added false otherwise
     */
    @Override
    public boolean addUser(User user) {
        return db.insert(user, table);
    }

    /**
     * delete user
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteUser(long id) {
        return db.delete(table, id);
    }

    /**
     * get all users data from database
     *
     * @param pendings 
     * @return found users
     */
    @Override
    public List<User> getAllUsers(boolean pendings) {
        List<User> users = new ArrayList();

        String where = "`group_id`!=1";

        if (pendings) {
            where = "`group_id`!=1 AND `register_status`=0";
        }

        try (ResultSet rs = db.findAll(new String[]{"*"}, table, where, "id", "DESC", null)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setRegStatus(rs.getInt("register_status"));
                user.setDate(rs.getDate("date"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return users;
    }

    /**
     * get latest users data from database depending on number
     *
     * @param num
     * @return latest num users
     */
    @Override
    public List<User> getLatestUsers(int num) {
        List<User> users = new ArrayList();

        try (ResultSet rs = db.findLatest(new String[]{"*"}, table, "`group_id`!=1", "id", "DESC", num + "")) {
            while (rs.next()) {
                User user = new User();

                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setRegStatus(rs.getInt("register_status"));
                user.setDate(rs.getDate("date"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return users;
    }

     /**
     * get number of users
     *
     * @return users number or 0
     */
    @Override
    public int getNumUsers() {
        int count = 0;
        try {
            return db.count(table, null);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }

    /**
     * get specific user with id
     *
     * @param id
     * @return found user
     */
    @Override
    public User getUserById(long id) {
        User user = null;

        try (ResultSet rs = db.findOne(new String[]{"*"}, table, "`id`=?", id)) {
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return user;
    }
}
