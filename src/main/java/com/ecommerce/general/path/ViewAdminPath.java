package com.ecommerce.general.path;

interface ViewAdminPath {

    final String rootViews = "/WEB-INF/views/";
    //admin Controller
    final String adminPath = "/ecommerce/admin/";
    final String adminJspPath = rootViews + "admin/";

    //get dashborad page
    final String dashboard_admin = adminJspPath + "dashboard_views/dashboard.jsp";

    //get Category Pages
    final String add_category_admin = adminJspPath + "category_views/add_category.jsp";

    final String edit_category_admin = adminJspPath + "category_views/edit_category.jsp";

    final String manage_category_admin = adminJspPath + "category_views/manage_categories.jsp";

    //get Item Pages 
    final String add_item_admin = adminJspPath + "item_views/add_item.jsp";

    final String edit_item_admin = adminJspPath + "item_views/edit_item.jsp";

    final String manage_item_admin = adminJspPath + "item_views/manage_items.jsp";

    //get User Pages
    final String add_user_admin = adminJspPath + "user_views/add_user.jsp";

    final String edit_user_admin = adminJspPath + "user_views/edit_user.jsp";

    final String manage_user_admin = adminJspPath + "user_views/manage_users.jsp";

    //get User Pages
    final String manage_comment_admin = adminJspPath + "comment_views/manage_comments.jsp";

    final String edit_comment_admin = adminJspPath + "comment_views/edit_comment.jsp";

    //get Include Pages
    final String header_admin = adminJspPath + "include_views/header.jsp";

    final String footer_admin = adminJspPath + "include_views/footer.jsp";

    final String navebar_admin = adminJspPath + "include_views/navbar.jsp";

}
