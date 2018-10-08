package com.ecommerce.general.path;

interface ViewGeneralPath {

    final String rootViews = "/WEB-INF/views/";
    final String generalJspPath = rootViews + "general/";

    //get Login pages
    final String login_Form = generalJspPath + "login/login_form.jsp";
    final String register_Form = generalJspPath + "login/register_form.jsp";
    final String login_register = generalJspPath + "login/login_register.jsp";

    final String success_error = generalJspPath + "success_error.jsp";

    //get Error Pages
    final String error_404 = generalJspPath + "error/404.jsp";
    final String error_500 = generalJspPath + "error/500.jsp";

}
