package com.sandy.e3646.lifeblabla.object;

public class Account {

    private String mId; //note id
    private String mAccountId;
    private String mNumber;
    private String mCreatedTime;
    private String mDescription;
    private String mRevenue;
    private String mExpense;
    private String mCategory;




    public Account() {

        mId = "";
        mAccountId = "";
        mNumber = "";
        mCreatedTime = "";
        mDescription = "";
        mRevenue = "";
        mExpense = "";
        mCategory = "" ;
    }


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAccountId() {
        return mAccountId;
    }

    public void setAccountId(String accountId) {
        mAccountId = accountId;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        mCreatedTime = createdTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getRevenue() {
        return mRevenue;
    }

    public void setRevenue(String revenue) {
        mRevenue = revenue;
    }

    public String getExpense() {
        return mExpense;
    }

    public void setExpense(String expense) {
        mExpense = expense;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }



}
