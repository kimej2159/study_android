package com.example.my28_recyclerview3;

import java.io.Serializable;

public class SingerDTO  implements Serializable {
    String age , gender , address;
    int resId;

    public SingerDTO(String age, String gender, String address, int resId) {
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.resId = resId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
