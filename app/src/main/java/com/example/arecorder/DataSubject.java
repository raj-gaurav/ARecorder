package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataSubject {
    String subid;
    String cid;
    String subject;
    String tid;
    String tclass;
    String key;

    public DataSubject(DataSnapshot dataSnapshot,String cid){

    }

    public DataSubject(String sid,String cid,String subject,String tid, String tclass,String key){
        this.subid=sid;
        this.cid=cid;
        this.subject=subject;
        this.tid=tid;
        this.tclass=tclass;
        this.key=key;
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
