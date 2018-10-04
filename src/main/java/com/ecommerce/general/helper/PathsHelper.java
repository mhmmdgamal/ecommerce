/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.general.helper;

import com.ecommerce.general.enumiration.ResourceParent;
import com.ecommerce.general.enumiration.ResourceType;
import com.ecommerce.general.enumiration.ViewParent;
import com.ecommerce.general.enumiration.ViewType;

/**
 *
 * @author mohamed
 */
public class PathsHelper {
    /**
     * generate path to view
     *
     * @param view
     * @param type
     * @param parent
     * @return full path to the view
     */
    public static String getView(String view, ViewType type, ViewParent parent) {
        String rootPath = "/WEB-INF/views/";

        rootPath += parent.toString().toLowerCase();

        // get class name with lower case
        String viewType = type.toString().toLowerCase();

        // remove "view" from class name
        viewType = Helper.rTrim(viewType, "view");

        viewType += "_views";

        String viewName = view.toLowerCase();

        if (!viewName.endsWith(".jsp")) {
            viewName = view.toLowerCase() + ".jsp";
        }

        String viewPath = rootPath + "/" + viewType + "/" + viewName;
        return viewPath;
    }

    /**
     * generate path to category view
     *
     * @param view
     * @return
     */
    public static String getAdminCategory(String view) {
        return getView(view, ViewType.CATEGORY, ViewParent.ADMIN);
    }
    
    /**
     * generate path to dashboard view
     *
     * @param view
     * @return
     */
    public static String getAdminDashboard(String view) {
        return getView(view, ViewType.DASHBOARD, ViewParent.ADMIN);
    }
    
    /**
     * generate path to comment view
     *
     * @param view
     * @return
     */
    public static String getAdminComment(String view) {
        return getView(view, ViewType.COMMENT, ViewParent.ADMIN);
    }
    
    /**
     * generate path to item view
     *
     * @param view
     * @return
     */
    public static String getAdminItem(String view) {
        return getView(view, ViewType.ITEM, ViewParent.ADMIN);
    }
    
    /**
     * generate path to include view
     *
     * @param view
     * @return
     */
    public static String getAdminInclude(String view) {
        return getView(view, ViewType.INCLUDE, ViewParent.ADMIN);
    }
    
    /**
     * generate path to user view
     *
     * @param view
     * @return
     */
    public static String getAdminUser(String view) {
        return getView(view, ViewType.USER, ViewParent.ADMIN);
    }
    
    /**
     * generate path to comment view
     *
     * @param view
     * @return
     */
    public static String getCustomerComment(String view) {
        return getView(view, ViewType.COMMENT, ViewParent.CUSTOMER);
    }
    
    /**
     * generate path to home view
     *
     * @param view
     * @return  
     */
    public static String getCustomerHome(String view) {
        return getView(view, ViewType.HOME, ViewParent.CUSTOMER);
    }
    
    /**
     * generate path to item view
     *
     * @param view
     * @return 
     */
    public static String getCustomerItem(String view) {
        return getView(view, ViewType.ITEM, ViewParent.CUSTOMER);
    }
    
    /**
     * generate path to user view
     *
     * @param view
     * @return
     */
    public static String getCustomerUser(String view) {
        return getView(view, ViewType.USER, ViewParent.CUSTOMER);
    }
    
    /**
     * generate path to error view
     *
     * @param view
     * @return  
     */
    public static String getPublicError(String view) {
        return getView(view, ViewType.ERROR, ViewParent.PUBLIC);
    }
    
    /**
     * generate path to include view
     *
     * @param view
     * @return f
     */
    public static String getPublicInclude(String view) {
        return getView(view, ViewType.INCLUDE, ViewParent.PUBLIC);
    }
    
    /**
     * generate path to login view
     *
     * @param view
     * @return 
     */
    public static String getPublicLogin(String view) {
        return getView(view, ViewType.LOGIN, ViewParent.PUBLIC);
    }

    
    /**
     * generate path to resource
     *
     * @param resourceName 
     * @param type
     * @param parent
     * @return 
     */
    public static String getResource(String resourceName, ResourceType type, ResourceParent parent) {
        String rootPath = "/ecommerce/resources/";

        rootPath += parent.toString().toLowerCase();

        // get class name with lower case
        String resourceType = type.toString().toLowerCase();

        String resourcePath = rootPath + "/" + resourceType + "/" + resourceName;
        return resourcePath;
    }
    
    /**
     * generate path to layout resource
     *
     * @param resourceName 
     * @return
     */
    public static String getAdminLayout(String resourceName) {
        return getResource(resourceName, ResourceType.LAYOUT, ResourceParent.ADMIN);
    }
    
    /**
     * generate path to img resource
     *
     * @param resourceName 
     * @return
     */
    public static String getPublicImg(String resourceName) {
        return getResource(resourceName, ResourceType.IMG, ResourceParent.PUBLIC);
    }
    
    /**
     * generate path to layout resource
     *
     * @param resourceName 
     * @return
     */
    public static String getPublicLayout(String resourceName) {
        return getResource(resourceName, ResourceType.LAYOUT, ResourceParent.PUBLIC);
    }
}
