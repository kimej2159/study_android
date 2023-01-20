package com.example.my26_recyclerview;

import java.io.Serializable;

public class SingerDTO implements Serializable {
    String name, moblie;
    int age, resID;

    public SingerDTO(String name, String moblie, int age, int resID) {
        this.name = name;
        this.moblie = moblie;
        this.age = age;
        this.resID = resID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}

