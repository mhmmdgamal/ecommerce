/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.bean;

import java.sql.Date;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getCountryMade() {
        return countryMade;
    }

    public void setCountryMade(String countryMade) {
        this.countryMade = countryMade;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public byte getApprove() {
        return approve;
    }

    public void setApprove(byte approve) {
        this.approve = approve;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        final Item other = (Item) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", addDate=" + addDate + ", countryMade=" + countryMade + ", image=" + image + ", status=" + status + ", rating=" + rating + ", approve=" + approve + ", tags=" + tags + ", user=" + user + ", category=" + category + '}';
    }

    private Item(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.addDate = builder.addDate;
        this.countryMade = builder.countryMade;
        this.image = builder.image;
        this.status = builder.status;
        this.rating = builder.rating;
        this.approve = builder.approve;
        this.tags = builder.tags;
        this.user = builder.user;
        this.category = builder.category;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

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

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(String price) {
            this.price = price;
            return this;
        }

        public Builder addDate(Date addDate) {
            this.addDate = addDate;
            return this;
        }

        public Builder countryMade(String countryMade) {
            this.countryMade = countryMade;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder rating(byte rating) {
            this.rating = rating;
            return this;
        }

        public Builder approve(byte approve) {
            this.approve = approve;
            return this;
        }

        public Builder tags(String tags) {
            this.tags = tags;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

}
