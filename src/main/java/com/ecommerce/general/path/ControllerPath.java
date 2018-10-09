package com.ecommerce.general.path;

public interface ControllerPath extends ControllerAdminPath, ControllerEcommercePath{
    
    String ROOT_PATH = "/ecommerce/";
    //get Login Controller Path
    
    final String LOGIN = ROOT_PATH + "login";
    
    final String REGISTER = ROOT_PATH + "register";
    
    final String LOGOUT = ROOT_PATH + "logout";
}
