package com.ecommerce.general.path;

public interface ControllerAdminPath {

    final String rootPath = "/ecommerce/admin/";

    //get dashborad page
    final String AdminDashboard = rootPath + "dashboard";

    //get Category Pages
    final String AdminAddCategory = rootPath + "add-category";

    final String AdminEditCategory = rootPath + "edit-category";

    final String AdminManageCategory = rootPath + "manage-category";

    //get Item Pages 
    final String AdminAddItem = rootPath + "add-item";

    final String AdminEditItem = rootPath + "edit-item";

    final String AdminManageItem = rootPath + "manage-item";

    //get User Pages
    final String AdminAddUser = rootPath + "add-user";

    final String AdminEditUser = rootPath + "edit-user";

    final String AdminManageUser = rootPath + "manage-user";

    //get User Pages
    final String AdminAddComment = rootPath + "commadd-comment";

    final String AdminEditComment = rootPath + "commedit-comment";
    
}
