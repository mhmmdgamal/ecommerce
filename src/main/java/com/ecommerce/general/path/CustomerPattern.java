package com.ecommerce.general.path;

public interface CustomerPattern {

    final String customerPath = "/ecommerce/";

    //get Home Page
    final String CustomerHome = customerPath + "home";

    //get Item Pages
    final String CustomerAddItem = customerPath + "add-item";
    final String CustomerEditItem = customerPath + "edit-item";
    final String CustomerShowItem = customerPath + "show-item";

    //get Category Pages
    final String CustomerShowCategory = customerPath + "show-category";

    //get Tag Page
    final String CustomerShowTag = customerPath + "show-tag";

    //get User Pages
    final String CustomerEditUser = customerPath + "edit-user";
    final String CustomerShowUser = customerPath + "show-user";
    final String CustomerShowProfile = customerPath + "show-profile";

    //get Comment Pages
    final String CustomerEditComment = customerPath + "edit-comment";

}
