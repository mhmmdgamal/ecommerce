/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.bean;

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
