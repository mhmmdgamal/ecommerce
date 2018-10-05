package com.ecommerce.general.path;

interface AdminViewsPath {
        


    final String rootViews = "/WEB-INF/views/";
    //admin Controller
    final String adminPath = "/ecommerce/admin/";
    final String adminJspPath = rootViews + "admin/";

    //get dashborad page
    final String AdminDashboard = adminJspPath + "dashboard_views/dashboard.jsp";

    //get Category Pages
    final String AdminAddCategory = adminJspPath + "category_views/add_category.jsp";

    final String AdminEditCategory = adminJspPath + "category_views/edit_category.jsp";

    final String AdminManageCategory = adminJspPath + "category_views/manage_category.jsp";

    //get Item Pages 
    final String AdminAddItem = adminJspPath + "item_views/add_item.jsp";

    final String AdminEditItem = adminJspPath + "item_views/edit_item.jsp";

    final String AdminManageItem = adminJspPath + "item_views/manage_item.jsp";

    //get User Pages
    final String AdminAddUser = adminJspPath + "user_views/add_user.jsp";

    final String AdminEditUser = adminJspPath + "user_views/edit_user.jsp";

    final String AdminManageUser = adminJspPath + "user_views/manage_user.jsp";

    //get User Pages
    final String AdminAddComment = adminJspPath + "comment_views/add_comment.jsp";

    final String AdminEditComment = adminJspPath + "comment_views/edit_comment.jsp";

    //get Include Pages
    final String AdminHeader = adminJspPath + "include_views/header.jsp";

    final String AdminFooter = adminJspPath + "include_views/footer.jsp";

    final String AdminNavebar = adminJspPath + "include_views/navbar.jsp";

}
