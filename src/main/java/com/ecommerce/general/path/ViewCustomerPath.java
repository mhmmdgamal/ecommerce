package com.ecommerce.general.path;

interface ViewCustomerPath {

    final String rootViews = "/WEB-INF/views/";
    final String customerJspPath = rootViews + "customer/";

    //get Home Page
    final String home = customerJspPath + "home/home.jsp";

    //get Item Pages
    final String add_item = customerJspPath + "item/add.jsp";
    final String edit_item = customerJspPath + "item/edit.jsp";
    final String show_item = customerJspPath + "item/show.jsp";

    //get Category Pages
    final String show_category = customerJspPath + "item/related/show_category.jsp";

    //get Tag Page
    final String show_tag = customerJspPath + "item/related/show_tag.jsp";

    //get User Pages
    final String edit_profile = customerJspPath + "user/profile/edit.jsp";
    final String show_profile = customerJspPath + "user/profile/show.jsp";
    final String show_user = customerJspPath + "user/show.jsp";

    //get Comment Pages
    final String edit_comment = customerJspPath + "comment/edit.jsp";

    //get Include Pages
    final String header = customerJspPath + "include/header.jsp";
    final String footer = customerJspPath + "include/footer.jsp";
    final String navbar = customerJspPath + "include/navbar.jsp";
}
