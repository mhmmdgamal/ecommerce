package com.ecommerce.general.path;

public class ViewPath implements
        ViewAdminPath, ViewCustomerPath, ViewGeneralPath {

    /**
     * @param path
     * @return Url Pattern of controller from PATH of page.jsp
     */
    //    @Override
    public static String getUrlPattern(String path) {
        //split param path by slash
        String[] path_array = path.split("/");
        //get latest element(title.jsp) from array
        String titleDotJsp = path_array[path_array.length - 1];
        //remove (.jsp) from title
        String urlPattern = titleDotJsp.substring(0, titleDotJsp.length() - 4);
        return urlPattern;
    }

//    @Override
    public static String getUrlPatternAdmin(String path) {
        //add Slash admin for pattern 
        return "admin/" + getUrlPattern(path);
    }

}
