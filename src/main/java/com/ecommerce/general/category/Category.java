package com.ecommerce.general.category;

import com.ecommerce.publicc.Bean;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Category implements Bean {

    private long id;
    private String name;
    private String description;
    private int parent;
    private int ordering;
    private int visibility;
    private int allowComments;
    private int allowAds;
}
