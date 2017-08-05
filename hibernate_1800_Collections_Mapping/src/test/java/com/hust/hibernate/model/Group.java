package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 38048 on 2016/10/11.
 */
@Entity
@Table(name = "t_group")
public class Group {
    private int id;
    private String name;
/*    private List<User> users = new ArrayList<User>();


    @OrderBy(value = "name asc")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }*/

    private Map<Integer, User> users = new HashMap<Integer, User>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapKey(name = "id")
    public Map<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(Map<Integer, User> users) {
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
