package com.hust.hibernate.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administration on 2016/6/27.
 */
@Entity
@Table(name = "teacher")
public class Teacher {
    private int id;
    private String name;
    private String title;
    private Date date;
    private TeacherType type;

    public Teacher() {
    }

    public Teacher(int id, String name, String title, Date date) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.date = date;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //给字段那么起别名_name
    @Column(name = "_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //透明，表示该字段不需要加入数据库
    @Transient
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //默认TIMESTAMP:记录日期和时间,还有DATE：日期和TIME：时间
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //  枚举类型STRING:字符串 ORDINAL:枚举类型的下标
    @Enumerated(EnumType.STRING)
    public TeacherType getType() {
        return type;
    }

    public void setType(TeacherType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
