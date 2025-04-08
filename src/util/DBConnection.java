package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
    private static Connection conn = null;

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            Properties props = new Properties();
            try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new RuntimeException("Không tìm thấy file cấu hình config.properties");
                }

                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String pass = props.getProperty("db.password");

                // Nạp driver MSSQL cho jre11
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(url, user, pass);
            }
        }
        return conn;
    }
}


