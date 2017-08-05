package com.hust.hibernate;


import com.hust.hibernate.model.Student;
import com.hust.hibernate.model.Teacher;
import org.hibernate.Query;
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
        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGetCurrentSession() {
        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());

        Session s1 = sf.getCurrentSession();
        s1.beginTransaction();
        s1.save(t);

        Session s2 = sf.getCurrentSession();
        System.out.println("getCurrentSession是两个相同的session," + (s1 == s2));
        s1.getTransaction().commit();

        Session s3 = sf.getCurrentSession();
        System.out.println("session commit后,不是两个相同的session," + (s1 == s3));

    }

    /**
     * 三种状态的区分关键在于：
     * 1、有没有ID
     * 2、ID在数据库中有没有
     * 3、在内存中有没有（缓存）
     * transient:内存中一个对象，没有ID，缓存中也没有
     * persistent：内存中有，缓存中有，数据库中有ID
     * detached：内存有，缓存没有，数据有有ID
     */
    @Test
    public void testSaveWith3State() {
        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(t);

        System.out.println(t.getId());
        session.getTransaction().commit();

        System.out.println(t.getId());
    }

    @Test
    public void testDelete() {
        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());
        System.out.println(t.getId());

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(t);
        System.out.println(t.getId());
        session.getTransaction().commit();

        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.delete(t);
        session2.getTransaction().commit();
        System.out.println(t.getId());

        testLoad();
    }

    @Test
    public void testLoad() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.load(Teacher.class, 1);
        System.out.println(t);
        session.getTransaction().commit();
    }

    /**
     * get:直接从数据库加载，不会延迟
     * load:返回的是代理对象，等到真正用到对象的内容时才发出sql语句
     */
    @Test
    public void testGet() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.get(Teacher.class, 1);
        session.getTransaction().commit();
    }

    /**
     * detached状态的对象发生更新,更新完成后转换为persistent状态
     */
    @Test
    public void testUpdate1() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.get(Teacher.class, 1);
        session.getTransaction().commit();

        t.setName("张三");

        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.update(t);
        session2.getTransaction().commit();
    }

    /**
     * 用来更新transient状态对象会报错
     * 如果更新自己设定id的transient对象可以（数据库有对应记录）
     */
    @Test
    public void testUpdate2() {
        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
    }

    /**
     * persistent状态的对象只要设定不同字段就会发生更新
     */
    @Test
    public void testUpdate3() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.get(Teacher.class, 1);
        t.setName("李四");
        session.getTransaction().commit();

    }

    /**
     * 在xml文件中配置dynamic-update 可以动态更新要修改的字段
     */
    @Test
    public void testUpdate4() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Student s = (Student) session.get(Student.class, 1);
        s.setName("李四");
        session.getTransaction().commit();
    }

    /**
     * 如果跨session更新字段，还是发出更新全部字段sql语句
     */
    @Test
    public void testUpdate5() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Student s = (Student) session.get(Student.class, 1);
        s.setName("lisi");
        session.getTransaction().commit();

        s.setName("wangwu");
        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.update(s);
        session2.getTransaction().commit();
    }

    /**
     * 如果跨session更新字段，使用merge，还是会发出要修改字段的sql语句，不过会多发一条select语句
     */
    @Test
    public void testUpdate6() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Student s = (Student) session.get(Student.class, 1);
        s.setName("lisi");
        session.getTransaction().commit();

        s.setName("wangwu");
        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.merge(s);
        session2.getTransaction().commit();
    }

    /**
     * 推荐使用HQL语句修改一部分字段
     */
    @Test
    public void testUpdate7() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("update Student s set s.name='z5' where s.id=1");
        q.executeUpdate();
        session.getTransaction().commit();
    }

    /**
     * ①.对于刚创建的一个对象，如果session中和数据库中都不存在该对象，那么该对象就是瞬时对象(Transient)
     * <p>
     * ②.瞬时对象调用save方法，或者离线对象调用update方法可以使该对象变成持久化对象，如果对象是持久化对象时，那么对该对象的任何修改，都会在提交事务时才会与之进行比较，如果不同，则发送一条update语句，否则就不会发送语句
     * <p>
     * ③.离线对象就是，数据库存在该对象，但是该对象又没有被session所托管
     */
    @Test
    public void testSaveOrUpdate() {
        Teacher t = new Teacher();
        t.setName("老师");
        t.setDate(new Date());
        System.out.println(t.getId());

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();

        System.out.println(t.getId());
        t.setName("老师2");

        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.saveOrUpdate(t);
        session2.getTransaction().commit();
    }

    /**
     * 无论是get还是load，都会查找缓存(一级缓存)，如果没有才会去数据库中查找，clear()用于强制清除一级缓存
     */
    @Test
    public void testClear() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.load(Teacher.class, 1);
        System.out.println(t);

        session.clear();
        System.out.println(t);

        Teacher t1 = (Teacher) session.load(Teacher.class, 1);
        System.out.println(t1);
        session.getTransaction().commit();
    }

    /**
     * flush()可以强制从内存到数据库的同步
     * FlushMode的模式,用于调节性能(不需要研究)
     */
    @Test
    public void testFlush() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.load(Teacher.class, 1);
        t.setName("tttt");
        System.out.println(t);
        session.flush();
        //session.clear();
        t.setName("ttttt");
        System.out.println(t);
        session.getTransaction().commit();
    }

    /**
     * 用程序来建表 第1个boolean类型是说是否覆盖原有的表，第2个boolean是说，是否新建表
     */
    @Test
    public void testSchemaExport() {
        new SchemaExport(new Configuration().configure()).create(true, true);
    }

    @AfterClass
    public static void afterClass() {
        if (sf != null) {
            sf.close();
        }
    }
}