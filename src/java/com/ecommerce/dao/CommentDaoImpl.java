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
        return CommentDaoImpl.this.getAllCommentsOfItem(0, sort);
    }

    /**
     * get all comments data from database depending on item id
     *
     * @param id
     * @param sort
     * @return found comments
     */
    @Override
    public List<Comment> getAllCommentsOfItem(long id, String sort) {
        List<Comment> comments = new ArrayList();

        String itemId = null;
        if (id != 0) {
            itemId = " `item_id`=" + id + " AND status = 1";
        }

        try (ResultSet rs = db.findAll(new String[]{"*"}, table, itemId, "id", sort, null)) {
            while (rs.next()) {
                Comment comment = new Comment.Builder()
                        .id(rs.getLong("id"))
                        .comment(rs.getString("comment"))
                        .addDate(rs.getDate("add_date"))
                        .status(rs.getByte("status"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .item(new ItemDaoImpl(sc).getItemById(rs.getLong("item_id")))
                        .build();
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
                Comment comment = new Comment.Builder()
                        .id(rs.getLong("id"))
                        .comment(rs.getString("comment"))
                        .addDate(rs.getDate("add_date"))
                        .status(rs.getByte("status"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .item(new ItemDaoImpl(sc).getItemById(rs.getLong("item_id")))
                        .build();

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
                comment = new Comment.Builder()
                        .id(rs.getLong("id"))
                        .comment(rs.getString("comment"))
                        .addDate(rs.getDate("add_date"))
                        .status(rs.getByte("status"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .item(new ItemDaoImpl(sc).getItemById(rs.getLong("item_id")))
                        .build();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return comment;
    }
}
