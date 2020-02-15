package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataRoutine {

    String rid;
    String day;
    String period;
    String subid;
    String total;

    public DataRoutine(DataSnapshot dataSnapshot,String rid){

    }

    public DataRoutine(String rid,String day,String period,String subid,String total){
        this.rid=rid;
        this.day=day;
        this.period=period;
        this.subid=subid;
        this.total=total;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
