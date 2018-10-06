package com.ecommerce.general.path;

interface ViewGeneralPath {

    final String rootViews = "/WEB-INF/views/";
    final String generalJspPath = rootViews + "general/";

    //get Login pages
    final String login_Form = generalJspPath + "login_views/login_form.jsp";
    final String register_Form = generalJspPath + "login_views/register_form.jsp";
    final String login_register = generalJspPath + "login_views/login_register.jsp";

    //fet Include Pages
    final String header = generalJspPath + "include_views/header.jsp";
    final String footer = generalJspPath + "include_views/footer.jsp";
    final String navbar = generalJspPath + "include_views/navbar.jsp";
    final String success_error = generalJspPath + "include_views/success_error.jsp";

    //get Error Pages
    final String error_404 = generalJspPath + "error_views/404.jsp";
    final String error_500 = generalJspPath + "error_views/500.jsp";

}
