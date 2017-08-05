package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Topic {
    private int id;
    private String title;
    private Date createDate;
    private Category category;
    private List<Msg> msgs = new ArrayList<Msg>();

    @OneToMany(mappedBy = "topic")
    public List<Msg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Msg> msgs) {
        this.msgs = msgs;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToOne
    @JoinColumn(name = "categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createDate=" + createDate +
                ", category=" + category +
                ", msgs=" + msgs +
                '}';
    }
}
