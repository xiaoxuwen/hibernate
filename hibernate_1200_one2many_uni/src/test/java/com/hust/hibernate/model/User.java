package com.hust.hibernate.model;

import javax.persistence.*;

/**
 * Created by 38048 on 2016/10/11.
 */
@Entity
@Table(name = "t_user")
public class User {
    private int id;
    private String name;

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
