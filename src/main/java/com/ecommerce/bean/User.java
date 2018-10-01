package com.ecommerce.bean;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class User implements Bean {

    private long id;
    private String name;
    private String password;
    private String email;
    private String fullName;
    private int groupId;
    private int trustStatus;
    private int regStatus;
    private Date date;
}
