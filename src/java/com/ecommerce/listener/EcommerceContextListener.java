/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.listener;

import com.ecommerce.helper.MySQLDatabaseHelper;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EcommerceContextListener implements ServletContextListener {

    /**
     * create database connection and set it to the app context
     * when app created for the first time
     * 
     * @param sce 
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
 
        // get database init params from app context  
    	String dbUrl = sc.getInitParameter("dbUrl");
    	String dbUsername = sc.getInitParameter("dbUsername");
    	String dbPassword = sc.getInitParameter("dbPassword");
    	String dbName = sc.getInitParameter("dbName");
        
        // get database singletone instance
    	MySQLDatabaseHelper db = MySQLDatabaseHelper.getInstance(dbUrl + dbName, dbUsername, dbPassword);
        
        // set database to request
        sc.setAttribute("db", db);
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
