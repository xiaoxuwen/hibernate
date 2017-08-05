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
    public void testSaveUser() {
        User u = new User();
        u.setName("u1");
        Group g = new Group();
        g.setName("g1");
        u.setGroup(g);
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        //在User上设置cascade，这样可以在添加u的同时关联到g，CascadeType.ALL:表示增删改都会级联
        //s.save(g);
        s.save(u);
        s.getTransaction().commit();
    }

    @Test
    public void testSaveGroup() {
        User u1 = new User();
        u1.setName("u1");
        User u2 = new User();
        u2.setName("u2");
        Group g = new Group();
        g.setName("g1");
        g.getUsers().add(u1);
        g.getUsers().add(u2);
        u1.setGroup(g);
        u2.setGroup(g);
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        //在Group上设置cascade，这样可以在添加g的同时关联到u，CascadeType.ALL:表示增删改都会级联
        s.save(g);
        s.getTransaction().commit();
    }

    @Test
    public void testGetUser() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        //在取出User数据的同时也会取出Group的数据，可以取出one的数据，fetch默认值EAGER
        //如果不想取出one一方的数据，可以设置fetch为LAZY
        User u = (User) s.get(User.class, 1);
        //设置fetch为LAZY的时候，如果用到Group的数据，会再发一条查询数据，取出Group
        System.out.println(u.getGroup().getName());
        s.getTransaction().commit();
    }

    @Test
    public void testGetGroup() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        //在取出Group数据的同时不会取出User的数据，不会取出many一方的数据，fetch默认值LAZY
        //如果想取出many一方的数据,可以设置fetch为EAGER
        Group g = (Group) s.get(Group.class, 1);
        s.getTransaction().commit();
        for (User u : g.getUsers()) {
            System.out.println(u.getName());
        }
    }

    @Test
    public void testLoadUser() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        User u = (User) s.load(User.class, 1);
        //User的fetch为LAZY的时候，会先发一条sql语句取出User，再发出一条sql语句取出Group和User,
        //原因是User的fetch为LAZY，会发出User的查询，Group的fetch也为EAGER，会在发出GROUP和User的查询

        //User的fetch为EAGER的时候，会先发出一条sql语句取出Group和User,再发一条sql语句取出User,
        //原因是User的fetch为EAGER，会发出User和Group的级联，Group的fetch也为EAGER，会在发出User的查询，

        //因此，需要注意：不要在双向关联中都设置EAGER
        System.out.println(u.getGroup().getName());
        s.getTransaction().commit();
    }

    @Test
    public void testUpdateUser() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        User u = (User) s.load(User.class, 1);
        u.setName("user");
        u.getGroup().setName("gggg");
        s.getTransaction().commit();
    }

    @Test
    public void testUpdate2User() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        User u = (User) s.get(User.class, 1);
        s.getTransaction().commit();
        u.setName("user");
        u.getGroup().setName("gggg");
        Session s2 = sf.getCurrentSession();
        s2.beginTransaction();
        s2.update(u);
        s2.getTransaction().commit();
    }

    @Test
    public void testDeleteUser() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        User u = (User) s.get(User.class, 1);
        //删除User的时候，由于User的cascade = {CascadeType.ALL}：会把User相关联的Group也删除掉
        //解决办法1：打破关联u.setGroup(null);
        //解决办法2：HQL语句（推荐）s.createQuery("delete from User u where u.id=1").executeUpdate;
        s.delete(u);
        s.getTransaction().commit();
    }

    @Test
    public void testDeleteGroup() {
        testSaveGroup();

        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Group g = (Group) s.get(Group.class, 1);
        //删除Group的时候，由于Group的cascade = {CascadeType.ALL}：会把Group相关联的User也删除掉
        //往往one的一方删除，会把many的一方也删除
        //解决办法1：打破关联
        //解决办法2：HQL语句(推荐)
        s.delete(g);
        s.getTransaction().commit();
    }

    @AfterClass
    public static void afterClass() {
        if (sf != null) {
            sf.close();
        }
    }
}