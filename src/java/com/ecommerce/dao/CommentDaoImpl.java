/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.Comment;
import com.ecommerce.helper.MySQLDatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class CommentDaoImpl implements CommentDao {

    private final Connection connection;
    private final MySQLDatabaseHelper db;
    private final String table = "comments";
    private final ServletContext sc;

    public CommentDaoImpl(ServletContext sc) {
        this.sc = sc;
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
        this.connection = db.getConnection();
    }

    /**
     * approve comment depending on id
     * 
     * @param id
     * @return true if approved false otherwise
     */
    @Override
    public boolean approveComment(long id) {
        String sql = "UPDATE `items` SET `status` = 1 WHERE `id` = ?";

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
     * update comment
     * 
     * @param comment 
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateComment(Comment comment) {
        return db.update(comment, table);
    }

    /**
     * add comment
     * 
     * @param comment
     * @return true if added false otherwise
     */
    @Override
    public boolean addComment(Comment comment) {
        return db.insert(comment, table);
    }

    /**
     * delete commnet
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteComment(long id) {
        return db.delete(table, id);
    }

    /**
     * get all comments data from database
     *
     * @param sort
     * @return found comments
     */
    @Override
    public List<Comment> getAllComments(String sort) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.findAll(new String[]{"*"}, table, null, "id", sort, null)) {
            while (rs.next()) {
                Comment comment = new Comment();

                comment.setId(rs.getLong("id"));
                comment.setComment(rs.getString("comment"));
                comment.setAddDate(rs.getDate("add_date"));
                comment.setStatus(rs.getByte("status"));
                comment.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                comment.setItem(new ItemDaoImpl(sc).getItemById(rs.getLong("item_id")));

                comments.add(comment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return comments;
    }

    /**
     * get latest comments data from database depending on number
     *
     * @param num
     * @return latest num comments
     */
    @Override
    public List<Comment> getLatestComments(int num) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.findLatest(new String[]{"*"}, table, null, "id", "DESC", num + "")) {
            while (rs.next()) {
                Comment comment = new Comment();

                comment.setId(rs.getLong("id"));
                comment.setComment(rs.getString("comment"));
                comment.setAddDate(rs.getDate("add_date"));
                comment.setStatus(rs.getByte("status"));
                comment.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                comment.setItem(new ItemDaoImpl(sc).getItemById(rs.getLong("item_id")));

                comments.add(comment);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return comments;
    }

    /**
     * get number of comments
     *
     * @return comments number or 0
     */
    @Override
    public int getNumComments() {
        int count = 0;
        try {
            return db.count(table, null);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }

    /**
     * get specific comment with id
     *
     * @param id
     * @return found comment
     */
    @Override
    public Comment getCommentById(long id) {
        Comment comment = null;

        try (ResultSet rs = db.findOne(new String[]{"*"}, table, "`id`=?", id)) {
            if (rs.next()) {
                comment = new Comment();

                comment.setId(rs.getLong("id"));
                comment.setComment(rs.getString("comment"));
                comment.setAddDate(rs.getDate("add_date"));
                comment.setStatus(rs.getByte("status"));
                comment.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                comment.setItem(new ItemDaoImpl(sc).getItemById(rs.getLong("item_id")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return comment;
    }
}
