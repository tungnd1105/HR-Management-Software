
package Home.DAO;

import Home.helper.Share;
import Home.helper.JDBC;
import Home.model.table.TablePhongBan;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TablePhongBanDAO {
    
    NhanVienDAO nvdao = new NhanVienDAO();
    PhongBanDAO pbdao = new PhongBanDAO();
    
    public ObservableList<TablePhongBan> getData(){
        ObservableList<TablePhongBan> data = FXCollections.observableArrayList();
        try {
            String sql = "{Call SP_FindPhongBanByCode(?)}";
            ResultSet rs = JDBC.executeQuery(sql, (Object) null);
            while (rs.next()){
                TablePhongBan tblpb = new TablePhongBan(rs.getString(1), rs.getString(2));
                
                data.add(tblpb);
                tblpb.getDelete().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Share.tcController.deletePB(pbdao.findByCode(tblpb.getMaPB()).get(0));
                    }
                });
                tblpb.getUpdate().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Share.tcController.pb = pbdao.findByCode(tblpb.getMaPB()).get(0);
                        Share.tcController.setModel(Share.tcController.pb);
                        Share.tcController.setStatusPB(false);
                    }
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
