package com.ecommerce.general.item;

import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.helper.MySQLDatabaseHelper;
import com.ecommerce.general.user.UserDaoImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class ItemDaoImpl implements ItemDao {

    private final MySQLDatabaseHelper db;
    private final String table = "items";
    private final ServletContext sc;

    public ItemDaoImpl(ServletContext sc) {
        this.sc = sc;
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
    }

    /**
     * approve item depending on id
     *
     * @param id
     * @return true if approved false otherwise
     */
    @Override
    public boolean approveItem(long id) {
        boolean approved = false;
        try {
            approved = db.table(table)
                    .data("approve", 1)
                    .where("`id`=?", id)
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return approved;
    }

    /**
     * get last item id
     *
     * @return int
     */
    @Override
    public int getLastItemId() {
        try {
            return db.getLastId();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return 0;
    }
    
    /**
     * get all items linked with the user
     *
     * @param tag
     * @param sort
     * @return items
     */
    @Override
    public List<Item> getTagItems(String tag, String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .where("`tags` LIKE '%" + tag + "%'")
                .where(" AND `approve`=1")
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Item item = Item.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getString("price"))
                        .addDate(rs.getDate("add_date"))
                        .countryMade(rs.getString("country_made"))
                        .image(rs.getString("image"))
                        .status(rs.getString("status"))
                        .rating(rs.getByte("rating"))
                        .approve(rs.getByte("approve"))
                        .tags(rs.getString("tags"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .category(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")))
                        .build();

                items.add(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * <improve>improve this function to use join</improve>
     * get all comments linked with the item
     *
     * @param id
     * @param sort
     * @return comments
     */
    @Override
    public List<Comment> getItemComments(long id, String sort) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .where("`item_id`=?", id)
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            Comment comment;

            while (rs.next()) {
                comment = Comment.builder()
                        .id(rs.getLong("id"))
                        .comment(rs.getString("comment"))
                        .addDate(rs.getDate("add_date"))
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
     * update item
     *
     * @param item
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateItem(Item item) {
        boolean updated = false;
        try {
            updated = db.table(table)
                    .data("name", item.getName())
                    .data("description", item.getDescription())
                    .data("price", item.getPrice())
                    .data("country_made", item.getCountryMade())
//                    .data("image", item.getImage())
                    .data("status", item.getStatus())
                    .data("rating", item.getRating())
                    .data("category_id", item.getCategory().getId())
                    .data("user_id", item.getUser().getId())
                    .data("tags", item.getTags())
                    .where("`id`=?", item.getId())
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * add item
     *
     * @param item
     * @return true if added false otherwise
     */
    @Override
    public boolean addItem(Item item) {
        boolean inserted = false;
        try {
            inserted = db.table(table)
                    .data("name", item.getName())
                    .data("description", item.getDescription())
                    .data("price", item.getPrice())
                    .data("country_made", item.getCountryMade())
//                    .data("image", item.getImage())
                    .data("status", item.getStatus())
                    .data("rating", item.getRating())
                    .data("category_id", item.getCategory().getId())
                    .data("user_id", item.getUser().getId())
                    .data("approve", item.getApprove())
                    .data("add_date", Helper.getCurrentDate())
                    .data("tags", item.getTags())
                    .insert();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return inserted;
    }

    /**
     * delete item
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteItem(long id) {
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
     * <improve>improve this function to use join</improve>
     * get all items data table database
     *
     * @param sort
     * @return found items
     */
    @Override
    public List<Item> getAllItems(String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Item item = Item.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getString("price"))
                        .addDate(rs.getDate("add_date"))
                        .countryMade(rs.getString("country_made"))
                        .image(rs.getString("image"))
                        .status(rs.getString("status"))
                        .rating(rs.getByte("rating"))
                        .approve(rs.getByte("approve"))
                        .tags(rs.getString("tags"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .category(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")))
                        .build();

                items.add(item);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * <improve>improve this function to use join</improve>
     * get all items data table database
     *
     * @param sort
     * @return found items
     */
    @Override
    public List<Item> getAllApprovedItems(String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .where("`approve`=1")
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Item item = Item.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getString("price"))
                        .addDate(rs.getDate("add_date"))
                        .countryMade(rs.getString("country_made"))
                        .image(rs.getString("image"))
                        .status(rs.getString("status"))
                        .rating(rs.getByte("rating"))
                        .approve(rs.getByte("approve"))
                        .tags(rs.getString("tags"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .category(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")))
                        .build();

                items.add(item);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * <improve>improve this function to use join</improve>
     * get latest items data table database depending on number
     *
     * @param num
     * @return latest num items
     */
    @Override
    public List<Item> getLatestItems(int num) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .orderBy("id")
                .sort("DESC")
                .limit(num)
                .fetchData()) {

            while (rs.next()) {
                Item item = Item.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getString("price"))
                        .addDate(rs.getDate("add_date"))
                        .countryMade(rs.getString("country_made"))
                        .image(rs.getString("image"))
                        .status(rs.getString("status"))
                        .rating(rs.getByte("rating"))
                        .approve(rs.getByte("approve"))
                        .tags(rs.getString("tags"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .category(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")))
                        .build();

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
     * get specific item with id
     *
     * @param id
     * @return found item
     */
    @Override
    public Item getItemById(long id) {
        Item item = null;

        try (ResultSet rs = db.select()
                .table(table)
                .where("`id`=?", id)
                .fetchData()) {

            if (rs.next()) {
                item = Item.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getString("price"))
                        .addDate(rs.getDate("add_date"))
                        .countryMade(rs.getString("country_made"))
                        .image(rs.getString("image"))
                        .status(rs.getString("status"))
                        .rating(rs.getByte("rating"))
                        .approve(rs.getByte("approve"))
                        .tags(rs.getString("tags"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .category(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")))
                        .build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return item;
    }

    /**
     * <improve>improve this function to use join</improve>
     * get specific item with id and be approved
     *
     * @param id
     * @return found item
     */
    @Override
    public Item getApprovedItemById(long id) {
        Item item = null;

        try (ResultSet rs = db.select()
                .table(table)
                .where("`id`=?", id)
                .where(" AND `approve`=1")
                .fetchData()) {

            if (rs.next()) {
                item = Item.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getString("price"))
                        .addDate(rs.getDate("add_date"))
                        .countryMade(rs.getString("country_made"))
                        .image(rs.getString("image"))
                        .status(rs.getString("status"))
                        .rating(rs.getByte("rating"))
                        .approve(rs.getByte("approve"))
                        .tags(rs.getString("tags"))
                        .user(new UserDaoImpl(sc).getUserById(rs.getLong("user_id")))
                        .category(new CategoryDaoImpl(sc).getCategoryById(rs.getLong("category_id")))
                        .build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return item;
    }

}
