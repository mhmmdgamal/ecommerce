package com.ecommerce.general.comment;

import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.user.UserDaoImpl;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.helper.MySQLDatabaseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class CommentDaoImpl implements CommentDao {

    private final MySQLDatabaseHelper db;
    private final String table = "comments";
    private final ServletContext sc;

    public CommentDaoImpl(ServletContext sc) {
        this.sc = sc;
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
    }

    /**
     * approve comment depending on id
     *
     * @param id
     * @return true if approved false otherwise
     */
    @Override
    public boolean approveComment(long id) {

        boolean approved = false;
        try {
            approved = db.table(table)
                    .data("status", 1)
                    .where("`id`=?", id)
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return approved;
    }

    /**
     * update comment
     *
     * @param comment
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateComment(Comment comment) {
        boolean updated = false;
        try {
            updated = db.table(table)
                    .data("comment", comment.getComment())
                    .where("`id`=?", comment.getId())
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * add comment
     *
     * @param comment
     * @return true if added false otherwise
     */
    @Override
    public boolean addComment(Comment comment) {
        boolean inserted = false;
        try {
            inserted = db.table(table)
                    .data("comment", comment.getComment())
                    .data("status", comment.getStatus())
                    .data("add_date", Helper.getCurrentDate())
                    .data("item_id", comment.getItem().getId())
                    .data("user_id", comment.getUser().getId())
                    .insert();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return inserted;
    }

    /**
     * delete commnet
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteComment(long id) {
        boolean deleted = false;
        try {
            deleted = db.table(table)
                    .where("`id`=?", id)
                    .delete();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }

    /**
     * get all comments data table database
     *
     * @param sort
     * @return found comments
     */
    @Override
    public List<Comment> getAllComments(String sort) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Comment comment = Comment.builder()
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
     * get all comments data table database depending on item id
     *
     * @param id
     * @param sort
     * @return found comments
     */
    @Override
    public List<Comment> getItemComments(long id, String sort) {
        List<Comment> comments = new ArrayList();

        String where = "";
        if (id != 0) {
            where = " `item_id`=" + id + " AND status = 1";
        }

        try (ResultSet rs = db.select()
                .table(table)
                .where(where)
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Comment comment = Comment.builder()
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
     * <improve>improve this function to use join</improve>
     * get latest comments data table database depending on number
     *
     * @param num
     * @return latest num comments
     */
    @Override
    public List<Comment> getLatestComments(int num) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .orderBy("id")
                .sort("DESC")
                .limit(num)
                .fetchData()) {

            while (rs.next()) {
                Comment comment = Comment.builder()
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
            ResultSet rs = db.select("COUNT(id)")
                    .table(table)
                    .fetchData();

            if (rs.next()) {
                return rs.getInt("COUNT(id)");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }

    /**
     * <improve>improve this function to use join</improve>
     * get specific comment with id
     *
     * @param id
     * @return found comment
     */
    @Override
    public Comment getCommentById(long id) {
        Comment comment = null;

        try (ResultSet rs = db.select()
                .table(table)
                .where("`id`=?", id)
                .fetchData()) {

            if (rs.next()) {
                comment = Comment.builder()
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
