package com.hust.hibernate.model;

import javax.persistence.*;

/**
 * Created by 38048 on 2016/10/11.
 */
@Entity
@Table(name = "t_student")
public class Student {
    private int id;
    private String name;
    private Teacher group;

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
}
