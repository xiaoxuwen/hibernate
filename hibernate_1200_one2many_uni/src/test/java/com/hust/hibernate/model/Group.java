package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 38048 on 2016/10/11.
 */
@Entity
@Table(name = "t_group")
public class Group {
    private int id;
    private String name;
    private Set<User> users = new HashSet<User>();

    @OneToMany
    @JoinColumn(name = "g_id")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
