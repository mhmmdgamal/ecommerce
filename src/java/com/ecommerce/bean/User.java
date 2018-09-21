/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.bean;

import java.sql.Date;

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

    public User(long id, String name, String password, String email, String fullName, int groupId, int trustStatus, int regStatus, Date date) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.groupId = groupId;
        this.trustStatus = trustStatus;
        this.regStatus = regStatus;
        this.date = date;
    }

    public User(String name, String password, String email, String fullName, int groupId, int trustStatus, int regStatus, Date date) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.groupId = groupId;
        this.trustStatus = trustStatus;
        this.regStatus = regStatus;
        this.date = date;
    }

    public User() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        if (fullName == null) {
            return name;
        }
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the trustStatus
     */
    public int getTrustStatus() {
        return trustStatus;
    }

    /**
     * @param trustStatus the trustStatus to set
     */
    public void setTrustStatus(int trustStatus) {
        this.trustStatus = trustStatus;
    }

    /**
     * @return the regStatus
     */
    public int getRegStatus() {
        return regStatus;
    }

    /**
     * @param regStatus the regStatus to set
     */
    public void setRegStatus(int regStatus) {
        this.regStatus = regStatus;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
        final User other = (User) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", fullName=" + fullName + '}';
    }
}
