package com.ecommerce.publicc;

import com.ecommerce.general.category.Category;
import com.ecommerce.general.category.CategoryDaoImpl;
import com.ecommerce.general.helper.MySQLDatabaseHelper;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EcommerceContextListener implements ServletContextListener {

    /**
     * create database connection and set it to the app context when app created
     * for the first time
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        // get database init params table app context  
        String dbUrl = sc.getInitParameter("dbUrl");
        String dbUsername = sc.getInitParameter("dbUsername");
        String dbPassword = sc.getInitParameter("dbPassword");
        String dbName = sc.getInitParameter("dbName");

        // get database singletone instance
        MySQLDatabaseHelper db = MySQLDatabaseHelper.getInstance(dbUrl + dbName, dbUsername, dbPassword);

        // set database to request
        sc.setAttribute("db", db);
        
        List<Category> categories = new CategoryDaoImpl(sc).getAllSupCategories("ASC");
        
        sc.setAttribute("navCategories", categories);
    }

    /**
     * close database connection when app destroyed
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // get and close database connection
        ((MySQLDatabaseHelper) sce.getServletContext().getAttribute("db")).closeConnection();
    }
}
