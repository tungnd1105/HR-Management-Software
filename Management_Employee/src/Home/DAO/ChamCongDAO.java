package Home.DAO;

import Home.helper.Share;
import Home.helper.FormatNumber;
import Home.helper.JDBC;
import Home.helper.XDate;
import Home.model.ChamCong;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.Consumer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class ChamCongDAO {
    
    //Phuong thuc kiem tra tháng này đã chấm công chưa
    public boolean noRecordExist(int year, int month){
        boolean result = true;
        try {
            String sql = "{Call SP_FindCCByMonth(?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, year, month);
            while (rs.next()){
                result = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public int getSoGioLamViecTrongNam(int year) {
        int result = 0;
        try {
            String sql = "{call SP_SoGioLamViecTrongNam(?)}";
            ResultSet rs = JDBC.executeQuery(sql, year);
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int getSLNVDiLamDayDuTheoThang(int year, int month) {
        int result = 0;
        int ngayTrongThang = XDate.daysOfMonth(year, month);
        int soNgayLamViecThapNhat = Integer.min(26, XDate.countWorkingDaysInMonth(year, month));
        try {
            String sql = "{Call SP_ChuyenCanTheoThang(?,?,?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, Share.MAPB, year, month, soNgayLamViecThapNhat);
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public ObservableList<PieChart.Data> getDataForChuyenCanChart(int year, int month) {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        
        double tongSLNV = new NhanVienDAO().getSLNVTheoPBVaThang(Share.MAPB, year, month);
        int SLNVDiLamDayDu = new ChamCongDAO().getSLNVDiLamDayDuTheoThang(year, month);
        
        chartData.add(new PieChart.Data("Số lượng nhân viên đi làm đầy đủ", SLNVDiLamDayDu));
        chartData.add(new PieChart.Data("Số lượng nhân viên đi làm không đầy đủ", tongSLNV - SLNVDiLamDayDu));
        
        chartData.forEach(new Consumer<PieChart.Data>() {
            @Override
            public void accept(PieChart.Data data) {
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), ": ", FormatNumber.formatDouble(data.getPieValue()/tongSLNV*100), "%"));
            }
        });
        
        return chartData;
    }
    
    public ObservableList<PieChart.Data> getDataForSoNgayLamViecChart(int year, int month) {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        
        double ngayTrongThang = XDate.daysOfMonth(year, month);
        int soNgayLe = XDate.holidaysInMonth(year, month);
        
        chartData.add(new PieChart.Data("Số ngày làm việc trong tháng", ngayTrongThang - soNgayLe));
        chartData.add(new PieChart.Data("Số ngày lễ và Chủ nhật", soNgayLe));
        
        chartData.forEach(new Consumer<PieChart.Data>() {
            @Override
            public void accept(PieChart.Data data) {
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), ": ", FormatNumber.formatDouble(data.getPieValue()/ngayTrongThang*100), "%"));
            }
        });
        
        return chartData;
    }
    
    public ObservableList<Integer> getListYear(){
        ObservableList<Integer> list = FXCollections.observableArrayList();
        try {
            String sql = "{Call SP_ListYearCC}";
            ResultSet rs = JDBC.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public int insert(ChamCong cc){
        int result = 0;
        try {
            String sql = "{Call SP_ChamCong(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            result = JDBC.executeUpdate(sql, cc.getMaNV(), XDate.toSqlDate(cc.getNgayDauThang()), 
                    cc.getNgay1(), cc.getNgay2(), cc.getNgay3(), cc.getNgay4(), cc.getNgay5(), cc.getNgay6(), cc.getNgay7(), 
                    cc.getNgay8(), cc.getNgay9(), cc.getNgay10(), cc.getNgay11(), cc.getNgay12(), cc.getNgay13(), cc.getNgay14(), 
                    cc.getNgay15(), cc.getNgay16(), cc.getNgay17(), cc.getNgay18(), cc.getNgay19(), cc.getNgay20(), cc.getNgay21(), 
                    cc.getNgay22(), cc.getNgay23(), cc.getNgay24(), cc.getNgay25(), cc.getNgay26(), cc.getNgay27(), cc.getNgay28(), 
                    cc.getNgay29(), cc.getNgay30(), cc.getNgay31(),
                    "Insert");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public int update(String maNV, String ngay, boolean tinhTrang, Date ngayDauThang){
        int result = 0;
        try {
            String sql = "UPDATE ChamCong SET " + ngay + " = ? WHERE MaNV = ? AND NgayDauThang = ?";
            result = JDBC.executeUpdate(sql, tinhTrang, maNV, XDate.toSqlDate(ngayDauThang));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public int delete(ChamCong cc){
        int result = 0;
        try {
            String sql = "{Call SP_ChamCong(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            result = JDBC.executeUpdate(sql, cc.getMaNV(), XDate.toSqlDate(cc.getNgayDauThang()), 
                    cc.getNgay1(), cc.getNgay2(), cc.getNgay3(), cc.getNgay4(), cc.getNgay5(), cc.getNgay6(), cc.getNgay7(), 
                    cc.getNgay8(), cc.getNgay9(), cc.getNgay10(), cc.getNgay11(), cc.getNgay12(), cc.getNgay13(), cc.getNgay14(), 
                    cc.getNgay15(), cc.getNgay16(), cc.getNgay17(), cc.getNgay18(), cc.getNgay19(), cc.getNgay20(), cc.getNgay21(), 
                    cc.getNgay22(), cc.getNgay23(), cc.getNgay24(), cc.getNgay25(), cc.getNgay26(), cc.getNgay27(), cc.getNgay28(), 
                    cc.getNgay29(), cc.getNgay30(), cc.getNgay31(),
                    "Delete");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
