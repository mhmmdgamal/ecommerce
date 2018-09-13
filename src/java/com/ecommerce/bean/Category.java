/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.bean;

public class Category implements Bean {

    private long id;
    private String name;
    private String description;
    private int parent;
    private int ordering;
    private int visibility;
    private int allowComments;
    private int allowAds;

    public Category(long id, String name, String description, int parent, int ordering, int visibility, int allowComments, int allowAds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.ordering = ordering;
        this.visibility = visibility;
        this.allowComments = allowComments;
        this.allowAds = allowAds;
    }

    public Category(String name, String description, int parent, int ordering, int visibility, int allowComments, int allowAds) {
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.ordering = ordering;
        this.visibility = visibility;
        this.allowComments = allowComments;
        this.allowAds = allowAds;
    }

    public Category() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(int allowComments) {
        this.allowComments = allowComments;
    }

    public int getAllowAds() {
        return allowAds;
    }

    public void setAllowAds(int allowAds) {
        this.allowAds = allowAds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + ", description=" + description + ", parent=" + parent + ", ordering=" + ordering + ", visibility=" + visibility + ", allowComments=" + allowComments + ", allowAds=" + allowAds + '}';
    }

}
