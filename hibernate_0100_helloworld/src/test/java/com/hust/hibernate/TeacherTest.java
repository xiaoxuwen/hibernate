package com.hust.hibernate;

import com.hust.hibernate.model.Teacher;
import com.hust.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by Administration on 2016/6/27.
 */
public class TeacherTest {
    @Test
    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Teacher t = new Teacher();
        t.setName("老师");
        session.save(t);

        session.getTransaction().commit();
        session.close();
    }
}