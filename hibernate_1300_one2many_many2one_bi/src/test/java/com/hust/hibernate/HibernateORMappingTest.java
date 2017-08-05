package com.hust.hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * Created by Administration on 2016/6/27.
 */
public class HibernateORMappingTest {
    private static SessionFactory sf = null;

    //@BeforeClass
    public static void beforeClass() {
        Configuration cfg = new Configuration().configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sf = cfg.buildSessionFactory(serviceRegistry);
    }

    /**
     * 创建表结构，第一个true 表示在控制台打印sql语句，第二个true 表示导入sql语句到数据库
     */
    @Test
    public void testSchemaExport() {
        new SchemaExport(new Configuration().configure()).create(false, true);
    }

    //@AfterClass
    public static void afterClass() {
        if (sf != null) {
            sf.close();
        }
    }
}