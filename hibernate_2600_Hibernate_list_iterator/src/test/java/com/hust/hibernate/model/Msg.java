package com.hust.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Msg {
    private int id;
    private String cont;
    private Topic topic;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicId")
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", cont='" + cont + '\'' +
                ", topic=" + topic +
                '}';
    }
}
