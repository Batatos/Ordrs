package com.example.jorjborj.ordrs;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ahed on 5/3/2018.
 */

public class UpcomingEventObject implements Serializable {

    private static final long serialVersionUID = 1L;
    int tableNum;
    String contactName;
    int phoneNum;
    int numOfPeople;
    String notes;
    String timeDate;

    public UpcomingEventObject(){

    }

    public UpcomingEventObject(int tableNum, String contactName, int phoneNum, int numOfPeople, String notes, String timeDate) {
        this.tableNum = tableNum;
        this.contactName = contactName;
        this.phoneNum = phoneNum;
        this.numOfPeople = numOfPeople;
        this.notes = notes;
        this.timeDate = timeDate;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }
}
