package com.ecommerce.general.path;

interface CustomerViews {

    final String rootViews = "/WEB-INF/views/";
    final String customerJspPath = rootViews + "customer/";

    //get Home Page
    final String CustomerHome = customerJspPath + "home_views/home.jsp";

    //get Item Pages
    final String CustomerAddItem = customerJspPath + "item_views/add_item.jsp";
    final String CustomerEditItem = customerJspPath + "item_views/edit_item.jsp";
    final String CustomerShowItem = customerJspPath + "item_views/show_item.jsp";

    //get Category Pages
    final String CustomerShowCategory = customerJspPath + "item_views/show_category.jsp";

    //get Tag Page
    final String CustomerShowTag = customerJspPath + "item_views/show_tag.jsp";

    //get User Pages
    final String CustomerEditUser = customerJspPath + "user_views/edit_user.jsp";
    final String CustomerShowUser = customerJspPath + "user_views/show_user.jsp";
    final String CustomerShowProfile = customerJspPath + "user_views/show_profile.jsp";

    //get Comment Pages
    final String CustomerEditComment = customerJspPath + "comment_views/edit_comment.jsp";

}
