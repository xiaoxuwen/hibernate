package com.hust.hibernate;


import com.hust.hibernate.model.Course;
import com.hust.hibernate.model.Score;
import com.hust.hibernate.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void testSave() {
        Student s = new Student();
        s.setName("s1");
        Course c = new Course();
        c.setName("c1");
        Score score = new Score();
        score.setStudent(s);
        score.setCourse(c);
        score.setScore(80);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(s);
        session.save(c);
        session.save(score);
        session.getTransaction().commit();
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