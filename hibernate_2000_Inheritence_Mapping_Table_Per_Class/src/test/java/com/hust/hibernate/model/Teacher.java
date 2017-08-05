package com.hust.hibernate.model;

import javax.persistence.Entity;

/**
 * Created by 38048 on 2016/10/14.
 */
@Entity
public class Teacher extends Person {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "title='" + title + '\'' +
                '}';
    }
}
