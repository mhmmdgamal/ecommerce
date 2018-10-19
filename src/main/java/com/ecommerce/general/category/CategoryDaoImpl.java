package com.ecommerce.general.category;

import com.ecommerce.general.helper.MySQLDatabaseHelper;
import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.UserDaoImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class CategoryDaoImpl implements CategoryDao {

    private final MySQLDatabaseHelper db;
    private final String table = "categories";
    private final ServletContext sc;

    public CategoryDaoImpl(ServletContext sc) {
        // get database instance table app context
        this.db = (MySQLDatabaseHelper) sc.getAttribute("db");

        this.sc = sc;
    }

    /**
     * update category
     *
     * @param category
     * @return true if updated false otherwise
     */
    @Override
    public boolean updateCategory(Category category) {
        boolean updated = false;
        try {
            updated = db.table(table)
                    .data("name", category.getName())
                    .data("description", category.getDescription())
                    .data("ordering", category.getOrdering())
                    .data("parent", category.getParent())
                    .data("visibility", category.getVisibility())
                    .data("allow_comments", category.getAllowComments())
                    .data("allow_ads", category.getAllowAds())
                    .where("`id`=?", category.getId())
                    .update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }
    
    /**
     * get last category id
     *
     * @return int
     */
    @Override
    public int getLastCategoryId() {
        try {
            return db.getLastId();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * add category
     *
     * @param category
     * @return true if added false otherwise
     */
    @Override
    public boolean addCategory(Category category) {
        boolean inserted = false;
        try {
            inserted = db.table(table)
                    .data("name", category.getName())
                    .data("description", category.getDescription())
                    .data("ordering", category.getOrdering())
                    .data("parent", category.getParent())
                    .data("visibility", category.getVisibility())
                    .data("allow_comments", category.getAllowComments())
                    .data("allow_ads", category.getAllowAds())
                    .insert();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return inserted;
    }

    /**
     * delete category
     *
     * @param id
     * @return true if deleted false otherwise
     */
    @Override
    public boolean deleteCategory(long id) {
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
     * get all super categories data table database
     *
     * @param sort
     * @return found categories
     */
    @Override
    public List<Category> getAllSupCategories(String sort) {
        List<Category> categories = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .where("`parent`=0")
                .orderBy("ordering")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Category category = Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .ordering(rs.getInt("ordering"))
                        .parent(rs.getInt("parent"))
                        .visibility(rs.getInt("visibility"))
                        .allowComments(rs.getInt("allow_comments"))
                        .allowAds(rs.getInt("allow_ads"))
                        .build();
                categories.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * get all categories data table database
     *
     * @param sort
     * @return found categories
     */
    @Override
    public List<Category> getAllCategories(String sort) {
        List<Category> categories = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .orderBy("ordering")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Category category = Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .ordering(rs.getInt("ordering"))
                        .parent(rs.getInt("parent"))
                        .visibility(rs.getInt("visibility"))
                        .allowComments(rs.getInt("allow_comments"))
                        .allowAds(rs.getInt("allow_ads"))
                        .build();
                categories.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * get all sub categories data table database
     *
     * @param sort
     * @return found categories
     */
    @Override
    public List<Category> getAllSubCategories(String sort) {
        List<Category> categories = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .where("`parent`!=0")
                .orderBy("ordering")
                .sort(sort)
                .fetchData()) {

            while (rs.next()) {
                Category category = Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .ordering(rs.getInt("ordering"))
                        .parent(rs.getInt("parent"))
                        .visibility(rs.getInt("visibility"))
                        .allowComments(rs.getInt("allow_comments"))
                        .allowAds(rs.getInt("allow_ads"))
                        .build();
                categories.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * get all items linked with the category
     *
     * @param id
     * @param sort
     * @return items
     */
    @Override
    public List<Item> getCategoryItems(long id, String sort) {
        List<Item> items = new ArrayList();

        try (ResultSet rs = db.select()
                .table("items")
                .where("`category_id`=?", id)
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
     * get latest categories data table database depending on number
     *
     * @param num
     * @return latest num categories
     */
    @Override
    public List<Category> getLatestCategories(int num) {
        List<Category> categories = new ArrayList();

        try (ResultSet rs = db.select()
                .table(table)
                .orderBy("id")
                .sort("DESC")
                .limit(num)
                .fetchData()) {

            while (rs.next()) {
                Category category = Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .ordering(rs.getInt("ordering"))
                        .parent(rs.getInt("parent"))
                        .visibility(rs.getInt("visibility"))
                        .allowComments(rs.getInt("allow_comments"))
                        .allowAds(rs.getInt("allow_ads"))
                        .build();
                categories.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * get number of categories
     *
     * @return categories number or 0
     */
    @Override
    public int getNumCategories() {
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
     * get specific category with id
     *
     * @param id
     * @return found category
     */
    @Override
    public Category getCategoryById(long id) {
        Category category = null;

        try (ResultSet rs = db.select()
                .table(table)
                .where("`id`=?", id)
                .fetchData()) {

            if (rs.next()) {
                category = Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .ordering(rs.getInt("ordering"))
                        .parent(rs.getInt("parent"))
                        .visibility(rs.getInt("visibility"))
                        .allowComments(rs.getInt("allow_comments"))
                        .allowAds(rs.getInt("allow_ads"))
                        .build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return category;
    }
}
