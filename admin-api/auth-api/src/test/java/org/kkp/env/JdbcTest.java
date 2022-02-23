package org.kkp.env;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Klaus
 * @since 2022/2/23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTest {

    @Test
    public void test() {
        String url = "jdbc:mysql://localhost:3306?ry-vue";
        String username = "root";
        String password = "123456";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
