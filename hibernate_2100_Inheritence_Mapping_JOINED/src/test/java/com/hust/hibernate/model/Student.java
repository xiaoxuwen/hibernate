package com.hust.hibernate.model;

import javax.persistence.Entity;

/**
 * Created by 38048 on 2016/10/14.
 */
@Entity
public class Student extends Person {
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "score=" + score +
                '}';
    }
}
