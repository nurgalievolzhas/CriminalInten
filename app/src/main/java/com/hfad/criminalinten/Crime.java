package com.hfad.criminalinten;

import java.util.Date;
import java.util.UUID;

public class Crime {
    //declare needed fields
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    //Constructor
    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    //Getters and Setters

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
