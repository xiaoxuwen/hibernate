package com.hust.hibernate;

import com.hust.hibernate.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Administration on 2016/6/27.
 */
public class StudentTest {
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

        Student s = new Student();
        s.setName("张三");
        s.setAge(12);
        session.save(s);

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
