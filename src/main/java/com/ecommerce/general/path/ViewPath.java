package com.ecommerce.general.path;

public class ViewPath implements 
        ViewAdminPath, ViewCustomerPath, ViewGeneralPath {

//     @Override
    public static String getTitle(String path) {
        String[] path_array = path.split("/");
        String titleDotJsp = path_array[path_array.length - 1];
        String title = titleDotJsp.substring(0, titleDotJsp.length() - 4);
        return title;
    }

//    @Override
    public static String getAdminTitle(String path) {
        return "admin/" + getTitle(path);
    }
    
}
