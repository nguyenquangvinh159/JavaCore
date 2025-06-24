package util;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DBConnect {
    private static Connection conn;

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            Properties props = new Properties();

            InputStream input = DBConnect.class.getClassLoader().getResourceAsStream("config/db.properties");
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file config/db.properties");
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            if (user == null || pass == null || user.isEmpty()) {
                throw new RuntimeException("Lỗi: Thiếu user hoặc password trong db.properties");
            }

            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }
}
