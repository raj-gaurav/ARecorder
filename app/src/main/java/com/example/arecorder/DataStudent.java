package com.example.arecorder;

import com.google.firebase.database.DataSnapshot;

public class DataStudent {
    String name;
    String department;
    String latitude;



    String longitude;
    String email;
    String section;
    String classroll;
    String registration;
    String id;
    String password;
    String joinyear;
    String contact;

    public DataStudent(String id, DataSnapshot child){


    }

    public DataStudent(String id,String name,String department,String section,String latitude,String longitude,String email,String password,String joinyear,String contact,String classroll,String registration) {
        this.id=id;
        this.name=name;
        this.department=department;
        this.section=section;
        this.latitude=latitude;
        this.longitude=longitude;
        this.email=email;
        this.password=password;
        this.joinyear=joinyear;
        this.contact=contact;
        this.classroll=classroll;
        this.registration=registration;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getClassroll() {
        return classroll;
    }

    public void setClassroll(String classroll) {
        this.classroll = classroll;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
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
