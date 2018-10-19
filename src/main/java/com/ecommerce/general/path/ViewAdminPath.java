package com.ecommerce.general.path;

interface ViewAdminPath {

    final String rootViews = "/WEB-INF/views/";

    final String adminJspPath = rootViews + "admin/";

    //get dashborad page
    final String dashboard_admin = adminJspPath + "dashboard/dashboard.jsp";

    //get Category Pages
    final String add_category_admin = adminJspPath + "category/add.jsp";

    final String edit_category_admin = adminJspPath + "category/edit.jsp";

    final String manage_category_admin = adminJspPath + "category/manage.jsp";

    //get Item Pages 
    final String add_item_admin = adminJspPath + "item/add.jsp";

    final String edit_item_admin = adminJspPath + "item/edit.jsp";

    final String manage_item_admin = adminJspPath + "item/manage.jsp";

    //get User Pages
    final String add_user_admin = adminJspPath + "user/add.jsp";

    final String edit_user_admin = adminJspPath + "user/edit.jsp";

    final String manage_user_admin = adminJspPath + "user/manage.jsp";

    //get User Pages
    final String manage_comment_admin = adminJspPath + "comment/manage.jsp";

    final String edit_comment_admin = adminJspPath + "comment/edit.jsp";

    //get Include Pages
    final String header_admin = adminJspPath + "include/header.jsp";

    final String footer_admin = adminJspPath + "include/footer.jsp";

    final String navebar_admin = adminJspPath + "include/navbar.jsp";

    //get title of pages to able to redirect
//    public String getAdminTitle(String path);
}
