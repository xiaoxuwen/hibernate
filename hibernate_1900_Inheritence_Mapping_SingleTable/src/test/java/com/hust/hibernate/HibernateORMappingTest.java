package com.hust.hibernate;


import com.hust.hibernate.model.Student;
import com.hust.hibernate.model.Teacher;
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
        Student student = new Student();
        student.setName("s1");
        student.setScore(80);
        Teacher t = new Teacher();
        t.setName("t1");
        t.setTitle("中级");
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        s.save(student);
        s.save(t);
        s.getTransaction().commit();
    }

    @Test
    public void testLoad() {
        testSave();
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Student student = (Student) s.load(Student.class, 1);
        System.out.println(student);
        Teacher teacher = (Teacher) s.load(Teacher.class, 2);
        System.out.println(teacher);
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