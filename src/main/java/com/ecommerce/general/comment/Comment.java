package com.ecommerce.general.comment;

import com.ecommerce.general.item.Item;
import com.ecommerce.general.user.User;
import com.ecommerce.general.bean.Bean;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Comment implements Bean {

    private long id;
    private String comment;
    private byte status;
    private Date addDate;
    private User user;
    private Item item;
}
