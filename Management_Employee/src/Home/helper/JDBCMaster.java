
package Home.helper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCMaster {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=master";
    private static String username = "sa";
    private static String password = "fpolyduan1";
    private static Connection connection;
    //Nạp driver
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static CallableStatement callableStatement(String sql, Object... args) {
        CallableStatement cstmt = null;
        try {
            connection = DriverManager.getConnection(dburl, username, password);   
            cstmt = connection.prepareCall(sql);
            for (int i = 0; i < args.length; i++) {
                cstmt.setObject(i + 1, args[i]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cstmt;
    }
    
    /**
     * Đóng kết nối
     */
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
    }
}
