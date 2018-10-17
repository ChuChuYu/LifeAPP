package com.example.e3646.lifeblabla.object;

import java.util.ArrayList;

public class Note {

    private String mId;
    private String mTitle;
    private String mText;
    private String mCreatedTime;
    private String mUpdatedTime;
    private ArrayList<String> mTag;

    private String mClassification;
    private String mPlace;

    private String mPicture;
    private String mVideo;
    private String mAudio;

    private String mMind;
    private String mWeather;

    private String mAccountRevenue;



    private String mAccountExpense;
    private String mAccountBalance;

    private ArrayList mAccountItem;
    private ArrayList mConferenceItem;

    public Note() {

        mId = "";
        mTitle = "";
        mText = "";
        mCreatedTime = "";
        mUpdatedTime = "";

        mClassification = "";
        mPlace = "";

        mPicture = "";
        mVideo = "";
        mAudio = "";

        mAccountRevenue = "$ 0";
        mAccountExpense = "$ 0";
        mAccountBalance = "$ 0";

        mMind = "";
        mWeather = "";

    }


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmCreatedTime() {
        return mCreatedTime;
    }

    public void setmCreatedTime(String mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }

    public String getmUpdatedTime() {
        return mUpdatedTime;
    }

    public void setmUpdatedTime(String mUpdatedTime) {
        this.mUpdatedTime = mUpdatedTime;
    }

    public ArrayList<String> getmTag() {
        return mTag;
    }

    public void setmTag(ArrayList<String> mTag) {
        this.mTag = mTag;
    }

    public String getmClassification() {
        return mClassification;
    }

    public void setClassification(String mClassification) {
        this.mClassification = mClassification;
    }

    public String getmPlace() {
        return mPlace;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getmPicture() {
        return mPicture;
    }

    public void setmPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setmVideo(String mVideo) {
        this.mVideo = mVideo;
    }

    public String getmAudio() {
        return mAudio;
    }

    public void setmAudio(String mAudio) {
        this.mAudio = mAudio;
    }

    public String getmMind() {
        return mMind;
    }

    public void setmMind(String mMind) {
        this.mMind = mMind;
    }

    public String getmWeather() {
        return mWeather;
    }

    public void setmWeather(String mWeather) {
        this.mWeather = mWeather;
    }

    public ArrayList getmAccountItem() {
        return mAccountItem;
    }

    public void setmAccountItem(ArrayList mAccountItem) {
        this.mAccountItem = mAccountItem;
    }

    public ArrayList getmConferenceItem() {
        return mConferenceItem;
    }

    public void setmConferenceItem(ArrayList mConferenceItem) {
        this.mConferenceItem = mConferenceItem;
    }

    public String getAccountRevenue() {
        return mAccountRevenue;
    }

    public void setAccountRevenue(String accountRevenue) {
        mAccountRevenue = accountRevenue;
    }

    public String getAccountExpense() {
        return mAccountExpense;
    }

    public void setAccountExpense(String accountExpense) {
        mAccountExpense = accountExpense;
    }

    public String getAccountBalance() {
        return mAccountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        mAccountBalance = accountBalance;
    }





}
