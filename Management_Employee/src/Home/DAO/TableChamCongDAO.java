package Home.DAO;

import Home.helper.JDBC;
import Home.helper.XDate;
import Home.model.table.TableChamCong;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableChamCongDAO {

//    public ObservableList<TableChamCong> getData(int year, int month, String timKiem) {
//        ObservableList<TableChamCong> data = FXCollections.observableArrayList();
//        ObservableList<NhanVien> listNhanVien = new NhanVienDAO().findByMonth(year, month, timKiem);
//
//        for (NhanVien nv : listNhanVien) {
//            try {
//                Boolean[] onwork = new Boolean[31];
//                Arrays.fill(onwork, Boolean.FALSE);
//                String sql = "{Call SP_ChamCongTheoThang(?,?,?)}";
//                ResultSet rs = JDBC.executeQuery(sql, nv.getMaNV(), year, month);
//                while (rs.next()) {
//                    String dayOfMonth = rs.getString(1).substring(8, 10);
//                    onwork[Integer.parseInt(dayOfMonth) - 1] = rs.getBoolean(2);
//                }
//                TableChamCong tableChamCong = new TableChamCong(nv.getMaNV(), nv.getHoTen(),
//                        new PhongBanDAO().findByCode(nv.getMaPB()).get(0), year + "", month + "", onwork);
//                data.add(tableChamCong);
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        return data;
//    }
    public ObservableList<TableChamCong> getData(Date ngayDauThang, String timKiem) {
        ObservableList<TableChamCong> data = FXCollections.observableArrayList();

        try {
            String sql = "{Call SP_TBLChamCong(?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, XDate.toSqlDate(ngayDauThang), timKiem);
            while (rs.next()) {
                TableChamCong tableChamCong = new TableChamCong(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getBoolean(6), rs.getBoolean(7), rs.getBoolean(8), rs.getBoolean(9), rs.getBoolean(10),
                        rs.getBoolean(11), rs.getBoolean(12), rs.getBoolean(13), rs.getBoolean(14), rs.getBoolean(15),
                        rs.getBoolean(16), rs.getBoolean(17), rs.getBoolean(18), rs.getBoolean(19), rs.getBoolean(20),
                        rs.getBoolean(21), rs.getBoolean(22), rs.getBoolean(23), rs.getBoolean(24), rs.getBoolean(25),
                        rs.getBoolean(26), rs.getBoolean(27), rs.getBoolean(28), rs.getBoolean(29), rs.getBoolean(30),
                        rs.getBoolean(31), rs.getBoolean(32), rs.getBoolean(33), rs.getBoolean(34), rs.getBoolean(35),
                        rs.getBoolean(36));
                data.add(tableChamCong);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
