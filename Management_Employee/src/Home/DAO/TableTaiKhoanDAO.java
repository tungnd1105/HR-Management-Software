package Home.DAO;

import Home.helper.JDBC;
import Home.model.table.TableTaiKhoan;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableTaiKhoanDAO {

    public ObservableList<TableTaiKhoan> getData() {
        ObservableList<TableTaiKhoan> data = FXCollections.observableArrayList();
        NhanVienDAO nvdao = new NhanVienDAO();
        PhongBanDAO pbdao = new PhongBanDAO();
        try {
            String sql = "{Call SP_TBLTaiKhoan()}";
            ResultSet rs = JDBC.executeQuery(sql);
            while (rs.next()) {
                TableTaiKhoan tbltk = new TableTaiKhoan(rs.getString(1), rs.getString(2),
                        nvdao.findByCode(rs.getString(3)),
                        pbdao.findByCode(rs.getString(4)).get(0));

                data.add(tbltk);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

}
