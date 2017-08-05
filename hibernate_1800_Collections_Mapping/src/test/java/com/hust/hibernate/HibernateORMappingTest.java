package com.hust.hibernate;


import com.hust.hibernate.model.Group;
import com.hust.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administration on 2016/6/27.
 */
public class HibernateORMappingTest {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        Configuration cfg = new Configuration().configure();
        new SchemaExport(cfg).create(false, true);
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sf = cfg.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void testLoadGroup() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Group g = (Group) s.get(Group.class, 1);
        for (Map.Entry<Integer, User> entry : g.getUsers().entrySet()) {
            System.out.println(entry.getValue().getName());
        }
        s.getTransaction().commit();
    }

    @AfterClass
    public static void afterClass() {
        if (sf != null) {
            sf.close();
        }
    }

    public static void main(String[] args) {
        beforeClass();
    }
}