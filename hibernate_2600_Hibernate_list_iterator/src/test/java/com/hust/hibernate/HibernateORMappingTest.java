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
import java.util.Iterator;
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

    /**
     * 结论：
     * （1）从上面的执行结果可以看出获取的方式不一样
     * List的获取方式为：List<Customers> list = query.list();
     * Iterator的获取方式：Iterator<Customers> it = query.iterate();
     * （2）从执行结果可以看出list输出一条语句，而iterator输出的是两条sql语句，我们可想一下，为什么会输出这样的效果？
     * 因为他们获取数据的方式不一样，list()会直接查询数据库，iterator()会先到数据库中把id都取出来，然后真正要遍历某个对象的时候先到缓存中找，如果找不到，以id为条件再发一条sql到数据库，这样如果缓存中没有数据，则查询数据库的次数为n+1次
     * （3）list只查询一级缓存，而iterator会从二级缓存中查
     * （4）list方法返回的对象都是实体对象，而iterator返回的是代理对象
     * （5) session中list第二次发出，仍会到数据库査询，iterate 第二次，首先找session级缓存
     */
    @Test
    public void testQueryList() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        List<Category> categories = s.createQuery("from Category").list();
        for (Category c : categories) {
            System.out.println(c.getId() + "--" + c.getName());
        }

        List<Category> categories2 = s.createQuery("from Category").list();
        for (Category c : categories2) {
            System.out.println(c.getId() + "--" + c.getName());
        }
        s.getTransaction().commit();
    }

    @Test
    public void testQueryIterator() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Iterator<Category> categories = s.createQuery("from Category").iterate();
        while (categories.hasNext()) {
            Category c = categories.next();
            System.out.println(c.getId() + "--" + c.getName());
        }

        Iterator<Category> categories2 = s.createQuery("from Category").iterate();
        while (categories2.hasNext()) {
            Category c = categories2.next();
            System.out.println(c.getId() + "--" + c.getName());
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