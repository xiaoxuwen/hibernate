package com.hust.hibernate;

import com.hust.hibernate.model.Student;
import com.hust.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by Administration on 2016/6/27.
 */
public class StudentTest {

    @Test
    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Student s = new Student();
        s.setName("张三");
        s.setAge(12);
        session.save(s);

        session.getTransaction().commit();
        session.close();
    }

}
