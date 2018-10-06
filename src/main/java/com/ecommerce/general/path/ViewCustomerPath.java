package com.ecommerce.general.path;

interface ViewCustomerPath {

    final String rootViews = "/WEB-INF/views/";
    final String customerJspPath = rootViews + "customer/";

    //get Home Page
    final String home = customerJspPath + "home_views/home.jsp";

    //get Item Pages
    final String add_item = customerJspPath + "item_views/add_item.jsp";
    final String edit_item = customerJspPath + "item_views/edit_item.jsp";
    final String show_item = customerJspPath + "item_views/show_item.jsp";

    //get Category Pages
    final String show_category = customerJspPath + "item_views/show_category.jsp";

    //get Tag Page
    final String show_tag = customerJspPath + "item_views/show_tag.jsp";

    //get User Pages
    final String edit_profile = customerJspPath + "user_views/edit_profile.jsp";
    final String show_user = customerJspPath + "user_views/show_user.jsp";
    final String show_profile = customerJspPath + "user_views/show_profile.jsp";

    //get Comment Pages
    final String edit_comment = customerJspPath + "comment_views/edit_comment.jsp";

}
