
import com.ecommerce.helper.Helper;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mohamed and Ramadan
 */
public class Test {

    public static void main(String[] args) {
        try {
            //        Field[] declaredFields = Category.class.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            System.out.println(declaredField.getType().getSimpleName());
//            System.out.println(declaredField.getName());
//        }

            System.out.println(new Test().prepareFindQuery(new String[]{"name", "sdf"}, "users", "`id` > 0", "id", null));
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String prepareFindQuery(String[] columns, String table, String where, String orderBy, String sort) throws SQLException {
        String query = "SELECT ";

        if (columns != null) {
            if (columns.length == 1) {
                String column = columns[0].trim();
                if (column.equals("*")) {
                    query += column;
                } else {
                    query += "`" + column + "` ";
                }
            } else {
                for (int i = 0; i < columns.length; i++) {
                    query += "`" + columns[i] + "`, ";
                }
                query = Helper.rTrim(query, ", ");
            }
        }

        if (table != null) {
            query += " From `" + table + "`";
        }

        if (where != null) {
            query += " WHERE " + where;
        }

        if (orderBy != null) {
            query += " ORDER BY `" + orderBy + "` ";
        }

        if (sort != null) {
            if (sort.equalsIgnoreCase("ASC") || sort.equalsIgnoreCase("DESC")) {
                query += sort.toUpperCase();
            }
        }
        return query;
    }
}
