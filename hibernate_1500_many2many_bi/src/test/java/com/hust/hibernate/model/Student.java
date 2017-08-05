package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 38048 on 2016/10/11.
 */
@Entity
@Table(name = "t_student")
public class Student {
    private int id;
    private String name;
    private Set<Teacher> teachers = new HashSet<Teacher>();

    @ManyToMany(mappedBy = "students")
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
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
