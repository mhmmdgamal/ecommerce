package com.ecommerce.general.path;

public class ControllerPath implements ControllerAdminPath, ControllerCustomerPath {

    @Override
    public String getPattern(String path) {

        String[] path_array = path.split("/");
        String urlPattern = path_array[path_array.length - 1];

        return urlPattern;
    }

    @Override
    public String getAdminPattern(String path) {

        String[] path_array = path.split("/");
        String urlPattern = path_array[path_array.length - 1];

        return "/admin" + urlPattern;
    }

}
