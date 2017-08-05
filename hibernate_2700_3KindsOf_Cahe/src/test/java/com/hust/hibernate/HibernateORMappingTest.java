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
            t.setTitle("topic" + i);
            t.setCreateDate(new Date());
            s.save(c);
            s.save(t);
        }
        s.getTransaction().commit();
    }


//      一级缓存：一级缓存是hibernate自带的，不受用户干预，其生命周期和session的生命周期一致，
//      当前session一旦关闭，一级缓存就会消失，因此，一级缓存也叫session缓存或者事务级缓存，
//      一级缓存只存储实体对象，不会缓存一般的对象属性，即：当获得对象后，就将该对象缓存起来，
//      如果在同一个session中再去获取这个对象时，它会先判断缓存中有没有这个对象的ID，
//      如果有，就直接从缓存中取出，否则，则去访问数据库，取了以后同时会将这个对象缓存起来
//      二级缓存：
//      二级缓存也称为进程缓存或者sessionFactory级的缓存，它可以被所有的session共享，
//      二级缓存的生命周期和sessionFactory的生命周期一致，二级缓存也是只存储实体对象。
//      二级缓存的一般过程如下：
//      ①：条件查询的时候，获取查询到的实体对象
//      ②：把获得到的所有数据对象根据ID放到二级缓存中
//      ③：当Hibernate根据ID访问数据对象时，首先从session的一级缓存中查，查不到的时候如果配置了二级缓存，会从二级缓存中查找，如果还查不到，再查询数据库，把结果按照ID放入到缓存中
//      ④：进行delete、update、add操作时会同时更新缓存
//      <p>
//      查询缓存：
//      查询缓存是对普通属性结果集的缓存，对实体对象的结果集只缓存id，对于经常使用的查询语句，
//      如果启用了查询缓存，当第一次执行查询语句时，Hibernate会把查询结果存放在二级缓存中，
//      以后再次执行该查询语句时，只需从缓存中获得查询结果，从而提高查询性能，
//      查询缓存中以键值对的方式存储的，key键为查询的条件语句（具体的key规则应该是：类名+方法名+参数列表），
//      value为查询之后等到的结果集的ID列表。
//      查询缓存的一般过程如下：
//      ①：Query Cache保存了之前查询执行过的SelectSQL，以及结果集等信息组成一个Query Key
//      ②：当再次遇到查询请求的时候，就会根据QueryKey从QueryCache中找，找到就返回，但当数据表发生数据变动的话，
//      hibernate就会自动清除QueryCache中对应的Query Key 我们从查询缓存的策略中可以看出，Query Cache只有在特定的条件下才会发挥作用，而且要求相当严格：
//      ①：完全相同的SelectSQL重复执行
//      ②：重复执行期间，QueryKey对应的数据表不能有数据变动


    //load默认使用二级缓存,iterate默认使用二级缓存
    //list默认往二级缓存加数据，但是在查询的时候不能用


//    在配置文件中配置查询缓存和二级缓存
//     <!--指定二级缓存产品的提供商 -->
//    <propertyname="hibernate.cache.provider_class" value="net.sf.ehcache.hibernate.SingletonEhCacheProvider"/>
//    <!--<propertyname="hibernate.cache.region.factory_class" value="org.hibernate.cache.ehcache.EhCacheRegionFactory"/>-->
//    <!--开启二级缓存 -->
//    <propertyname="hibernate.cache.use_second_level_cache" value="true"/>
//    <!--开启查询缓存 -->
//    <propertyname="hibernate.cache.use_query_cache" value="true"/>
//    <!--指定缓存配置文件位置   -->
//    <propertyname="hibernate.cache.provider_configuration_file_resource_path" value="/ehcache.xml"/>


    @Test
    public void testQuery1() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Category c = (Category) s.load(Category.class, 1);
        System.out.println(c.getName());

        Category c2 = (Category) s.load(Category.class, 1);
        System.out.println(c2.getName());
        s.getTransaction().commit();
    }


    @Test
    public void testQuery2() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        Category c = (Category) s.load(Category.class, 1);
        System.out.println(c.getName());
        s.getTransaction().commit();

        Session s2 = sf.getCurrentSession();
        s2.beginTransaction();
        Category c2 = (Category) s2.load(Category.class, 1);
        System.out.println(c2.getName());
        s2.getTransaction().commit();
    }

    /**
     * 加上查询缓存后，list查询发一条sql语句
     */
    @Test
    public void testQueryCache() {
        Session s = sf.getCurrentSession();
        s.beginTransaction();
        //同一个session中
        List<Category> categorys = s.createQuery("from Category").setCacheable(true).list();
        List<Category> categorys2 = s.createQuery("from Category").setCacheable(true).list();
        s.getTransaction().commit();

        Session s2 = sf.getCurrentSession();
        s2.beginTransaction();
        //跨session
        List<Category> categorys3 = s2.createQuery("from Category").setCacheable(true).list();
        s2.getTransaction().commit();
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