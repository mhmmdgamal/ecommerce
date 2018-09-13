/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.bean;

import java.sql.Date;

public class Comment implements Bean {

    private long id;
    private String comment;
    private byte status;
    private Date addDate;
    private User user;
    private Item item;

    public Comment() {
    }

    public Comment(long id, String comment, byte status, Date addDate, User user, Item item) {
        this.id = id;
        this.comment = comment;
        this.status = status;
        this.addDate = addDate;
        this.user = user;
        this.item = item;
    }

    public Comment(String comment, byte status, Date addDate, User user, Item item) {
        this.comment = comment;
        this.status = status;
        this.addDate = addDate;
        this.user = user;
        this.item = item;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Comment other = (Comment) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", comment=" + comment + ", status=" + status + ", addDate=" + addDate + ", user=" + user + ", item=" + item + '}';
    }

}
