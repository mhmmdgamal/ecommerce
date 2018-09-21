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

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
        this.fullName = builder.fullName;
        this.groupId = builder.groupId;
        this.trustStatus = builder.trustStatus;
        this.regStatus = builder.regStatus;
        this.date = builder.date;
    }

    public static class Builder {

        private long id;
        private String name;
        private String password;
        private String email;
        private String fullName;
        private int groupId;
        private int trustStatus;
        private int regStatus;
        private Date date;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder groupId(int groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder trustStatus(int trustStatus) {
            this.trustStatus = trustStatus;
            return this;
        }

        public Builder regStatus(int regStatus) {
            this.regStatus = regStatus;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
