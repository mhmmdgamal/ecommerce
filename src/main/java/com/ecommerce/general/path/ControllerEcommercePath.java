package com.ecommerce.general.path;

public interface ControllerEcommercePath {

    final String ROOT_PATH = "/ecommerce/";

    //get Home Page
    final String HOME = ROOT_PATH + "home";

    //get _ITEM Pages
    final String ADD_ITEM = ROOT_PATH + "add-item";
    final String EDIT__ITEM = ROOT_PATH + "edit-item";
    final String SHOW__ITEM = ROOT_PATH + "show-item";
    final String DELETE__ITEM = ROOT_PATH + "delete-item";

    //get Category Pages
    final String SHOW_CATEGORY = ROOT_PATH + "show-category";

    //get Tag Page
    final String SHOW_TAG = ROOT_PATH + "show-tag";

    //get User Pages
    final String EDIT_PROFILE = ROOT_PATH + "edit-profile";
    final String SHOW_USER = ROOT_PATH + "show-user";
    final String SHOW_PROFILE = ROOT_PATH + "show-profile";

    //get Comment Pages
    final String EDIT_COMMENT = ROOT_PATH + "edit-comment";
    final String ADD_COMMENT = ROOT_PATH + "add-comment";
    final String DELETE_COMMENT = ROOT_PATH + "delete-comment";

}
