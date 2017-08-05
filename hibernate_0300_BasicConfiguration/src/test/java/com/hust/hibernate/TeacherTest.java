package com.hust.hibernate;


import com.hust.hibernate.model.Teacher;
import com.hust.hibernate.model.TeacherType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Administration on 2016/6/27.
 */
public class TeacherTest {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        Configuration cfg = new Configuration().configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sf = cfg.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void save() {
        Session session = sf.openSession();
        session.beginTransaction();

        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());
        t.setType(TeacherType.A);
        session.save(t);

        session.getTransaction().commit();
        session.close();
    }

    @AfterClass
    public static void afterClass() {
        if (sf != null) {
            sf.close();
        }
    }
}