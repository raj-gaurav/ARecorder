package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataAttendance {

    String present,total,c_id,tid,sid,subid,month,flag,date,period;

    public DataAttendance(String present, DataSnapshot dataSnapshot){

    }
    public DataAttendance(String c_id,String sid,String month,String subid,String tid,String present,String total,String flag,String date,String period){
        this.c_id=c_id;
        this.sid=sid;
        this.month=month;
        this.subid=subid;
        this.tid=tid;
        this.present=present;
        this.total=total;
        this.flag=flag;
        this.date=date;
        this.period=period;

    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
