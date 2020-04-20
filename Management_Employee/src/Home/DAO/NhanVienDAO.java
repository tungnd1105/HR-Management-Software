package Home.DAO;

import Home.helper.FormatNumber;
import Home.helper.Share;
import Home.helper.JDBC;
import Home.helper.XDate;
import Home.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class NhanVienDAO {

    public NhanVien findByCode(String code) {
        NhanVien nhanVien = null;
        try {
            String sql = "{Call SP_FindNVByCode(?)}";
            ResultSet rs = JDBC.executeQuery(sql, code);
            while (rs.next()) {
                nhanVien = new NhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getDate(14), rs.getDate(15), rs.getDouble(16), rs.getBoolean(17));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nhanVien;
    }

    public NhanVien findbyCMND(String cmnd) {
        NhanVien nhanVien = null;
        try {
            String sql = "{Call SP_FindNVByCMND(?)}";
            ResultSet rs = JDBC.executeQuery(sql, cmnd);
            while (rs.next()) {
                nhanVien = new NhanVien(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhanVien;
    }

    public NhanVien findbyMaHD(String MaHD) {
        NhanVien nhanVien = null;
        try {
            String sql = "{Call SP_FindNVByMaHD(?)}";
            ResultSet rs = JDBC.executeQuery(sql, MaHD);
            while (rs.next()) {
                nhanVien = new NhanVien(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhanVien;
    }

    public ObservableList<NhanVien> findByMaPB(String maPB) {
        ObservableList<NhanVien> list = FXCollections.observableArrayList();

        try {
            String sql = "{Call SP_FindNVTheoPB(?)}";
            ResultSet rs = JDBC.executeQuery(sql, maPB);
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getDate(14), rs.getDate(15), rs.getDouble(16), rs.getBoolean(17));
                list.add(nv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ObservableList<NhanVien> findNVStartWorkingInMonth(int year, int month) {
        ObservableList<NhanVien> list = FXCollections.observableArrayList();

        try {
            String sql = "{Call SP_FindNVStartWorkingInMonth(?,?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, Share.MAPB, year, month);
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getDate(14), rs.getDate(15), rs.getDouble(16), rs.getBoolean(17));
                list.add(nv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int getSLNVTheoPhongBan(Object maPB) {
        int SLNhanVien = 0;
        try {
            String sql = "{call SP_SLNVTheoPB(?)}";
            ResultSet rs = JDBC.executeQuery(sql, maPB);
            while (rs.next()) {
                SLNhanVien += rs.getInt(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return SLNhanVien;
    }

    public int getSLNVTheoPBVaNam(int year) {
        int SLNhanVien = 0;
        try {
            String sql = "{call SP_SLNVTheoPBVaNam(?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, (Object) null, year);
            while (rs.next()) {
                SLNhanVien += rs.getInt(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return SLNhanVien;
    }

    public ObservableList getSLNVTheoPBVaThang(String MaPB, int year) {
        ObservableList data = FXCollections.observableArrayList();
        int month = XDate.monthOfYear(year);
        for (int i = 1; i <= month; i++) {
            try {
                String sql = "{call SP_SLNVTheoPBVaThang(?,?,?)}";
                ResultSet rs = JDBC.executeQuery(sql, MaPB, i + "", year + "");
                while (rs.next()) {
                    data.add(new XYChart.Data("Tháng " + i, rs.getInt(1)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }

    public int getSLNVTheoPBVaThang(Object MaPB, int year, int month) {
        int result = 0;
        try {
            String sql = "{call SP_SLNVTheoPBVaThang(?,?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, MaPB, month, year);
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public ObservableList<NhanVien> findByMonth(int year, int month, String timKiem) {
        ObservableList<NhanVien> list = FXCollections.observableArrayList();

        try {
            String sql = "{Call SP_FindNVByMonth(?,?,?,?)}";
            ResultSet rs = JDBC.executeQuery(sql, Share.MAPB, year, month, timKiem);
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getDate(14), rs.getDate(15), rs.getDouble(16), rs.getBoolean(17));
                list.add(nv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ObservableList<PieChart.Data> getDataForPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String sql = "{call SP_SLNamNu(?)}";
            ResultSet rs = JDBC.executeQuery(sql, Share.MAPB);
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getBoolean(1) ? "Nam" : "Nữ", rs.getInt(2)));
            }
            if (data.size() < 2) {
                if (data.get(0).getName().equals("Nam")) {
                    data.add(new PieChart.Data("Nữ", 0));
                }
                if (data.get(0).getName().equals("Nữ")) {
                    data.add(new PieChart.Data("Nam", 0));
                }
            }
            data.forEach(new Consumer<PieChart.Data>() {
                @Override
                public void accept(PieChart.Data data) {
                    double soLuong = new NhanVienDAO().getSLNVTheoPhongBan(Share.MAPB);
                    data.nameProperty().bind(Bindings.concat(data.getName(), ": ",
                            FormatNumber.formatDouble(data.getPieValue() / soLuong * 100), "%"));
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public XYChart.Series getDataForBarChart() {
        XYChart.Series data = new XYChart.Series<>();
        double SLNV = 0; //tong so luong nhan vien
        int count = 0; //So luong phong ban
        try {
            String sql = "{call SP_SLNVTheoPB(?)}";
            ResultSet rs = JDBC.executeQuery(sql, (Object) null);
            while (rs.next()) {
                data.getData().add(new XYChart.Data(rs.getString(1), rs.getDouble(2)));
                SLNV += rs.getDouble(2);
                count++;
            }
            data.getData().add(new XYChart.Data("Trung bình", SLNV / count));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public String getMaxNaNVByPhongBan(String maPB) {
        String maNV = "";
        try {
            String sql = "{call SP_MaxNaNVByPhongBan(?)}";
            ResultSet rs = JDBC.executeQuery(sql, maPB);
            while (rs.next()) {
                maNV = rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maNV;
    }

    public String getMaxMaHDOfYear(int year) {
        String maHD = "HDLD" + String.valueOf(year).substring(2, 4) + "0000";
        try {
            String sql = "{call SP_MaxMaHDOfYear(?)}";
            ResultSet rs = JDBC.executeQuery(sql, year);
            while (rs.next()) {
                if (rs.getString(1) != null) {
                    maHD = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maHD;
    }

    public int insert(NhanVien nv) {
        int result = 0;
        try {
            String sql = "{call sp_nhanvien(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            result = JDBC.executeUpdate(sql,
                    null,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getGioiTinh(),
                    nv.getNgaySinh(),
                    nv.getSoCM(),
                    nv.getDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getHinh(),
                    nv.getTrinhDoHV(),
                    nv.getMaHD(),
                    nv.getMaCV(),
                    nv.getMaPB(),
                    nv.getNgayVaoLam(),
                    nv.getNgayKetThuc(),
                    nv.getHeSoLuong(),
                    nv.getTrangThai(), "insert");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int update(NhanVien nv, String... maNVCu) {
        int result = 0;
        try {
            String sql = "{call sp_nhanvien(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            result = JDBC.executeUpdate(sql, (maNVCu.length > 0 ? maNVCu[0] : null),
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getGioiTinh(),
                    nv.getNgaySinh(),
                    nv.getSoCM(),
                    nv.getDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getHinh(),
                    nv.getTrinhDoHV(),
                    nv.getMaHD(),
                    nv.getMaCV(),
                    nv.getMaPB(),
                    nv.getNgayVaoLam(),
                    nv.getNgayKetThuc(),
                    nv.getHeSoLuong(),
                    nv.getTrangThai(),
                    "update");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(NhanVien nv) {
        int result = 0;
        try {
            String sql = "{call sp_nhanvien(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            result = JDBC.executeUpdate(sql,
                    null,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getGioiTinh(),
                    nv.getNgaySinh(),
                    nv.getSoCM(),
                    nv.getDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getHinh(),
                    nv.getTrinhDoHV(),
                    nv.getMaHD(),
                    nv.getMaCV(),
                    nv.getMaPB(),
                    nv.getNgayVaoLam(),
                    nv.getNgayKetThuc(),
                    nv.getHeSoLuong(),
                    nv.getTrangThai(), "delete");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
