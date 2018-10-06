package com.ecommerce.general.path;

interface ViewGeneralPath {

    final String rootViews = "/WEB-INF/views/";
    final String generalJspPath = rootViews + "general/";

    //get Login pages
    final String GeneralLoginForm = generalJspPath + "login_views/login_form.jsp";
    final String GeneralRegisterForm = generalJspPath + "login_views/register_form.jsp";
    final String GeneralLoginRegister = generalJspPath + "login_views/login_register.jsp";

    //fet Include Pages
    final String GeneralHeader = generalJspPath + "include_views/header.jsp";
    final String GeneralFooter = generalJspPath + "include_views/footer.jsp";
    final String GeneralNavebar = generalJspPath + "include_views/navbar.jsp";
    final String GeneralSuccessError = generalJspPath + "include_views/success_error.jsp";

    //get Error Pages
    final String GeneralError404 = generalJspPath + "error_views/404.jsp";
    final String GeneralError500 = generalJspPath + "error_views/500.jsp";

}
