package com.ecommerce.general.path;

public interface AdminPattern {

//    final String adminControllerPath = "/ecommerce/admin/";
    final String adminPath = "/admin/";

    //get dashborad page
    final String AdminDashboard = adminPath + "dashboard";

    //get Category Pages
    final String AdminAddCategory = adminPath + "add-category";

    final String AdminEditCategory = adminPath + "edit-category";

    final String AdminManageCategory = adminPath + "manage-category";

    //get Item Pages 
    final String AdminAddItem = adminPath + "add-item";

    final String AdminEditItem = adminPath + "edit-item";

    final String AdminManageItem = adminPath + "manage-item";

    //get User Pages
    final String AdminAddUser = adminPath + "add-user";

    final String AdminEditUser = adminPath + "edit-user";

    final String AdminManageUser = adminPath + "manage-user";

    //get User Pages
    final String AdminAddComment = adminPath + "commadd-comment";

    final String AdminEditComment = adminPath + "commedit-comment";
}
