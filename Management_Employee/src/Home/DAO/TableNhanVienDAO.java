package Home.DAO;

import Home.helper.Share;
import Home.helper.CustomDialog;
import Home.helper.FormatNumber;
import Home.helper.IConfirmationDialog;
import Home.helper.JDBC;
import Home.model.NhanVien;
import Home.model.table.TableNhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TableNhanVienDAO {

    public ObservableList<TableNhanVien> getData(String timKiem) {
        ObservableList<TableNhanVien> data = FXCollections.observableArrayList();
        try {
            String sql = "{Call SP_TBLNhanVien(?,?)}";
            ResultSet rs = null;
            if (Share.MAPB != null && Share.MAPB.equals("NS")) {
                rs = JDBC.executeQuery(sql, (Object) null, timKiem);
            } else {
                rs = JDBC.executeQuery(sql, Share.MAPB, timKiem);
            }
            while (rs.next()) {
                TableNhanVien tblnv = new TableNhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3) ? "Nam" : "Nữ", rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), FormatNumber.formatDouble(Double.parseDouble(rs.getString(15))),
                        rs.getBoolean(16) ? "Đang làm việc" : "Đã nghỉ việc");

                data.add(tblnv);
                tblnv.getDelete().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        tempNV = new NhanVienDAO().findByCode(tblnv.getMaNV());
                        if (tempNV.getMaNV().equals(Share.USER.getMaNV())) {
                            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Không thể xóa chính mình");
                            return;
                        }
                        customDialog.confirmDialog(Share.mainPane, Share.blurPane,
                                "Bạn chắc chắn muốn xóa nhân viên " + tempNV.getHoTen(), new deleteNhanVienHandler());

                    }
                });
                tblnv.getUpdate().setOnAction((ActionEvent event) -> {
                    Share.nvController.nv = new NhanVienDAO().findByCode(tblnv.getMaNV());
                    Share.nvController.setModelNhanVien(Share.nvController.nv);
                    Share.nvController.changeTabPane(2);
                    Share.nvController.loadDataToTableNT();
                    Share.nvController.setStatusNV(false);
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    CustomDialog customDialog = new CustomDialog();
    NhanVien tempNV;

    private class deleteNhanVienHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            Share.nvController.deleteNV(tempNV);
        }

        @Override
        public void onCancel() {

        }

    }
}
