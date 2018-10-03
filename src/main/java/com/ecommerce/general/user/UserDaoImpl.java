package com.ecommerce.general.user;

import com.ecommerce.general.item.ItemDaoImpl;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.comment.Comment;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.helper.Helper;
import com.ecommerce.general.helper.MySQLDatabaseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class UserDaoImpl implements UserDao {

    private final MySQLDatabaseHelper db;
    private final String table = "users";
    private final ServletContext sc;

    public UserDaoImpl(ServletContext sc) {
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");
        this.sc = sc;
    }

    /**
     * get login user
     *
     * @param userName
     * @param password
     * @param admin
     * @return found user
     */
    @Override
    public User getLoginUser(String userName, String password) {

        String where = "`name` = ? AND `password` = ?";
       
        User user = null;
        try (ResultSet rs = db.select("id", "name", "password", "full_name", "group_id")
                .table(table)
                .where(where, userName, password)
                .fetchData()) {

            if (rs.next()) {
                user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .fullName(rs.getString("full_name"))
                        .groupId(rs.getInt("group_id"))
                        .build();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    /**
     * get all items linked with the user
     *
     * @param id
     * @param sort
     * @return items
     */
    @Override
    public List<Item> getUserItems(long id, String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.select()
                .table("items")
                .where("`user_id`=?", id)
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
     * get number of pending users
     *
     * @return users number or 0
     */
    @Override
    public int getNumPendingUsers() {
        int count = 0;
        try {
            ResultSet rs
                    = db.select("COUNT(id)")
                            .table(table)
                            .where("`register_status`=0")
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
     * active user depending on id
     *
     * @param id
     * @return true if activated false otherwise
     */
    @Override
    public boolean activeUser(long id) {

        boolean actived = false;
        try {
            actived = db.table(table)
                    .data("register_status", 1)
                    .where("`id`=?", id)
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return actived;
    }

    /**
     * update user
     *
     * @param user
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateUser(User user) {
        boolean updated = false;
        try {
            updated = db.table(table)
                    .data("name", user.getName())
                    .data("password", user.getPassword())
                    .data("email", user.getEmail())
                    .data("full_name", user.getFullName())
                    .where("`id`=?", user.getId())
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * add user
     *
     * @param user
     * @return true if added false otherwise
     */
    @Override
    public boolean addUser(User user) {
        boolean inserted = false;
        try {
            inserted = db.table(table)
                    .data("name", user.getName())
                    .data("password", user.getPassword())
                    .data("email", user.getEmail())
                    .data("full_name", user.getFullName())
                    .data("date", Helper.getCurrentDate())
                    .insert();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return inserted;
    }

    /**
     * delete user
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteUser(long id) {
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
     * get all users data table database
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

        try (ResultSet rs = db.select()
                .table(table)
                .where(where)
                .orderBy("id")
                .sort("DESC")
                .fetchData()) {

            while (rs.next()) {
                User user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .fullName(rs.getString("full_name"))
                        .regStatus(rs.getInt("register_status"))
                        .date(rs.getDate("date"))
                        .build();

                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return users;
    }

    /**
     * get latest users data table database depending on number
     *
     * @param num
     * @return latest num users
     */
    @Override
    public List<User> getLatestUsers(int num) {
        List<User> users = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .where("`group_id`!=1")
                .orderBy("id")
                .sort("DESC")
                .limit(num)
                .fetchData()) {

            while (rs.next()) {
                User user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .fullName(rs.getString("full_name"))
                        .regStatus(rs.getInt("register_status"))
                        .date(rs.getDate("date"))
                        .build();

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
     * get specific user with id
     *
     * @param id
     * @return found user
     */
    @Override
    public User getUserById(long id) {
        User user = null;

        try (ResultSet rs = db.select()
                .table(table)
                .where("`id`=?", id)
                .fetchData()) {

            if (rs.next()) {
                user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .fullName(rs.getString("full_name"))
                        .email(rs.getString("email"))
                        .date(rs.getDate("date"))
                        .build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return user;
    }

    /**
     * get all comments linked with the user
     *
     * @param id
     * @param sort
     * @return comments
     */
    @Override
    public List<Comment> getUserComments(Long id, String sort) {
        List<Comment> comments = new ArrayList();

        try (ResultSet rs = db.select()
                .table("comments")
                .where("`user_id`=?", id)
                .orderBy("id")
                .sort(sort)
                .fetchData()) {

            Comment comment;

            while (rs.next()) {
                comment = Comment.builder()
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
}
