package com.hust.hibernate;


import com.hust.hibernate.model.Category;
import com.hust.hibernate.model.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Administration on 2016/6/27.
 */
public class HibernateORMappingTest {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        Configuration cfg = new Configuration().configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sf = cfg.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void testSchemaExport() {
        new SchemaExport(new Configuration().configure()).create(true, true);
    }

    @Test
    public void testSave() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        for (int i = 0; i < 10; i++) {
            Category c = new Category();
            c.setName("c" + i);
            Topic t = new Topic();
            t.setCategory(c);
            t.setTitle("t" + i);
            t.setCreateDate(new Date());
            s.save(c);
            s.save(t);
        }
        s.getTransaction().commit();
    }

    //      N+1问题 一个对象关联了另一个对象，同时FetchType为EAGER,典型的ManyToOne
//      解决方案一：fetch设为lazy
//      解决方案二：join fetch List<Topic> topics = s.createCriteria(Topic.class).list();
//      解决方案三：BatchSize,在Entity上加@BatchSize
    @Test
    public void testQuery1() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        List<Topic> topics = s.createQuery("from Topic").list();

        for (Topic t : topics) {
            System.out.println(t.getId() + "--" + t.getTitle());
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