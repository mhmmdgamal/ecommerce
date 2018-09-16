package com.ecommerce.dao;

import com.ecommerce.bean.Comment;
import com.ecommerce.bean.Item;
import com.ecommerce.helper.MySQLDatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class ItemDaoImpl implements ItemDao {

    private final Connection connection;
    private final MySQLDatabaseHelper db;
    private final String table = "items";
    private final ServletContext sc;

    public ItemDaoImpl(ServletContext sc) {
        this.sc = sc;
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
        this.connection = db.getConnection();
    }

    /**
     * approve item depending on id
     *
     * @param id
     * @return true if approved false otherwise
     */
    @Override
    public boolean approveItem(long id) {
        String sql = "UPDATE `items` SET `approve` = 1 WHERE `id` = ?";

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
     * get all comments linked with the item
     *
     * @param id
     * @param sort
     * @return comments
     */
    @Override
    public List<Comment> getItemComments(long id, String sort) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.findAll(new String[]{"*"}, "comments", "`item_id`=" + id, "id", sort, null)) {

            Comment comment;

            while (rs.next()) {
                comment = new Comment();

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
     * update item
     *
     * @param item
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateItem(Item item) {
        return db.update(item, table);
    }

    /**
     * add item
     *
     * @param item
     * @return true if added false otherwise
     */
    @Override
    public boolean addItem(Item item) {
        return db.insert(item, table);
    }

    /**
     * delete item
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteItem(long id) {
        return db.delete(table, id);
    }

    /**
     * get all items data from database
     *
     * @param sort
     * @return found items
     */
    @Override
    public List<Item> getAllItems(String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.findAll(new String[]{"*"}, table, null, "id", sort, null)) {
            while (rs.next()) {
                Item item = new Item();

                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getString("price"));
                item.setAddDate(rs.getDate("add_date"));
                item.setCountryMade(rs.getString("country_made"));
                item.setImage(rs.getString("image"));
                item.setStatus(rs.getString("status"));
                item.setRating(rs.getByte("rating"));
                item.setApprove(rs.getByte("approve"));
                item.setTags(rs.getString("tags"));
                item.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                item.setCategory(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")));
                items.add(item);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * get all items data from database
     *
     * @param sort
     * @return found items
     */
    @Override
    public List<Item> getAllApprovedItems(String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.findAll(new String[]{"*"}, table, " AND `approve`=1", "id", sort, null)) {

            while (rs.next()) {
                Item item = new Item();

                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getString("price"));
                item.setAddDate(rs.getDate("add_date"));
                item.setCountryMade(rs.getString("country_made"));
                item.setImage(rs.getString("image"));
                item.setStatus(rs.getString("status"));
                item.setRating(rs.getByte("rating"));
                item.setApprove(rs.getByte("approve"));
                item.setTags(rs.getString("tags"));
                item.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                item.setCategory(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")));
                items.add(item);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * get latest items data from database depending on number
     *
     * @param num
     * @return latest num items
     */
    @Override
    public List<Item> getLatestItems(int num) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.findLatest(new String[]{"*"}, table, null, "id", "DESC", num + "")) {

            while (rs.next()) {
                Item item = new Item();

                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getString("price"));
                item.setAddDate(rs.getDate("add_date"));
                item.setCountryMade(rs.getString("country_made"));
                item.setImage(rs.getString("image"));
                item.setStatus(rs.getString("status"));
                item.setRating(rs.getByte("rating"));
                item.setApprove(rs.getByte("approve"));
                item.setTags(rs.getString("tags"));
                item.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                item.setCategory(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")));
                items.add(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * get number of items
     *
     * @return items number or 0
     */
    @Override
    public int getNumItems() {
        int count = 0;
        try {
            return db.count(table, null);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }

    /**
     * get specific item with id
     *
     * @param id
     * @return found item
     */
    @Override
    public Item getItemById(long id) {
        Item item = null;

        try (ResultSet rs = db.findOne(new String[]{"*"}, table, "`id`=?", id)) {
            if (rs.next()) {
                item = new Item();
                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getString("price"));
                item.setAddDate(rs.getDate("add_date"));
                item.setCountryMade(rs.getString("country_made"));
                item.setImage(rs.getString("image"));
                item.setStatus(rs.getString("status"));
                item.setRating(rs.getByte("rating"));
                item.setApprove(rs.getByte("approve"));
                item.setTags(rs.getString("tags"));
                item.setUser(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")));
                item.setCategory(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return item;
    }

}
