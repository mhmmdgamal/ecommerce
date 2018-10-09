package com.ecommerce.general.path;

public interface ControllerAdminPath {

    final String ROOT_PATH = "/ecommerce/admin/";

    //get dashborad page
    final String DASHBOARD_ADMIN = ROOT_PATH + "dashboard";

    //get Category Controller Path
    final String ADD_CATEGORY_ADMIN = ROOT_PATH + "add-category";
    final String DELETE_CATEGORY_ADMIN = ROOT_PATH + "delete-category";

    final String EDIT_CATEGORY_ADMIN = ROOT_PATH + "edit-category";

    final String MANAGE_CATEGORY_ADMIN = ROOT_PATH + "manage-categories";

    //get Item Controller Path 
    final String ADD_ITEM_ADMIN = ROOT_PATH + "add-item";

    final String EDIT_ITEM_ADMIN = ROOT_PATH + "edit-item";

    final String DELETE_ITEM_ADMIN = ROOT_PATH + "delete-item";
    
    final String APPROVE_ITEM_ADMIN = ROOT_PATH + "approve-item";

    final String MANAGE_ITEM_ADMIN = ROOT_PATH + "manage-items";

    //get User Controller Path
    final String ADD_USER_ADMIN = ROOT_PATH + "add-user";

    final String EDIT_USER_ADMIN = ROOT_PATH + "edit-user";
    
    final String DELETE_USER_ADMIN = ROOT_PATH + "delete-user";
    
    final String ACTIVE_USER_ADMIN = ROOT_PATH + "active-user";

    final String MANAGE_USER_ADMIN = ROOT_PATH + "manage-users";

    //get User Controller Path
    final String ADD_COMMENT_ADMIN = ROOT_PATH + "commadd-comment";

    final String EDIT_COMMENT_ADMIN = ROOT_PATH + "commedit-comment";
    
    final String DELETE_COMMENT_ADMIN = ROOT_PATH + "delete-comment";
    
    final String APPROVE_COMMENT_ADMIN = ROOT_PATH + "approve-comment";

    final String MANAGE_COMMENT_ADMIN = ROOT_PATH + "manage-comments";
    
}
