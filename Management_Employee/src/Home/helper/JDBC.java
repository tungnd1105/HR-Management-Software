package Home.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyNhanSu";
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

    /**
     * Xây dựng PreparedStatement
     *
     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
     * gọi thủ tục lưu
     * @param args là danh sách các giá trị được cung cấp cho các tham số trong
     * câu lệnh sql
     * @return PreparedStatement tạo được
     */
    public static PreparedStatement prepareStatement(String sql, Object... args) {
        PreparedStatement pstmt = null;
        try {
            connection = DriverManager.getConnection(dburl, username, password);
            if (sql.trim().startsWith("{")) {
                pstmt = connection.prepareCall(sql);
            } else {
                pstmt = connection.prepareStatement(sql);
            }
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pstmt;
    }

    /**
     * Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE, DELETE) hoặc thủ tục lưu
     * thao tác dữ liệu
     *
     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
     * gọi thủ tục lưu
     * @param args là danh sách các giá trị được cung cấp cho các tham số trong
     * câu lệnh sql
     *
     * @return số dòng được thực thi
     */
    public static int executeUpdate(String sql, Object... args) throws RuntimeException {
        int result = 0;
        try {
            PreparedStatement pstm = prepareStatement(sql, args);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            result = 0;
        }
        return result;
    }

    /**
     * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ
     * liệu
     *
     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
     * gọi thủ tục lưu
     * @param args là danh sách các giá trị được cung cấp cho các tham số trong
     * câu lệnh sql
     * @return kết quả câu lệnh truy vấn
     */
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement pstm = prepareStatement(sql, args);
            return pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Đóng kết nối
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
    }
}
