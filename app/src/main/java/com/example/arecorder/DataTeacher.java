package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataTeacher {



    String name;
    String department;
    String latitude;
    String longitude;
    String email;
    String id;
    String password;
    String joinyear;
    String contact;

    public DataTeacher(String id, DataSnapshot child){


    }

    public DataTeacher(String id,String name,String department,String latitude,String longitude,String email,String password,String joinyear,String contact) {
        this.id=id;
        this.name=name;
        this.department=department;
        this.latitude=latitude;
        this.longitude=longitude;
        this.email=email;
        this.password=password;
        this.joinyear=joinyear;
        this.contact=contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoinyear() {
        return joinyear;
    }

    public void setJoinyear(String joinyear) {
        this.joinyear = joinyear;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }



}
