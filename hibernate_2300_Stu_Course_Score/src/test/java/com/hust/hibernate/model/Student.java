package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 38048 on 2016/10/14.
 */
@Entity
public class Student {
    private int id;
    private String name;
    private Set<Course> courses = new HashSet<Course>();

    @ManyToMany
    @JoinTable(name = "score",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
