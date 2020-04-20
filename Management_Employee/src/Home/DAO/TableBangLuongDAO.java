
package Home.DAO;

import Home.helper.JDBC;
import Home.helper.Share;
import Home.model.table.TableBangLuong;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableBangLuongDAO {
    public ObservableList<TableBangLuong> getData(int year,int month, String timKiem){
        ObservableList<TableBangLuong> data = FXCollections.observableArrayList();
        try {
            String sql = "{Call SP_TBLBangLuong(?,?,?,?)}";
            ResultSet rs = null;
            if (Share.MAPB != null && Share.MAPB.equals("KT")) {
                rs = JDBC.executeQuery(sql, (Object)null, year, month, timKiem);
            } else{
                rs = JDBC.executeQuery(sql, Share.MAPB, year, month, timKiem);
            }
            
            while (rs.next()){
                TableBangLuong tblbl = new TableBangLuong(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                    rs.getDate(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), 
                    rs.getInt(13), rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getBoolean(17));
                
                data.add(tblbl);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
