package com.ecommerce.general.item;

import com.ecommerce.general.user.User;
import com.ecommerce.general.category.Category;
import com.ecommerce.general.bean.Bean;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Item implements Bean {

    private long id;
    private String name;
    private String description;
    private String price;
    private Date addDate;
    private String countryMade;
    private String image;
    private String status;
    private byte rating;
    private byte approve;
    private String tags;
    private User user;
    private Category category;
}
