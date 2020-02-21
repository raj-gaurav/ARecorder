package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

import java.util.Date;

public class DataSubject {
    String subid;
    String cid;
    String subject;
    String tid;
    String tclass;
    String key;
    String flag;
    String date;
    String period;
    public DataSubject(DataSnapshot dataSnapshot,String cid){

    }

    public DataSubject(String sid,String cid,String subject,String tid, String tclass,String key,String flag,String date,String period){
        this.subid=sid;
        this.cid=cid;
        this.subject=subject;
        this.tid=tid;
        this.tclass=tclass;
        this.key=key;
        this.flag=flag;
        this.date=date;
        this.period=period;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTclass() {
        return tclass;
    }

    public void setTclass(String tclass) {
        this.tclass = tclass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
