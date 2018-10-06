package com.ecommerce.general.path;

public interface ControllerCustomerPath {

    final String rootPath = "/ecommerce/";

    //get Home Page
    final String CustomerHome = rootPath + "home";

    //get Item Pages
    final String CustomerAddItem = rootPath + "add-item";
    final String CustomerEditItem = rootPath + "edit-item";
    final String CustomerShowItem = rootPath + "show-item";

    //get Category Pages
    final String CustomerShowCategory = rootPath + "show-category";

    //get Tag Page
    final String CustomerShowTag = rootPath + "show-tag";

    //get User Pages
    final String CustomerEditUser = rootPath + "edit-user";
    final String CustomerShowUser = rootPath + "show-user";
    final String CustomerShowProfile = rootPath + "show-profile";

    //get Comment Pages
    final String CustomerEditComment = rootPath + "edit-comment";

    public String getPattern(String path);

}
