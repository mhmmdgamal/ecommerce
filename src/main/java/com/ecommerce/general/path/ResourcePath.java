package com.ecommerce.general.path;

public interface ResourcePath {

    String rootPath = "/ecommerce/resources/";

    //admin layout
    final String css_admin = rootPath + "admin/layout/css/";
    final String js_admin = rootPath + "admin/layout/js/";
    final String fonts_admin = rootPath + "admin/layout/fonts/";
    //ecommerce images path
    final String img_admin = rootPath + "admin/img/";

    ////////////////////////////////////////////////////////////////////////////
    
    //ecommerece layout path
    final String css = rootPath + "ecommerce/layout/css/";
    final String js = rootPath + "ecommerce/layout/js/";
    final String fonts = rootPath + "ecommerce/layout/fonts/";
    //ecommerce images path
    final String img = rootPath + "ecommerce/img/";

}
