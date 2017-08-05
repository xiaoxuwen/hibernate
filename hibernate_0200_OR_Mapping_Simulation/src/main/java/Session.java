import com.hust.hibernate.model.Student;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administration on 2016/6/28.
 */
public class Session {
    String tableName = "_Student";
    Map<String, String> cfs = new HashMap<String, String>();
    String[] methodNames;

    public Session() {
        cfs.put("_id", "id");
        cfs.put("_name", "name");
        cfs.put("_age", "age");
        methodNames = new String[cfs.size()];
    }


    public void save(Student s) throws Exception {
        String sql = createSQL();
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate", "root", "root");
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < methodNames.length; i++) {
            Method method = s.getClass().getMethod(methodNames[i]);
            Class r = method.getReturnType();
            if (r.getName().equals("java.lang.String")) {
                String returnValue = (String) method.invoke(s);
                ps.setString(i + 1, returnValue);
            } else if (r.getName().equals("int")) {
                Integer returnValue = (Integer) method.invoke(s);
                ps.setInt(i + 1, returnValue);
            }
            System.out.println(method.getName() + "|" + method.getReturnType());
        }
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    private String createSQL() {
        String str1 = "";
        int index = 0;
        for (String s : cfs.keySet()) {
            String value = cfs.get(s);
            value = Character.toUpperCase(value.charAt(0)) + value.substring(1);
            methodNames[index] = "get" + value;
            str1 += s + ",";
            index++;
        }
        str1 = str1.substring(0, str1.length() - 1);
        System.out.println(str1);
        String str2 = "";
        for (int i = 0; i < cfs.size(); i++) {
            str2 += "?,";
        }
        str2 = str2.substring(0, str2.length() - 1);
        System.out.println(str2);
        String sql = "insert into " + tableName + "(" + str1 + ")" + " values (" + str2 + ")";
        System.out.println(sql);
        return sql;
    }
}
