package com.hust.hibernate.model;

import javax.persistence.*;

/**
 * Created by Administration on 2016/6/27.
 */
@Entity
@Table(name = "teacher")
public class Teacher {
    private int id;
    private String name;
    private String title;

    public Teacher() {
    }

    public Teacher(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    @Id
    @GeneratedValue
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

    @Transient
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
