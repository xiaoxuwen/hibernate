package com.hust.hibernate.model;

import java.io.Serializable;

/**
 * Created by 38048 on 2016/10/11.
 */
public class WifePK implements Serializable {
    private int id;
    private String name;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WifePK wifePK = (WifePK) o;

        if (id != wifePK.id) return false;
        return name != null ? name.equals(wifePK.name) : wifePK.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
