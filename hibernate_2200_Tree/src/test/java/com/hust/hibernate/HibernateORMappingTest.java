package com.hust.hibernate;


import com.hust.hibernate.model.Org;
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
        Org o = new Org();
        o.setName("总公司");
        Org o1 = new Org();
        o1.setName("分公司1");
        o1.setParent(o);
        Org o2 = new Org();
        o2.setName("分公司2");
        o2.setParent(o);
        Org o11 = new Org();
        o11.setName("分公司1下的部门1");
        o11.setParent(o1);
        Org o12 = new Org();
        o12.setName("分公司1下的部门2");
        o12.setParent(o1);

        o.getChildrens().add(o1);
        o.getChildrens().add(o2);
        o1.getChildrens().add(o11);
        o1.getChildrens().add(o12);

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        s.save(o);
        s.getTransaction().commit();
    }

    @Test
    public void testLoad() {
        testSave();
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Org o = (Org) s.load(Org.class, 1);
        print(o, 0);
        s.getTransaction().commit();
    }

    private void print(Org o, int level) {
        String preStr = "";
        for (int i = 0; i < level; i++) {
            preStr += "----";
        }
        System.out.println(preStr + o.getName());
        for (Org child : o.getChildrens()) {
            print(child, level + 1);
        }
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