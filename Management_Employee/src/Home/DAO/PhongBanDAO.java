package Home.DAO;

import Home.helper.JDBC;
import Home.model.PhongBan;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PhongBanDAO {

    public ObservableList<PhongBan> findByCode(String code) {
        ObservableList<PhongBan> list = FXCollections.observableArrayList();
        PhongBan phongBan;
        try {
            String sql = "{Call SP_FindPhongBanByCode(?)}";
            ResultSet rs = JDBC.executeQuery(sql, code);
            while (rs.next()) {
                phongBan = new PhongBan(rs.getString(1), rs.getString(2));
                list.add(phongBan);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int insert(PhongBan pb) {
        int result = 0;
        try {
            String sql = "{call sp_phongban(?,?,?)}";
            result = JDBC.executeUpdate(sql,
                    pb.getMaPB(),
                    pb.getTenPB(),
                    "insert"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int update(PhongBan pb) {
        int result = 0;
        try {
            String sql = "{call sp_phongban(?,?,?)}";
            result = JDBC.executeUpdate(sql,
                    pb.getMaPB(),
                    pb.getTenPB(),
                    "update"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int delete(PhongBan pb) throws RuntimeException{
        int result = 0;
        try {
            String sql = "{call sp_phongban(?,?,?)}";
            result = JDBC.executeUpdate(sql,
                    pb.getMaPB(),
                    pb.getTenPB(),
                    "delete"
            );
        } catch (Exception ex) {
            result = 0;
        }
        return result;
    }
}
