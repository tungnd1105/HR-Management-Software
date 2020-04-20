package Home.DAO;

import Home.helper.JDBC;
import javafx.collections.ObservableList;
import Home.model.table.TableNhanThan;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;

public class TableThanNhanDAO {

    public ObservableList<TableNhanThan> getDATA(String maNV) {
        ObservableList<TableNhanThan> DATA = FXCollections.observableArrayList();
        try {
            String sql = "{Call SP_TBLThanNhan(?)}";
            ResultSet rs = JDBC.executeQuery(sql, maNV);
            while (rs.next()) {
                TableNhanThan tblNT = new TableNhanThan(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6) ? "Có" : "Không");
                DATA.add(tblNT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DATA;
    }
}
