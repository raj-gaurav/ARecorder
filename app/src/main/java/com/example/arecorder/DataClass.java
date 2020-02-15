package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataClass {

    String id;
    String branch;
    String section;
    String year;
    String sem;

    public DataClass(String id, DataSnapshot child){

    }

    public DataClass(String id,String branch,String section,String year,String sem){

        this.id=id;
        this.branch=branch;
        this.section=section;
        this.year=year;
        this.sem=sem;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }
}
