package com.example.my10_intentresult;

import java.io.Serializable;

public class Student implements Serializable {
    String name;
    int age;
    //생성자

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
