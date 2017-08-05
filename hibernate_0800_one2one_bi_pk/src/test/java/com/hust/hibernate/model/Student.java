package com.hust.hibernate.model;

/**
 * Created by Administration on 2016/6/27.
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private StuIdCard stuIdCard;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public StuIdCard getStuIdCard() {
        return stuIdCard;
    }

    public void setStuIdCard(StuIdCard stuIdCard) {
        this.stuIdCard = stuIdCard;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
