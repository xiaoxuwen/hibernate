import com.hust.hibernate.model.Student;

/**
 * Created by Administration on 2016/6/28.
 */
public class StudentTest {
    public static void main(String[] args) throws Exception {
        Student s = new Student();
        s.setName("zhangsan");
        s.setAge(12);
        Session session = new Session();
        session.save(s);
    }
}
