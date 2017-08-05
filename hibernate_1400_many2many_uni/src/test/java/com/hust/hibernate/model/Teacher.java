package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 38048 on 2016/10/11.
 */
@Entity
@Table(name = "t_teacher")
public class Teacher {
    private int id;
    private String name;
    private Set<Student> students = new HashSet<Student>();

    @ManyToMany
    @JoinTable(name = "t_s", joinColumns = {@JoinColumn(name = "teacher_id")}, inverseJoinColumns = {@JoinColumn(name = "student_id")})
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
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
