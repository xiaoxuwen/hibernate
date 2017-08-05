package com.hust.hibernate.model;

/**
 * Created by 38048 on 2016/10/9.
 */
public class StuIdCard {
    private int id;
    private long num;
    private Student student;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
