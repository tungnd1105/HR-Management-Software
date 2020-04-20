package Home.controller;

import Home.DAO.ChamCongDAO;
import Home.DAO.NhanVienDAO;
import Home.DAO.PhongBanDAO;
import Home.DAO.TableChamCongDAO;
import Home.helper.CustomDialog;
import Home.helper.Share;
import Home.helper.TransitionHelper;
import Home.helper.XDate;
import Home.model.ChamCong;
import Home.model.NhanVien;
import Home.model.table.TableChamCong;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ChamCongController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TransitionHelper.createTransition(500, 1000, -1 * anchorPane.getPrefWidth() / 2, anchorPane).play();

            tbl_ccdao = new TableChamCongDAO();
            ccdao = new ChamCongDAO();
            nvdao = new NhanVienDAO();
            listUpdate = new ArrayList<>();
            customDialog = new CustomDialog();

            //load Tabpane1
            loadCboNam1();
            year1 = cboNam1.getSelectionModel().getSelectedItem();
            loadCboThang1();
            month1 = Integer.valueOf(cboThang1.getSelectionModel().getSelectedItem());
            loadChart();

            //Loadtabpanel2
            loadCboNam2();
            year2 = cboNam2.getSelectionModel().getSelectedItem();
            loadCboThang2();
            month2 = Integer.valueOf(cboThang2.getSelectionModel().getSelectedItem());
            setColumnModel();
            loadTable();

            //load sự kiện
            addListener();

            accessPermission();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void accessPermission() {
        if (Share.MAPB != null) {
            chuyenCanChart.setTitle(chuyenCanChart.getTitle() + "\nphòng "
                    + new PhongBanDAO().findByCode(Share.MAPB + "").get(0).getTenPB());
        }
    }

    private void loadCboNam1() {
        cboNam1.getItems().clear();

        cboNam1.setItems(ccdao.getListYear());
        cboNam1.getSelectionModel().select(0);

    }

    private void loadCboThang1() {
        cboThang1.getItems().clear();
        for (int i = 1; i <= XDate.monthOfYear(year1); i++) {
            cboThang1.getItems().add(String.format("%02d", i));
        }
        //mặc định cboThang chọn tháng hiện tại nếu năm đang chọn là năm hiện tại
        //nếu không chọn tháng 1
        if (year1 == LocalDate.now().getYear()) {
            cboThang1.getSelectionModel().select(XDate.monthOfYear(year1) - 1);
        } else {
            cboThang1.getSelectionModel().select(0);
        }
    }

    private void loadCboNam2() {
        cboNam2.getItems().clear();

        cboNam2.setItems(ccdao.getListYear());
        cboNam2.getSelectionModel().select(0);

    }

    private void loadCboThang2() {
        cboThang2.getItems().clear();
        for (int i = 1; i <= XDate.monthOfYear(year2); i++) {
            cboThang2.getItems().add(String.format("%02d", i));
        }
        //mặc định cboThang chọn tháng hiện tại nếu năm đang chọn là năm hiện tại
        //nếu không chọn tháng 1
        if (year2 == LocalDate.now().getYear()) {
            cboThang2.getSelectionModel().select(XDate.monthOfYear(year2) - 1);
        } else {
            cboThang2.getSelectionModel().select(0);
        }
    }

    private void addListener() {

        //load lại cboThang1 khi thay đổi cboNam1
        cboNam1.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue ov, Integer oldValue, Integer newValue) {
                year1 = newValue;
                loadCboThang1();
            }
        });
        //load lại chart khi thay đổi cboThang1
        cboThang1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                try {
                    month1 = Integer.parseInt(newValue);
                } catch (Exception e) {
                }
                loadChart();
            }
        });
        //load lại cboThang2 khi thay đổi cboNam2
        cboNam2.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue ov, Integer oldValue, Integer newValue) {
                year2 = newValue;
                loadCboThang2();
            }
        });
        //load lại table khi thay đổi cboThang2
        cboThang2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                try {
                    month2 = Integer.parseInt(newValue);
                } catch (Exception e) {
                }
                createRecordsIfNoRecordExist();
                loadTable();
            }
        });

//        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                loadTable();
//            }
//        });
    }

    private void loadChart() {
        chuyenCanChart.setData(ccdao.getDataForChuyenCanChart(year1, month1));
        soNgayLamViecChart.setData(ccdao.getDataForSoNgayLamViecChart(year1, month1));
    }

    private void createRecordsIfNoRecordExist() {
        if (ccdao.noRecordExist(year2, month2)) {
            ObservableList<NhanVien> listNhanViens = nvdao.findByMonth(year2, month2, "");
            for (NhanVien nv : listNhanViens) {
//                Boolean onwork[] = new Boolean[31];
//                for (int i = 0; i < 31; i++) {
//                    Date ngay = XDate.toDate((i + 1) + "/" + month2 + "/" + year2);
//                    if (!XDate.isHoliday(ngay) && (i + 1) <= XDate.maxDaysOfMonth(year2, month2)) {
//                        onwork[i] = Boolean.TRUE;
//                    } else {
//                        onwork[i] = Boolean.FALSE;
//                    }
//                }
//                ChamCong cc = new ChamCong(nv.getMaNV(), XDate.toDate("1/" + month2 + "/" + year2), onwork);
//                ccdao.insert(cc);
                ChamCong cc = new ChamCong(nv.getMaNV(), XDate.toDate("1/" + month2 + "/" + year2),
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
                ccdao.insert(cc);
            }
            loadTable();
        }
    }

    @FXML
    private void loadTable() {
        tblChamCong.getItems().clear();
        tblChamCong.setItems(tbl_ccdao.getData(XDate.toDate("1/" + month2 + "/" + year2), ""));
        disableCells();
    }
    
    @FXML
    private void loadTableWithSearch() {
        tblChamCong.getItems().clear();
        tblChamCong.setItems(tbl_ccdao.getData(XDate.toDate("1/" + month2 + "/" + year2), txtTimKiem.getText()));
        disableCells();
    }

    private void setColumnModel() {
        //Khai bao cot
        maNVCol = new TableColumn<>("Mã nhân viên");
        maNVCol.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        maNVCol.setPrefWidth(120);
        hoTenCol = new TableColumn<>("Họ tên");
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        hoTenCol.setPrefWidth(200);
        phongBanCol = new TableColumn<>("Phòng ban");
        phongBanCol.setCellValueFactory(new PropertyValueFactory<>("tenPhongBan"));
        phongBanCol.setPrefWidth(110);
        col1 = new TableColumn<>("Ngày 1");
        col2 = new TableColumn<>("Ngày 2");
        col3 = new TableColumn<>("Ngày 3");
        col4 = new TableColumn<>("Ngày 4");
        col5 = new TableColumn<>("Ngày 5");
        col6 = new TableColumn<>("Ngày 6");
        col7 = new TableColumn<>("Ngày 7");
        col8 = new TableColumn<>("Ngày 8");
        col9 = new TableColumn<>("Ngày 9");
        col10 = new TableColumn<>("Ngày 10");
        col11 = new TableColumn<>("Ngày 11");
        col12 = new TableColumn<>("Ngày 12");
        col13 = new TableColumn<>("Ngày 13");
        col14 = new TableColumn<>("Ngày 14");
        col15 = new TableColumn<>("Ngày 15");
        col16 = new TableColumn<>("Ngày 16");
        col17 = new TableColumn<>("Ngày 17");
        col18 = new TableColumn<>("Ngày 18");
        col19 = new TableColumn<>("Ngày 19");
        col20 = new TableColumn<>("Ngày 20");
        col21 = new TableColumn<>("Ngày 21");
        col22 = new TableColumn<>("Ngày 22");
        col23 = new TableColumn<>("Ngày 23");
        col24 = new TableColumn<>("Ngày 24");
        col25 = new TableColumn<>("Ngày 25");
        col26 = new TableColumn<>("Ngày 26");
        col27 = new TableColumn<>("Ngày 27");
        col28 = new TableColumn<>("Ngày 28");
        col29 = new TableColumn<>("Ngày 29");
        col30 = new TableColumn<>("Ngày 30");
        col31 = new TableColumn<>("Ngày 31");

        col1.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay1());
                addCheckBoxCellListener(booleanProp, obj, "Ngay1");
                return booleanProp;
            }
        });
        col2.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay2());
                addCheckBoxCellListener(booleanProp, obj, "Ngay2");
                return booleanProp;
            }
        });
        col3.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay3());
                addCheckBoxCellListener(booleanProp, obj, "Ngay3");
                return booleanProp;
            }
        });
        col4.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay4());
                addCheckBoxCellListener(booleanProp, obj, "Ngay4");
                return booleanProp;
            }
        });
        col5.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay5());
                addCheckBoxCellListener(booleanProp, obj, "Ngay5");
                return booleanProp;
            }
        });
        col6.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay6());
                addCheckBoxCellListener(booleanProp, obj, "Ngay6");
                return booleanProp;
            }
        });
        col7.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay7());
                addCheckBoxCellListener(booleanProp, obj, "Ngay7");
                return booleanProp;
            }
        });
        col8.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay8());
                addCheckBoxCellListener(booleanProp, obj, "Ngay8");
                return booleanProp;
            }
        });
        col9.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay9());
                addCheckBoxCellListener(booleanProp, obj, "Ngay9");
                return booleanProp;
            }
        });
        col10.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay10());
                addCheckBoxCellListener(booleanProp, obj, "Ngay10");
                return booleanProp;
            }
        });
        col11.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay11());
                addCheckBoxCellListener(booleanProp, obj, "Ngay11");
                return booleanProp;
            }
        });
        col12.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay12());
                addCheckBoxCellListener(booleanProp, obj, "Ngay12");
                return booleanProp;
            }
        });
        col13.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay13());//khi thay đổi
                addCheckBoxCellListener(booleanProp, obj, "Ngay13");
                return booleanProp;
            }
        });
        col14.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay14());
                addCheckBoxCellListener(booleanProp, obj, "Ngay14");
                return booleanProp;
            }
        });
        col15.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay15());//khi thay đổi
                addCheckBoxCellListener(booleanProp, obj, "Ngay15");
                return booleanProp;
            }
        });
        col16.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay16());
                addCheckBoxCellListener(booleanProp, obj, "Ngay16");
                return booleanProp;
            }
        });
        col17.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay17());
                addCheckBoxCellListener(booleanProp, obj, "Ngay17");
                return booleanProp;
            }
        });
        col18.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay18());
                addCheckBoxCellListener(booleanProp, obj, "Ngay18");
                return booleanProp;
            }
        });
        col19.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay19());
                addCheckBoxCellListener(booleanProp, obj, "Ngay19");
                return booleanProp;
            }
        });
        col20.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay20());
                addCheckBoxCellListener(booleanProp, obj, "Ngay20");
                return booleanProp;
            }
        });
        col21.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay21());
                addCheckBoxCellListener(booleanProp, obj, "Ngay21");
                return booleanProp;
            }
        });
        col22.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay22());
                addCheckBoxCellListener(booleanProp, obj, "Ngay22");
                return booleanProp;
            }
        });
        col23.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay23());
                addCheckBoxCellListener(booleanProp, obj, "Ngay23");
                return booleanProp;
            }
        });
        col24.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay24());
                addCheckBoxCellListener(booleanProp, obj, "Ngay24");
                return booleanProp;
            }
        });
        col25.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay25());
                addCheckBoxCellListener(booleanProp, obj, "Ngay25");
                return booleanProp;
            }
        });
        col26.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay26());
                addCheckBoxCellListener(booleanProp, obj, "Ngay26");
                return booleanProp;
            }
        });
        col27.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay27());
                addCheckBoxCellListener(booleanProp, obj, "Ngay27");
                return booleanProp;
            }
        });
        col28.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay28());
                addCheckBoxCellListener(booleanProp, obj, "Ngay28");
                return booleanProp;
            }
        });
        col29.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay29());
                addCheckBoxCellListener(booleanProp, obj, "Ngay29");
                return booleanProp;
            }
        });
        col30.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay30());
                addCheckBoxCellListener(booleanProp, obj, "Ngay30");
                return booleanProp;
            }
        });
        col31.setCellValueFactory(new Callback<CellDataFeatures<TableChamCong, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TableChamCong, Boolean> param) {
                TableChamCong obj = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(obj.getNgay31());
                addCheckBoxCellListener(booleanProp, obj, "Ngay31");
                return booleanProp;
            }
        });

        tblChamCong.getColumns().addAll(maNVCol, hoTenCol, phongBanCol, col1, col2, col3, col4, col5, col6, col7, col8, col9,
                col10, col11, col12, col13, col14, col15, col16, col17, col18, col19, col20, col21, col22, col23, col24,
                col25, col26, col27, col28, col29, col30, col31);

        for (int colIndex = 3; colIndex < tblChamCong.getColumns().size(); colIndex++) {
            TableColumn<TableChamCong, Boolean> BooleanCol = (TableColumn<TableChamCong, Boolean>) tblChamCong.getColumns().get(colIndex);
            setCellFactor(BooleanCol);
        }

    }

    //định dạng checkBox
    private void setCellFactor(TableColumn<TableChamCong, Boolean> column) {
        column.setCellFactory(new Callback<TableColumn<TableChamCong, Boolean>, TableCell<TableChamCong, Boolean>>() {
            @Override
            public TableCell<TableChamCong, Boolean> call(TableColumn<TableChamCong, Boolean> p) {
                CheckBoxTableCell<TableChamCong, Boolean> cell = new CheckBoxTableCell<>();
                return cell;
            }
        });
        column.setStyle("-fx-alignment: CENTER;");
    }

    private void addCheckBoxCellListener(SimpleBooleanProperty booleanProp, TableChamCong obj, String ngay) {
        booleanProp.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                listUpdate.add(new updateChamCong(obj.getMaNV(), ngay, newValue));
            }
        });
    }

    //Phuong thuc disable các ô là ngày lễ, CN, không tồn tại trong tháng 
    //hoặc trước ngày nhân viên bắt đầu đi làm
    private void disableCells() {
        ObservableList<TableColumn<TableChamCong, ?>> colList = tblChamCong.getColumns();
        int maxDay = XDate.maxDaysOfMonth(year2, month2);

        for (int dayOfMonth = 1; dayOfMonth <= 31; dayOfMonth++) {
            Date date = XDate.toDate(dayOfMonth + "/" + month2 + "/" + year2);
            //disable các ô là ngày lễ, CN và không tồn tại trong tháng
            if (XDate.isHoliday(date) || dayOfMonth > maxDay) {
                colList.get(dayOfMonth + 2).setEditable(false);
            } else {
                colList.get(dayOfMonth + 2).setEditable(true);
                disableCellsBeforeStartWorking(dayOfMonth + 2);
            }
        }

    }

    private void disableCellsBeforeStartWorking(int colIndex) {
        TableColumn<TableChamCong, Boolean> column = (TableColumn<TableChamCong, Boolean>) tblChamCong.getColumns().get(colIndex);
        ArrayList<Integer> listDisableRowIndex = new ArrayList<>();
        ObservableList<NhanVien> listNV = nvdao.findNVStartWorkingInMonth(year2, month2);
        for (NhanVien nv : listNV) {
            for (TableChamCong row : tblChamCong.getItems()) {
                if (row.getMaNV().equals(nv.getMaNV())) {
                    LocalDate ngayVaoLam = XDate.toLocalDate(nv.getNgayVaoLam());
                    if (ngayVaoLam.getDayOfMonth() > (colIndex - 2)) {
                        listDisableRowIndex.add(tblChamCong.getItems().indexOf(row));
                    }
                }
            }
        }
        disableCellInColum(listDisableRowIndex, column);

    }

    private void disableCellInColum(ArrayList<Integer> listDisableRowIndex, TableColumn<TableChamCong, Boolean> column) {
        column.setCellFactory(new Callback<TableColumn<TableChamCong, Boolean>, TableCell<TableChamCong, Boolean>>() {
            @Override
            public TableCell<TableChamCong, Boolean> call(TableColumn<TableChamCong, Boolean> param) {
                return new CheckBoxTableCell<TableChamCong, Boolean>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        try {
                            for (Integer rowIndex : listDisableRowIndex) {
                                if (getIndex() == rowIndex) {
                                    setEditable(false);
                                }
                            }
                        } catch (Exception ex) {

                        }
                    }
                };
            }
        });
    }

    @FXML
    private void update() {
        for (updateChamCong update : listUpdate) {
            //kiểm tra bản ghi có tồn tại hay không
            ccdao.update(update.getMaNV(), update.getNgay(), update.getTinhTrang(), XDate.toDate("1/" + month2 + "/" + year2));
        }
        loadTable();
        listUpdate.clear();
        customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Cập nhật thành công");
    }

    private TableChamCongDAO tbl_ccdao;
    private ChamCongDAO ccdao;
    private NhanVienDAO nvdao;
    private CustomDialog customDialog;

    private int year1;//bien cua tab1
    private int month1;
    private int year2;//bien cua tab2
    private int month2;

    private ArrayList<updateChamCong> listUpdate;

    private TableColumn<TableChamCong, String> maNVCol;
    private TableColumn<TableChamCong, String> hoTenCol;
    private TableColumn<TableChamCong, String> phongBanCol;
    private TableColumn<TableChamCong, Boolean> col1;
    private TableColumn<TableChamCong, Boolean> col2;
    private TableColumn<TableChamCong, Boolean> col3;
    private TableColumn<TableChamCong, Boolean> col4;
    private TableColumn<TableChamCong, Boolean> col5;
    private TableColumn<TableChamCong, Boolean> col6;
    private TableColumn<TableChamCong, Boolean> col7;
    private TableColumn<TableChamCong, Boolean> col8;
    private TableColumn<TableChamCong, Boolean> col9;
    private TableColumn<TableChamCong, Boolean> col10;
    private TableColumn<TableChamCong, Boolean> col11;
    private TableColumn<TableChamCong, Boolean> col12;
    private TableColumn<TableChamCong, Boolean> col13;
    private TableColumn<TableChamCong, Boolean> col14;
    private TableColumn<TableChamCong, Boolean> col15;
    private TableColumn<TableChamCong, Boolean> col16;
    private TableColumn<TableChamCong, Boolean> col17;
    private TableColumn<TableChamCong, Boolean> col18;
    private TableColumn<TableChamCong, Boolean> col19;
    private TableColumn<TableChamCong, Boolean> col20;
    private TableColumn<TableChamCong, Boolean> col21;
    private TableColumn<TableChamCong, Boolean> col22;
    private TableColumn<TableChamCong, Boolean> col23;
    private TableColumn<TableChamCong, Boolean> col24;
    private TableColumn<TableChamCong, Boolean> col25;
    private TableColumn<TableChamCong, Boolean> col26;
    private TableColumn<TableChamCong, Boolean> col27;
    private TableColumn<TableChamCong, Boolean> col28;
    private TableColumn<TableChamCong, Boolean> col29;
    private TableColumn<TableChamCong, Boolean> col30;
    private TableColumn<TableChamCong, Boolean> col31;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXComboBox<Integer> cboNam1;

    @FXML
    private JFXComboBox<String> cboThang1;

    @FXML
    private JFXComboBox<Integer> cboNam2;

    @FXML
    private JFXComboBox<String> cboThang2;

    @FXML
    private TableView<TableChamCong> tblChamCong;

    @FXML
    private PieChart soNgayLamViecChart;

    @FXML
    private PieChart chuyenCanChart;

    @FXML
    private TextField txtTimKiem;

    class updateChamCong {

        private String maNV;
        private String ngay;
        private boolean tinhTrang;

        public updateChamCong(String maNV, String ngay, boolean tinhTrang) {
            this.maNV = maNV;
            this.ngay = ngay;
            this.tinhTrang = tinhTrang;
        }

        public boolean getTinhTrang() {
            return tinhTrang;
        }

        public void setTinhTrang(boolean tinhTrang) {
            this.tinhTrang = tinhTrang;
        }

        public String getMaNV() {
            return maNV;
        }

        public void setMaNV(String maNV) {
            this.maNV = maNV;
        }

        public String getNgay() {
            return ngay;
        }

        public void setNgay(String ngay) {
            this.ngay = ngay;
        }

    }
}
