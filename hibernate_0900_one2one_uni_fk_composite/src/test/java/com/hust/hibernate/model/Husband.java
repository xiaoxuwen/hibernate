package com.hust.hibernate.model;

import javax.persistence.*;

/**
 * Created by 38048 on 2016/10/9.
 */
@Entity
public class Husband {
    private int id;
    private String name;
    private Wife wife;

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

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "wifeId", referencedColumnName = "id"),
            @JoinColumn(name = "wifeName", referencedColumnName = "name")
    })
    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
