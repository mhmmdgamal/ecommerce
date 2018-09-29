//<editor-fold >
package com.ecommerce.helper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
//</editor-fold >


public final class MySQLDatabaseHelper {

    /**
     * Table Name
     *
     * @var string
     */
    private String table;

    /**
     * Data Container
     *
     * @var map
     */
    private Map<String, Object> data = new LinkedHashMap();

    /**
     * Bindings Container
     *
     * @var array
     */
    private ArrayList bindings = new ArrayList();

    /**
     * Wheres
     *
     * @var array
     */
    private ArrayList wheres = new ArrayList();

    /**
     * Selects
     *
     * @var array
     */
    private ArrayList<String> selects = new ArrayList();

    /**
     * Limit
     *
     * @var int
     */
    private int limit;

    /**
     * Offset
     *
     * @var int
     */
    private int offset;

    /**
     * Joins
     *
     * @var array
     */
    private ArrayList<String> joins = new ArrayList();

    /**
     * Order By
     *
     * @array
     */
    private ArrayList<String> orderBy = new ArrayList();

    /**
     * Order By
     *
     * @var string
     */
    private String sort;

    /**
     * Connection
     *
     * @var Connection
     */
    private Connection connection = null;

    /**
     * singleton instance of Database
     */
    private static MySQLDatabaseHelper instance = null;

    /**
     * connect to database
     *
     * @param url
     * @param userName
     * @param password
     */
    private MySQLDatabaseHelper(String url, String userName, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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
    
    /**
     * Get Database Connection Object PDO Object
     *
     * @return Connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Set select clause
     *
     * @param selects
     * @return this
     */
    public MySQLDatabaseHelper select(String... selects) {
        this.selects.addAll(Arrays.asList(selects));
        return this;
    }

    /**
     * Set Join clause
     *
     * @param join
     * @return this
     */
    public MySQLDatabaseHelper join(String join) {
        this.joins.add(join);
        return this;
    }

    /**
     * Set Limit
     *
     * @param limit
     * @return this
     */
    public MySQLDatabaseHelper limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Set and offset
     *
     * @param offset
     * @return this
     */
    public MySQLDatabaseHelper offset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Set Order By clause
     *
     * @param orderBy
     * @return this
     */
    public MySQLDatabaseHelper orderBy(String orderBy) {
        this.orderBy.add(orderBy);
        return this;
    }

    /**
     * Set sort clause
     *
     * @param sort
     * @return this
     */
    public MySQLDatabaseHelper sort(String sort) {
        if (sort.isEmpty()) {
            sort = "ASC";
        }
        this.sort = sort;
        return this;
    }

    /**
     * Fetch Records table Table
     *
     * @return ResultSet | null
     * @throws java.sql.SQLException
     */
    public ResultSet fetchData() throws SQLException {

        String query = this.fetchStatement();
        System.out.println(query);
        PreparedStatement ps = this.buildQuery(query, this.bindings);
        this.reset();
        ResultSet results = ps.executeQuery();

        return results;
    }

    /**
     * Prepare fetch Statement
     *
     * @return string
     */
    private String fetchStatement() {
        String query = "SELECT ";
        this.selects = Helper.replaceSpacesWithBlanck(this.selects);
        this.selects.removeAll(Arrays.asList(null, ""));
        if (Helper.isNotBlank(this.selects)) {
            query += String.join(", ", this.selects);
        } else {
            query += "* ";
        }
        query += " FROM " + this.table + " ";
        if (Helper.isNotBlank(this.joins)) {
            query += String.join(" ", this.joins);
        }
        if (Helper.isNotBlank(this.wheres)) {
            query += " WHERE " + String.join(" ", this.wheres) + " ";
        }
        if (Helper.isNotBlank(this.orderBy)) {
            query += " ORDER BY " + String.join(", ", this.orderBy);
        }
        if (Helper.isNotBlank(this.sort)) {
            query += " " + sort;
        }
        if (this.limit != 0) {
            query += " LIMIT " + this.limit;
        }
        if (this.offset != 0) {
            query += " OFFSET " + this.offset;
        }

        return query;
    }

    /**
     * Set the table name
     *
     * @param table
     * @return this
     */
    public MySQLDatabaseHelper table(String table) {
        this.table = table;
        return this;
    }

    /**
     * Delete Clause
     *
     * @return this
     * @throws java.sql.SQLException
     */
    public boolean delete() throws SQLException {

        String query = "DELETE FROM " + this.table + " ";
        if (Helper.isNotBlank(this.wheres)) {
            query += " WHERE " + String.join(" ", this.wheres);
        }
        System.out.println(query);
        PreparedStatement ps = this.buildQuery(query, this.bindings);
        this.reset();
        int rowsAffected = ps.executeUpdate();
        return (rowsAffected > 0);
    }

    /**
     * Set The Data that will be stored in database table
     *
     * @param key
     * @param value
     * @return this
     */
    public MySQLDatabaseHelper data(String key, Object value) {
        this.data.put(key, value);
        this.setBindings(value);
        return this;
    }

    /**
     * Insert Data to database
     *
     * @return this
     * @throws java.sql.SQLException
     */
    public boolean insert() throws SQLException {

        String query = "INSERT INTO " + this.table + " SET ";
        query += this.setFields();
        System.out.println(query);
        PreparedStatement ps = this.buildQuery(query, this.bindings);
        this.reset();
        int rowsAffected = ps.executeUpdate();
        return (rowsAffected > 0);
    }

    /**
     * Update Data In database
     *
     * @return this
     * @throws java.sql.SQLException
     */
    public boolean update() throws SQLException {

        String query = "UPDATE " + this.table + " SET ";
        query += this.setFields();
        if (Helper.isNotBlank(this.wheres)) {
            query += " WHERE " + String.join(" ", this.wheres);
        }
        PreparedStatement ps = this.buildQuery(query, this.bindings);
        this.reset();
        int rowsAffected = ps.executeUpdate();
        return (rowsAffected > 0);
    }

    /**
     * Set the fields for insert and update
     *
     * @return string
     */
    private String setFields() {
        String query = "";
        for (String key : this.data.keySet()) {
            query += "`" + key + "` = ? , ";
        }
        query = Helper.rTrim(query, ", ");
        return query;
    }

    /**
     * Add New Where clause
     *
     * @param query
     * @param wheres
     * @return this
     */
    public MySQLDatabaseHelper where(String query, Object... wheres) {
        this.setBindings(wheres);
        this.wheres.add(query);
        return this;
    }

    /**
     * Add New Where clause
     *
     * @param query
     * @return this
     */
    public MySQLDatabaseHelper where(String query) {
        this.wheres.add(query);
        return this;
    }

    /**
     * Execute the given buildQuery statement
     *
     * @param query
     * @param bindings
     * @return PreparedStatement object
     * @throws java.sql.SQLException
     */
    public PreparedStatement buildQuery(String query, ArrayList bindings) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int count = 1;
        for (Object binding : this.bindings) {
            if (binding instanceof String) {
                ps.setString(count, (String) binding);
                ++count;
            } else if (binding instanceof Byte) {
                ps.setByte(count, (byte) binding);
                ++count;
            } else if (binding instanceof Short) {
                ps.setShort(count, (short) binding);
                ++count;
            } else if (binding instanceof Integer) {
                ps.setInt(count, (int) binding);
                ++count;
            } else if (binding instanceof Long) {
                ps.setLong(count, (long) binding);
                ++count;
            } else if (binding instanceof Float) {
                ps.setFloat(count, (float) binding);
                ++count;
            } else if (binding instanceof Double) {
                ps.setDouble(count, (double) binding);
                ++count;
            } else if (binding instanceof Date) {
                ps.setDate(count, (Date) binding);
                ++count;
            }
        }
        return ps;
    }

    /**
     * Add the given value to bindings
     *
     * @param value
     * @return void
     */
    private void setBindings(Object... value) {
        this.bindings.addAll(Arrays.asList(value));
    }

    /**
     * Reset All Data
     *
     * @return void
     */
    private void reset() {
        this.bindings = new ArrayList<>();
        this.data = new LinkedHashMap<String, Object>();
        this.joins = new ArrayList<>();
        this.limit = 0;
        this.offset = 0;
        this.orderBy = new ArrayList<>();
        this.selects = new ArrayList<>();
        this.sort = null;
        this.table = null;
        this.wheres = new ArrayList<>();
    }
}
