package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataRoom {

    String rid;
    String cid;
    String sid;
    String day;
    String period;

    public DataRoom(DataSnapshot dataSnapshot,String rid){

    }
    public DataRoom(String rid,String day,String period,String cid,String sid){
        this.rid=rid;
        this.cid=cid;
        this.sid=sid;
        this.day=day;
        this.period=period;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
