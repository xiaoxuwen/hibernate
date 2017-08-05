package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 38048 on 2016/10/14.
 */
@Entity
public class Org {
    private int id;
    private String name;
    private Set<Org> childrens = new HashSet<Org>();
    private Org parent;

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

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    public Set<Org> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<Org> childrens) {
        this.childrens = childrens;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Org getParent() {
        return parent;
    }

    public void setParent(Org parent) {
        this.parent = parent;
    }
}
