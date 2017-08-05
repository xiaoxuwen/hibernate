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
    private Group group;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "g_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
}
