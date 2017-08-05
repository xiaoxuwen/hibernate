package com.hust.hibernate.model;

import javax.persistence.*;

/**
 * Created by 38048 on 2016/10/14.
 * Hibernate支持三种基本的继承映射策略：
 * <p>
 * 每个类分层结构一张表(table per class hierarchy)
 * <p>
 * 每个子类一张表(table per subclass)
 * <p>
 * 每个具体类一张表(table per concrete class)
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("person")
public class Person {
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
