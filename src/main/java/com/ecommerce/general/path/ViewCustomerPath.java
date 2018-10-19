package com.ecommerce.general.path;

interface ViewCustomerPath {

    final String ROOT_VIEWS = "/WEB-INF/views/";
    final String CUSTOMER_JSP_PATH = ROOT_VIEWS + "customer/";

    //get Home Page
    final String home = CUSTOMER_JSP_PATH + "home/home.jsp";

    //get Item Pages
    final String add_item = CUSTOMER_JSP_PATH + "item/add.jsp";
    final String edit_item = CUSTOMER_JSP_PATH + "item/edit.jsp";
    final String show_item = CUSTOMER_JSP_PATH + "item/show.jsp";

    //get Category Pages
    final String show_category = CUSTOMER_JSP_PATH + "item/related/show_category.jsp";

    //get Tag Page
    final String show_tag = CUSTOMER_JSP_PATH + "item/related/show_tag.jsp";

    //get User Pages
    final String edit_profile = CUSTOMER_JSP_PATH + "user/profile/edit.jsp";
    final String show_profile = CUSTOMER_JSP_PATH + "user/profile/show.jsp";
    final String show_user = CUSTOMER_JSP_PATH + "user/show.jsp";

    //get Include Pages
    final String header = CUSTOMER_JSP_PATH + "include/header.jsp";
    final String footer = CUSTOMER_JSP_PATH + "include/footer.jsp";
    final String navbar = CUSTOMER_JSP_PATH + "include/navbar.jsp";

    // get title of page from url
//    public static String getTitle(String path);
}
