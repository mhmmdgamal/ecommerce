/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.helper;

import com.ecommerce.bean.Bean;
import com.ecommerce.bean.Category;
import com.ecommerce.bean.Comment;
import com.ecommerce.bean.Item;
import com.ecommerce.bean.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseHelper {

    private static MySQLDatabaseHelper instance = null;
    private Connection connection = null;

    /**
     * connect to database
     *
     * @param url
     * @param userName
     * @param password
     */
    private MySQLDatabaseHelper(String url, String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * get singleton instance of MySQLDatabaseHelper
     *
     * @param url
     * @param userName
     * @param password
     * @return MySQLDatabaseHelper
     */
    public static MySQLDatabaseHelper getInstance(String url, String userName, String password) {
        if (instance == null) {
            instance = new MySQLDatabaseHelper(url, userName, password);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * close connection
     *
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean delete(String table, long id) {
        String query = "DELETE FROM `" + table + "` WHERE `id`=?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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

    public String prepareFindQuery(String[] columns, String table, String where, String orderBy, String sort, String limit) throws SQLException {
        String query = "SELECT ";

        if (columns != null) {
            if (columns.length == 1) {
                String column = columns[0].trim();
                if (column.equals("*")) {
                    query += column;
                } else {
                    query += " `" + column + "` ";
                }
            } else {
                for (int i = 0; i < columns.length; i++) {
                    query += " `" + columns[i] + "`, ";
                }
                query = Helper.rTrim(query, ", ");
            }
        }

        if (table != null) {
            query += " From `" + table + "` ";
        }

        if (where != null) {
            query += " WHERE " + where;
        }

        if (orderBy != null) {
            query += " ORDER BY `" + orderBy + "` ";
        }

        if (sort != null) {
            if (sort.equalsIgnoreCase("ASC") || sort.equalsIgnoreCase("DESC")) {
                query += sort.toUpperCase();
            } else {
                query += "ASC";
            }
        }

        if (limit != null) {
            query += " LIMIT " + limit;
        }

        return query;
    }

    public ResultSet findAll(String[] columns, String table, String where, String orderBy, String sort, String limit) throws SQLException {

        String query = prepareFindQuery(columns, table, where, orderBy, sort, limit);
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    public ResultSet findOne(String[] columns, String table, String where, long id) throws SQLException {
        String query = prepareFindQuery(columns, table, where, null, null, null);

        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setLong(1, id);

        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public int count(String table, String where) throws SQLException {
        String query = "SELECT COUNT(id) AS count FROM `" + table + "`";
        if (where != null) {
            query += " WHERE " + where;
        }

        int count = 0;
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    public ResultSet findLatest(String[] columns, String table, String where, String orderBy, String sort, String limit) throws SQLException {
        String query = prepareFindQuery(columns, table, where, orderBy, sort, limit);

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public boolean insert(Bean bean, String table) {
        String query = "INSERT INTO `" + table + "` SET ";
        String userQuery = "`name`=?, `password`=?, `email`=?, `full_name`=?, `Date`=now()";
        String categoryQuery = "`name`=?, `description`=?, `ordering`=?, `visibility`=?, `allow_comments`=?, `allow_ads`=?";
        String commentQuery = "`comment`=?,`status`=?,`add_date`=now(),`item_id`=?,`user_id`=?";
        String itemQuery = "`name`=?,`description`=?,`price`=?,`add_date`=now(),`country_made`=?,`image`=?,`status`=?,`rating`=?,`approve`=?,`category_id`=?,`user_id`=?,`tags`=?";

        PreparedStatement pstmt = null;
        try {
            if (bean instanceof User) {
                User user = (User) bean;
                query += userQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getFullName());
            } else if (bean instanceof Category) {
                Category category = (Category) bean;
                query += categoryQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, category.getName());
                pstmt.setString(2, category.getDescription());
                pstmt.setInt(3, category.getOrdering());
                pstmt.setInt(4, category.getVisibility());
                pstmt.setInt(5, category.getAllowComments());
                pstmt.setInt(6, category.getAllowAds());
            } else if (bean instanceof Comment) {
                Comment comment = (Comment) bean;
                query += commentQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, comment.getComment());
                pstmt.setByte(2, comment.getStatus());
                pstmt.setLong(3, comment.getItem().getId());
                pstmt.setLong(4, comment.getUser().getId());
            } else if (bean instanceof Item) {
                Item item = (Item) bean;
                query += itemQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, item.getName());
                pstmt.setString(2, item.getDescription());
                pstmt.setString(3, item.getPrice());
                pstmt.setString(4, item.getCountryMade());
                pstmt.setString(5, item.getImage());
                pstmt.setString(6, item.getStatus());
                pstmt.setShort(7, item.getRating());
                pstmt.setShort(8, item.getApprove());
                pstmt.setLong(9, item.getCategory().getId());
                pstmt.setLong(10, item.getUser().getId());
                pstmt.setString(11, item.getTags());
            }
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(Bean bean, String table) {
        String query = "UPDATE `" + table + "` SET ";
        String userQuery = "`name`=?, `password`=?, `email`=?, `full_name`=? WHERE `id`=?";
        String categoryQuery = "`name`=?, `description`=?, `ordering`=?, `visibility`=?, `allow_comments`=?, `allow_ads`=? WHERE `id`=?";
        String commentQuery = "`comment`=? WHERE `id`=?";
        String itemQuery = "`name`=?,`description`=?,`price`=?,`country_made`=?,`image`=?,`status`=?,`rating`=?,`category_id`=?,`user_id`=?,`tags`=? WHERE `id`=?";

        PreparedStatement pstmt = null;
        try {
            if (bean instanceof User) {
                User user = (User) bean;
                query += userQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getFullName());
                pstmt.setLong(5, user.getId());
            } else if (bean instanceof Category) {
                Category category = (Category) bean;
                query += categoryQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, category.getName());
                pstmt.setString(2, category.getDescription());
                pstmt.setInt(3, category.getOrdering());
                pstmt.setInt(4, category.getVisibility());
                pstmt.setInt(5, category.getAllowComments());
                pstmt.setInt(6, category.getAllowAds());
                pstmt.setLong(7, category.getId());
            } else if (bean instanceof Comment) {
                Comment comment = (Comment) bean;
                query += commentQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, comment.getComment());
                pstmt.setLong(2, comment.getId());;
            } else if (bean instanceof Item) {
                Item item = (Item) bean;
                query += itemQuery;

                pstmt = connection.prepareStatement(query);

                pstmt.setString(1, item.getName());
                pstmt.setString(2, item.getDescription());
                pstmt.setString(3, item.getPrice());
                pstmt.setString(4, item.getCountryMade());
                pstmt.setString(5, item.getImage());
                pstmt.setString(6, item.getStatus());
                pstmt.setShort(7, item.getRating());
                pstmt.setLong(8, item.getCategory().getId());
                pstmt.setLong(9, item.getUser().getId());
                pstmt.setString(10, item.getTags());
                pstmt.setLong(11, item.getId());
            }
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
}
