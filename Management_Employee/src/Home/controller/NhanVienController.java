package Home.controller;

import Home.DAO.NhanThanDAO;
import Home.DAO.TableThanNhanDAO;
import Home.model.table.TableNhanThan;
import Home.helper.CustomDialog;
import Home.DAO.ChucVuDAO;
import Home.DAO.NhanVienDAO;
import Home.DAO.PhongBanDAO;
import Home.DAO.TableNhanVienDAO;
import Home.helper.Share;
import Home.helper.FormatNumber;
import Home.helper.Picture;
import Home.helper.TransitionHelper;
import Home.helper.Validate;
import Home.helper.XDate;
import Home.model.ChucVu;
import Home.model.NhanVien;
import Home.model.PhongBan;
import Home.model.ThanNhan;
import Home.model.table.TableNhanVien;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import Home.helper.IConfirmationDialog;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NhanVienController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            TransitionHelper.createTransition(500, 1000, -1 * anchorPane.getPrefWidth(), anchorPane).play();

            Share.nvController = this;
            nvdao = new NhanVienDAO();
            pbdao = new PhongBanDAO();
            cvdao = new ChucVuDAO();
            tbl_nvdao = new TableNhanVienDAO();
            tbl_ntdao = new TableThanNhanDAO();
            ntdao = new NhanThanDAO();
            customDialog = new CustomDialog();

            //tab 1
            loadCharts();

            //tab 2
            setTableNVColumn();
            loadDataToTableNV();

            //tab 3
            loadComboboxs();
            //tab4 
            setTableNTcolumm();
            //định dạng ngày kiểu dd/MM/yyyy cho DatePicker
            DPickerNgaySinh.setConverter(XDate.converter);
            DPickerNgayBatDau.setConverter(XDate.converter);
            DPickerNgayKetThuc.setConverter(XDate.converter);
            newNV();
            setDisableTextFields();
            addListener();

            accessPermission();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void accessPermission() {
        if (Share.MAPB != null && !Share.MAPB.equals("NS")) {

            if (Share.MAPB != null) {
                chartTyLeNamNu.setTitle(chartTyLeNamNu.getTitle() + "\nphòng "
                        + new PhongBanDAO().findByCode(Share.MAPB + "").get(0).getTenPB());
            }

            paneBottomNV.setDisable(true);
            paneBottomNT.setDisable(true);
            for (Node node : infoPaneNV1.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).setEditable(false);
                }
                if (node instanceof ComboBox) {
                    ((ComboBox) node).setOnMouseClicked(e -> {
                        ((ComboBox) node).hide();
                    });
                }
                if (node instanceof DatePicker) {
                    ((DatePicker) node).setOnMouseClicked(e -> {
                        ((DatePicker) node).hide();
                    });
                }
            }
            for (Node node : infoPaneNV2.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).setEditable(false);
                }
                if (node instanceof ComboBox) {
                    ((ComboBox) node).setOnMouseClicked(e -> {
                        ((ComboBox) node).hide();
                    });
                }
                if (node instanceof DatePicker) {
                    ((DatePicker) node).setOnMouseClicked(e -> {
                        ((DatePicker) node).hide();
                    });
                }
            }
            for (Node node : infoPaneNV3.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).setEditable(false);
                }
                if (node instanceof ComboBox) {
                    ((ComboBox) node).setOnMouseClicked(e -> {
                        ((ComboBox) node).hide();
                    });
                }
                if (node instanceof DatePicker) {
                    ((DatePicker) node).setOnMouseClicked(e -> {
                        ((DatePicker) node).hide();
                    });
                }
            }
            for (Node node : infoPaneNT.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).setEditable(false);
                }
                if (node instanceof ComboBox) {
                    ((ComboBox) node).setOnMouseClicked(e -> {
                        ((ComboBox) node).hide();
                    });
                }
                if (node instanceof DatePicker) {
                    ((DatePicker) node).setOnMouseClicked(e -> {
                        ((DatePicker) node).hide();
                    });
                }
            }
            for (int i = 0; i < tblNhanVien.getItems().size(); i++) {
                deleteColumn.getCellData(i).setDisable(true);
            }
        }
    }

    private void loadCharts() {
        chartTyLeNamNu.setData(nvdao.getDataForPieChart());

        chartSLNhanVien.getData().add(nvdao.getDataForBarChart());
    }

    private void setTableNVColumn() {
        //Khai bao cot
        deleteColumn = new TableColumn<>("");
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        deleteColumn.setStyle("-fx-alignment: CENTER-RIGHT; "
                + "-fx-border-width: 1 0 1 1; "
                + "-fx-border-color: transparent");

        updateColumn = new TableColumn<>("");
        updateColumn.setCellValueFactory(new PropertyValueFactory<>("Update"));
        updateColumn.setStyle("-fx-alignment: CENTER-LEFT;"
                + "-fx-border-color: transparent");

        col1 = new TableColumn<>("Mã nhân viên");
        col1.setCellValueFactory(new PropertyValueFactory<>("MaNV"));

        col2 = new TableColumn<>("Họ tên");
        col2.setCellValueFactory(new PropertyValueFactory<>("HoTen"));

        col3 = new TableColumn<>("Giới tính");
        col3.setCellValueFactory(new PropertyValueFactory<>("GioiTinh"));

        col4 = new TableColumn<>("Ngày sinh");
        col4.setCellValueFactory(new PropertyValueFactory<>("NgaySinh"));

        col5 = new TableColumn<>("Số CMND");
        col5.setCellValueFactory(new PropertyValueFactory<>("SoCM"));

        col6 = new TableColumn<>("Điện thoại");
        col6.setCellValueFactory(new PropertyValueFactory<>("DienThoai"));

        col7 = new TableColumn<>("Email");
        col7.setCellValueFactory(new PropertyValueFactory<>("Email"));

        col8 = new TableColumn<>("Địa chỉ");
        col8.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));

        col9 = new TableColumn<>("Trình độ học vấn");
        col9.setCellValueFactory(new PropertyValueFactory<>("TrinhDoHV"));

        col10 = new TableColumn<>("Mã hợp đồng");
        col10.setCellValueFactory(new PropertyValueFactory<>("MaHD"));

        col11 = new TableColumn<>("Phòng ban");
        col11.setCellValueFactory(new PropertyValueFactory<>("PhongBan"));

        col12 = new TableColumn<>("Chức vụ");
        col12.setCellValueFactory(new PropertyValueFactory<>("ChucVu"));

        col13 = new TableColumn<>("Ngày vào làm");
        col13.setCellValueFactory(new PropertyValueFactory<>("NgayVaoLam"));

        col14 = new TableColumn<>("Ngày kết thúc");
        col14.setCellValueFactory(new PropertyValueFactory<>("NgayKetThuc"));

        col15 = new TableColumn<>("Hệ số lương");
        col15.setCellValueFactory(new PropertyValueFactory<>("HeSoLuong"));

        col16 = new TableColumn<>("Trạng thái");
        col16.setCellValueFactory(new PropertyValueFactory<>("TrangThai"));

        tblNhanVien.getColumns().addAll(deleteColumn, updateColumn, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10,
                col11, col12, col13, col14, col15, col16);
    }

    private void setTableNTcolumm() {
        HotenNT = new TableColumn<>("Họ tên");
        HotenNT.setCellValueFactory(new PropertyValueFactory<>("HoTen"));
        HotenNT.setPrefWidth(200);
        NgheNghiep = new TableColumn<>("Nghề nghiệp");
        NgheNghiep.setCellValueFactory(new PropertyValueFactory<>("NgheNghiep"));
        NgheNghiep.setPrefWidth(140);
        Moiquanhe = new TableColumn<>("Mối quan hệ");
        Moiquanhe.setCellValueFactory(new PropertyValueFactory<>("MoiQuanHe"));
        Moiquanhe.setPrefWidth(120);
        giamtruphuthuoc = new TableColumn<>("Giảm trừ phụ thuộc");
        giamtruphuthuoc.setCellValueFactory(new PropertyValueFactory<>("GiamTruPhuThuoc"));
        giamtruphuthuoc.setPrefWidth(160);
        tblNhanThan.getColumns().addAll(HotenNT, NgheNghiep, Moiquanhe, giamtruphuthuoc);
    }

    private void loadDataToTableNV() {
        tblNhanVien.getItems().clear();
        tblNhanVien.setItems(tbl_nvdao.getData(txtTimKiem.getText()));
    }

    public void loadDataToTableNT() {
        if (tblNhanThan.getItems() != null) {
            tblNhanThan.getItems().clear();
        }
        tblNhanThan.setItems(tbl_ntdao.getDATA(txtMaNV.getText()));
    }

    private void loadComboboxs() {
        listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
        cboGioiTinh.setItems(listGioiTinh);

        listTrangThai = FXCollections.observableArrayList("Đang làm việc", "Đã nghỉ việc");
        cboTrangThai.setItems(listTrangThai);

        listPhongBan = pbdao.findByCode(null);
        cboPhongBan.setItems(listPhongBan);

        listChucVu = cvdao.findByCode(null);
        cboChucVu.setItems(listChucVu);

        listGiamTruPhuThuoc = FXCollections.observableArrayList("Có", "Không");
        cboGiamTruPhuThuoc.setItems(listGiamTruPhuThuoc);
    }

    private void setDisableTextFields() {
        txtMaNV.setDisable(true);
        txtMaHD.setDisable(true);
        txtEmail.setDisable(true);
    }

    private void setDefaultValueForDisableTextFields() {
        txtMaNV.setText("Tự động tạo khi chọn phòng ban");
        txtMaHD.setText(createNewMaHD(LocalDate.now().getYear()));
        txtEmail.setText("@cty.com.vn");
    }

    private void addListener() {
        txtMaNV.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.equals("")) {
                    txtMaNV.setText("Tự động tạo khi chọn phòng ban");
                }
                txtEmail.setText(newValue + "@cty.com.vn");
            }

        });

        DPickerNgayBatDau.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                DPickerNgayKetThuc.setValue(newValue.plusYears(10));
                txtMaHD.setText(createNewMaHD(newValue.getYear()));
            }
        });

        cboPhongBan.valueProperty().addListener(new ChangeListener<PhongBan>() {
            @Override
            public void changed(ObservableValue<? extends PhongBan> observable, PhongBan oldValue, PhongBan newValue) {
                if (newValue != null) {
                    txtMaNV.setText(createNewMaNV(newValue));
                    if ("GD".equals(newValue.getMaPB())) {
                        cboChucVu.getSelectionModel().select(0);
                        cboChucVu.setDisable(true);
                    } else {
                        listChucVu = cvdao.findByCode(null);
                        listChucVu.remove(0);
                        cboChucVu.setItems(listChucVu);
                        cboChucVu.setDisable(false);
                    }
                } else {
                    listChucVu = cvdao.findByCode(null);
                    cboChucVu.setItems(listChucVu);
                    cboChucVu.setDisable(false);
                }
            }

        });

        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadDataToTableNV();
            }

        });
    }

    private String createNewMaHD(int year) {
        String maxMaHD = nvdao.getMaxMaHDOfYear(year);
        String suffix = maxMaHD.substring(6, 10);
        //Thêm số không vào trước suffix và tổng cộng chỉ có 4 chữ số
        String newsuffix = String.format("%04d", Integer.parseInt(suffix) + 1);
        String newMaHD = maxMaHD.substring(0, 6) + newsuffix;
        return newMaHD;
    }

    private String createNewMaNV(PhongBan pb) {
        if (nv != null && pb.getMaPB().equals(nv.getMaPB())) {
            return nv.getMaNV();
        }
        String maxMaNV = nvdao.getMaxNaNVByPhongBan(pb.getMaPB());
        if (maxMaNV == null) {
            return pb.getMaPB() + "001";
        }
        String suffix = maxMaNV.substring(2, 5);
        //Thêm số không vào trước suffix và tổng cộng chỉ có 3 chữ số
        String newsuffix = String.format("%03d", Integer.parseInt(suffix) + 1);
        String newMaNV = maxMaNV.substring(0, 2) + newsuffix;
        return newMaNV;
    }

    //Sự kiện click vào bảng nhan vien 
    @FXML
    private void selectNhanVien(MouseEvent event) {
        try {
            TableNhanVien tableNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();
            if (tableNhanVien != null) {
                nv = nvdao.findByCode(tableNhanVien.getMaNV());
                setModelNhanVien(nv);
                loadDataToTableNT();
                setStatusNV(false);
                if (event.getClickCount() == 2 && nv != null) {
                    changeTabPane(2);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void newNV() {
        setModelNhanVien(new NhanVien());
        setStatusNV(true);
        tblNhanVien.getSelectionModel().clearSelection();
        imageFile = null;
        imageName = "";
        setDefaultValueForDisableTextFields();
    }

    @FXML
    private void insertNV() {
        if (checkNullFormNhanVien() && checkContentFormNhanvien()) {
            nv = getModelNhanVien();
            if (checkDuplicationFormNhanvien(nv) && copyImageToAvatarFolder()) {
                try {
                    nvdao.insert(nv);
                    loadDataToTableNV();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Thêm nhân viên thành công ");
                    setStatusNV(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Thêm nhân viên thất bại! vui lòng kiểm tra lại ");
                }
            }
        }
    }

    @FXML
    private void updateNV() {
        if (checkNullFormNhanVien() && checkContentFormNhanvien()) {
            String maNVcu = nv.getMaNV();
            nv = getModelNhanVien();
            if (checkDuplicationFormNhanvien(nv) && copyImageToAvatarFolder()) {
                if (maNVcu.equals(nv.getMaNV())) {
                    updateNVWithoutChangingMaNV();
                } else {
                    updateNVWithChangingMaNV(maNVcu);
                }
            }
        }
    }

    private void updateNVWithoutChangingMaNV() {
        try {
            if (nvdao.update(nv) > 0) {
                loadDataToTableNV();
                customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Cập nhật thông tin nhân viên thành công ");
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Cập nhật thông tin nhân viên thất bại! vui lòng kiểm tra lại ");
        }
    }

    private void updateNVWithChangingMaNV(String maNVCu) {
        try {
            if (nvdao.update(nv, maNVCu) > 0) {
                loadDataToTableNV();
                customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Cập nhật thông tin nhân viên thành công ");
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Cập nhật thông tin nhân viên thất bại! vui lòng kiểm tra lại ");
        }
    }

    @FXML
    private void deleteNV() {
        if (nv.getMaNV().equals(Share.USER.getMaNV())) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Không thể xóa chính mình");
            return;
        }
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn xóa nhân viên " + nv.getHoTen(), new deleteNhanVienHandler());
    }

    public void deleteNV(NhanVien nv) {
        try {
            if (nvdao.delete(nv) > 0) {
                loadDataToTableNV();
                customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Xóa nhân viên thành công");
                newNV();
                tblNhanVien.getSelectionModel().clearSelection();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Xóa nhân viên thất bại! vui lòng kiểm tra lại ");
        }
    }

    private NhanVien getModelNhanVien() {

        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setHoTen(txtHoTenNV.getText());
        nv.setGioiTinh(cboGioiTinh.getSelectionModel().getSelectedIndex() == 0);
        nv.setNgaySinh(XDate.toDate(DPickerNgaySinh.getValue()));
        nv.setHinh(imageName);
        nv.setSoCM(txtSoCM.getText());
        nv.setDienThoai(txtDienThoai.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setTrinhDoHV(txtTrinhDoHV.getText());
        nv.setTrangThai(cboTrangThai.getSelectionModel().getSelectedIndex() == 0);

        nv.setMaHD(txtMaHD.getText());
        nv.setMaPB(cboPhongBan.getSelectionModel().getSelectedItem().getMaPB());
        nv.setMaCV(cboChucVu.getSelectionModel().getSelectedItem().getMaCV());
        nv.setHeSoLuong(Double.parseDouble(txtHeSoLuong.getText()));
        nv.setNgayVaoLam(XDate.toDate(DPickerNgayBatDau.getValue()));
        nv.setNgayKetThuc(XDate.toDate(DPickerNgayKetThuc.getValue()));
        return nv;
    }

    public void setModelNhanVien(NhanVien nv) {
        imageName = nv.getHinh();
        displayAvatar(imageName);
        txtMaNV.setText(nv.getMaNV());
        txtHoTenNV.setText(nv.getHoTen());
        if (nv.getGioiTinh() == null) {
            cboGioiTinh.getSelectionModel().clearSelection();
        } else {
            cboGioiTinh.getSelectionModel().select(nv.getGioiTinh() ? 0 : 1);
        }
        DPickerNgaySinh.setValue(XDate.toLocalDate(nv.getNgaySinh()));
        txtSoCM.setText(nv.getSoCM());
        txtDienThoai.setText(nv.getDienThoai());
        txtEmail.setText(nv.getEmail());
        txtDiaChi.setText(nv.getDiaChi());
        txtTrinhDoHV.setText(nv.getTrinhDoHV());
        if (nv.getTrangThai() == null) {
            cboTrangThai.getSelectionModel().clearSelection();
        } else {
            cboTrangThai.getSelectionModel().select(nv.getTrangThai() ? 0 : 1);
        }

        txtMaHD.setText(nv.getMaHD());
        if (nv.getMaPB() == null) {
            cboPhongBan.getSelectionModel().clearSelection();
        } else {
            for (PhongBan phongBan : listPhongBan) {
                if (phongBan.getMaPB().equals(nv.getMaPB())) {
                    cboPhongBan.getSelectionModel().select(phongBan);
                }
            }
        }
        if (nv.getMaCV() == null) {
            cboChucVu.getSelectionModel().clearSelection();
        } else {
            for (ChucVu chucvu : listChucVu) {
                if (chucvu.getMaCV().equals(nv.getMaCV())) {
                    cboChucVu.getSelectionModel().select(chucvu);
                }
            }
        }
        txtHeSoLuong.setText(FormatNumber.formatDouble(nv.getHeSoLuong()));
        DPickerNgayBatDau.setValue(XDate.toLocalDate(nv.getNgayVaoLam()));
        DPickerNgayKetThuc.setValue(XDate.toLocalDate(nv.getNgayKetThuc()));
    }

    public void setStatusNV(boolean insertableNV) {
        this.insertableNV = insertableNV;

        btnCapNhatNV.setDisable(insertableNV);
        btnXoaNV.setDisable(insertableNV);
        btnTaoMoiNV.setDisable(insertableNV);
        btnThemNV.setDisable(!insertableNV);

        if (insertableNV) {
            clearFormThanNhan();
        } else {
            newNT();
        }
    }

    private void clearFormThanNhan() {
        newNT();
        btnThemNT.setDisable(true);
        tblNhanThan.getSelectionModel().clearSelection();
        tblNhanThan.setItems(null);
    }

    private boolean checkNullFormNhanVien() {
        if (Validate.isNull(txtMaNV, "Vui lòng nhập mã nhân viên")) {
            return false;
        }
        if (Validate.isNull(txtHoTenNV, "Vui lòng nhập họ tên nhân viên")) {
            return false;
        }
        if (Validate.isNotSelected(cboGioiTinh, "Vui lòng chọn giới tính")) {
            return false;
        }
        if (DPickerNgaySinh.getValue() == null) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Vui lòng chọn ngày sinh ");
            DPickerNgaySinh.requestFocus();
            return false;
        }
        if (Validate.isNull(txtSoCM, "Vui lòng nhập số CMND")) {
            return false;
        }
        if (Validate.isNull(txtDienThoai, "Vui lòng nhập số điện thoại")) {
            return false;
        }
        if (Validate.isNull(txtEmail, "Vui lòng nhập địa chỉ email")) {
            return false;
        }
        if (Validate.isNull(txtDiaChi, "Vui lòng nhập địa chỉ")) {
            return false;
        }
        if (Validate.isNull(txtTrinhDoHV, "Vui lòng nhập trình độ học vấn ")) {
            return false;
        }
        if (Validate.isNotSelected(cboTrangThai, "Vui lòng chọn trạng thái")) {
            return false;
        }
        if (Validate.isNull(txtMaHD, "Vui lòng nhập mã hợp đồng")) {
            return false;
        }
        if (Validate.isNotSelected(cboPhongBan, "Vui lòng chọn phòng ban")) {
            return false;
        }
        if (Validate.isNotSelected(cboChucVu, "Vui lòng chọn chức vụ")) {
            return false;
        }
        if (Validate.isNull(txtHeSoLuong, "Vui lòng nhập hệ số lương")) {
            return false;
        }
        if (DPickerNgayBatDau.getValue() == null) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Vui lòng chọn ngày bắt đầu ");
            DPickerNgaySinh.requestFocus();
            return false;
        }
        if (DPickerNgayKetThuc.getValue() == null) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Vui lòng chọn ngày kết thúc");
            DPickerNgaySinh.requestFocus();
            return false;
        }
        //Kiểm tra ảnh
        if (imgHinh.getImage() == null) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Vui lòng chọn ảnh cho nhân viên");
            DPickerNgayKetThuc.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkContentFormNhanvien() {
        //kiểm tra họ và tên
        String regexVietnamese = "[\\p{L}\\p{M} ]+";
        // \\p{L} matches any kind of letter from any language
        // \\p{M} matches a character intended to be combined with another character (e.g. accents, umlauts, enclosing boxes, etc.)
        if (Validate.isNotMatches(txtHoTenNV, regexVietnamese, "Họ tên không được chứa số và các ký tự đặc biệt")) {
            return false;
        }

        //Kiểm tra số CMND
        if (Validate.isNotMatches(txtSoCM, "[0-9]{9,10}", "Số CMND chỉ có 9 hoặc 10 chữ số")) {
            return false;
        }

        //Kiểm tra ngày sinh
        if (ChronoUnit.YEARS.between(DPickerNgaySinh.getValue(), LocalDate.now()) < 18) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Nhân viên phải lớn hơn 18 tuổi");
            DPickerNgaySinh.requestFocus();
            return false;
        }

        //Kiểm tra số điện thoại
        if (Validate.isNotMatches(txtDienThoai, "[0-9]{10}", "Số điện thoại chỉ có 10 chữ số")) {
            return false;
        }

        //Kiểm tra email
        if (Validate.isNotMatches(txtEmail, "\\w+@\\w+(\\.\\w+){1,2}", "Email không đúng định dạng")) {
            return false;
        }

        //Kiểm tra hệ số lương
        if (Validate.isNotMatches(txtHeSoLuong, "[0-9]+(\\.[0-9]+)?", "Hệ số lương không hợp lệ")) {
            return false;
        }
        if (Double.parseDouble(txtHeSoLuong.getText()) <= 0) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Hệ số lương phải lớn hơn 0");
            return false;
        }

        //Kiểm tra ngày kết thúc hợp đồng lao động
        if (ChronoUnit.MONTHS.between(DPickerNgayBatDau.getValue(), DPickerNgayKetThuc.getValue()) < 3) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Hợp đồng lao động phải kéo dài ít nhất 3 tháng");
            DPickerNgayKetThuc.requestFocus();
            return false;
        }

        return true;
    }

    private boolean checkDuplicationFormNhanvien(NhanVien nv) {

        if (insertableNV && nvdao.findByCode(txtMaNV.getText().trim()) != null) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Mã nhân viên đã tồn tại");
            txtMaNV.requestFocus();
            return false;
        }

        if (nvdao.findbyCMND(txtSoCM.getText().trim()) != null && !nv.getSoCM().equals(txtSoCM.getText().trim())) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Số  CMND đã tồn tại");
            txtSoCM.requestFocus();
            return false;
        }
        if (nvdao.findbyMaHD(txtMaHD.getText().trim()) != null && !nv.getMaHD().equals(txtMaHD.getText().trim())) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Mã hợp đồng đã tồn tại");
            txtMaHD.requestFocus();
            return false;
        }
        return true;
    }

    //Chọn ảnh khi click vào ImageView Avatar
    @FXML
    private void chooseImage() {
        //loc file ảnh
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        FileChooser selectImage = new FileChooser();
        selectImage.getExtensionFilters().add(imageFilter);
        imageFile = selectImage.showOpenDialog(Share.primaryStage);
        displayAvatar(imageFile);
    }

    //Hiển thị ảnh lên ImageView nếu có file ảnh được chọn
    private void displayAvatar(File imageFile) {
        if (imageFile != null) {
            imageName = imageFile.getName();
            try {
                imgHinh.setImage(new Image(imageFile.toURI().toURL().toExternalForm()));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "File ảnh không hợp lệ");
            }
        }
    }

    //Hiển thị ảnh lên ImageView từ Avatar folder
    private void displayAvatar(String imageName) {
        try {
            imgHinh.setImage(Picture.readAvatar(imageName));
        } catch (Exception ex) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Lỗi hiển thị ảnh");
        }
    }

    private boolean copyImageToAvatarFolder() {
        try {
            if (imageFile != null) {
                Picture.saveAvatar(imageFile);
            }
            return true;
        } catch (Exception e) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Lỗi khi copy file ảnh");
            return false;
        }
    }

    //su kien click vao bang nhan than 
    @FXML
    private void selectNhanThan() {
        TableNhanThan tableModel = tblNhanThan.getSelectionModel().getSelectedItem();
        if (tableModel != null) {
            ThanNhan nt = ntdao.findByCode(tableModel.getMaTN()).get(0);
            setModelThanNhan(nt);
            setStatusNT(false);
        }
    }

    @FXML
    private void newNT() {
        setModelThanNhan(new ThanNhan());
        setStatusNT(true);
    }

    @FXML
    private void insertNT() {
        if (checkNullFormNhanThan() && checkContentFormNhanThan()) {
            ThanNhan nt = getModelThanThan();
            try {
                ntdao.insert(nt);
                loadDataToTableNT();
                customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Thêm thông tin nhân thân thành công ");
                newNT();
            } catch (Exception e) {
                e.printStackTrace();
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Thêm thông tin nhân thân thất bại! vui lòng kiểm tra lại ");
            }
        }

    }

    @FXML
    private void updateNT() {
        ThanNhan nt = getModelThanThan();
        if (checkNullFormNhanThan() && checkContentFormNhanThan()) {
            try {
                ntdao.update(nt);
                customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Cập nhật thông tin nhân thân thành công ");
                loadDataToTableNT();
            } catch (Exception e) {
                e.printStackTrace();
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Cập nhật thông tin nhân thân thất bại! Vui lòng kiểm tra lại ");
            }
        }
    }

    @FXML
    private void deleteNT() {
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn xóa thân nhân " + tn.getHoTen(), new deleteThanNhanHandler());
    }

    private void deleteNT(ThanNhan tn) {
        try {
            ntdao.delete(tn);
            loadDataToTableNT();
            customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Xóa nhân thân thành công");
            newNT();
            tblNhanThan.getSelectionModel().clearSelection();
        } catch (Exception e) {
            e.printStackTrace();
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Xóa nhân thân thất bại! Vui lòng kiểm tra lại ");
        }
    }

    private ThanNhan getModelThanThan() {
        ThanNhan TTNT = new ThanNhan();
        TTNT.setMaTN(Integer.parseInt(txtMaNT.getText()));
        TTNT.setHoTen(txtHoTenNT.getText());
        TTNT.setNgheNghiep(txtNgheNghiepNT.getText());
        TTNT.setMoiQuanHe(txtMoiQuanHeNT.getText());
        TTNT.setGiamTruPhuThuoc(cboGiamTruPhuThuoc.getSelectionModel().getSelectedIndex() == 0);
        TTNT.setMaNV(txtMaNV.getText());
        return TTNT;
    }

    private void setModelThanNhan(ThanNhan TTNT) {
        txtMaNT.setText(TTNT.getMaTN() + "");
        txtHoTenNT.setText(TTNT.getHoTen());
        txtNgheNghiepNT.setText(TTNT.getNgheNghiep());
        txtMoiQuanHeNT.setText(TTNT.getMoiQuanHe());
        if (TTNT.getGiamTruPhuThuoc() == null) {
            cboGiamTruPhuThuoc.getSelectionModel().clearSelection();
        } else {
            cboGiamTruPhuThuoc.getSelectionModel().select(TTNT.getGiamTruPhuThuoc() ? 0 : 1);
        }
    }

    private void setStatusNT(boolean insertableNT) {
        btnCapNhatNT.setDisable(insertableNT);
        btnXoaNT.setDisable(insertableNT);
        btnTaoMoiNT.setDisable(insertableNT);
        btnThemNT.setDisable(!insertableNT);
    }

    private boolean checkNullFormNhanThan() {
        if (Validate.isNull(txtHoTenNT, "Vui lòng nhập họ tên nhân thân")) {
            return false;
        }
        if (Validate.isNull(txtNgheNghiepNT, "Vui lòng nhập nghề nghiệp của nhân thân")) {
            return false;
        }
        if (Validate.isNull(txtMoiQuanHeNT, "Vui lòng nhập mối quan hệ nhân thân ")) {
            return false;
        }
        if (Validate.isNotSelected(cboGiamTruPhuThuoc, "Vui lòng chọn giảm trừ phụ thuộc")) {
            return false;
        }
        return true;
    }

    private boolean checkContentFormNhanThan() {
        String regexVietnamese = "[\\p{L}\\p{M} ]+";
        if (Validate.isNotMatches(txtHoTenNT, regexVietnamese, "Họ tên không được chứa số và các ký tự đặc biệt")) {
            return false;
        }
        if (Validate.isNotMatches(txtNgheNghiepNT, regexVietnamese, "Nghề nghiệp không được chứa số và các ký tự đặc biệt")) {
            return false;
        }
        if (Validate.isNotMatches(txtMoiQuanHeNT, regexVietnamese, "Mối quan hệ không được chứa số và các ký tự đặc biệt")) {
            return false;
        }
        return true;
    }

    public void changeTabPane(int tabIndex) {
        tabPane.getSelectionModel().select(tabIndex);
    }

    private NhanThanDAO ntdao;
    private NhanVienDAO nvdao;
    private PhongBanDAO pbdao;
    private TableThanNhanDAO tbl_ntdao;
    private TableNhanVienDAO tbl_nvdao;
    private ChucVuDAO cvdao;
    private CustomDialog customDialog;

    private ObservableList listGiamTruPhuThuoc;
    private ObservableList listGioiTinh;
    private ObservableList listTrangThai;
    private ObservableList<PhongBan> listPhongBan;
    private ObservableList<ChucVu> listChucVu;
    private Boolean insertableNV;
    private String imageName;
    private File imageFile;
    public NhanVien nv;
    protected ThanNhan tn;

    private TableColumn<TableNhanVien, Button> deleteColumn;
    private TableColumn<TableNhanVien, Button> updateColumn;
    private TableColumn<TableNhanVien, String> col1;
    private TableColumn<TableNhanVien, String> col2;
    private TableColumn<TableNhanVien, String> col3;
    private TableColumn<TableNhanVien, Date> col4;
    private TableColumn<TableNhanVien, String> col5;
    private TableColumn<TableNhanVien, String> col6;
    private TableColumn<TableNhanVien, String> col7;
    private TableColumn<TableNhanVien, String> col8;
    private TableColumn<TableNhanVien, String> col9;
    private TableColumn<TableNhanVien, String> col10;
    private TableColumn<TableNhanVien, String> col11;
    private TableColumn<TableNhanVien, String> col12;
    private TableColumn<TableNhanVien, Date> col13;
    private TableColumn<TableNhanVien, Date> col14;
    private TableColumn<TableNhanVien, Integer> col15;
    private TableColumn<TableNhanVien, String> col16;

    private TableColumn<TableNhanThan, String> HotenNT;
    private TableColumn<TableNhanThan, String> NgheNghiep;
    private TableColumn<TableNhanThan, String> Moiquanhe;
    private TableColumn<TableNhanThan, String> giamtruphuthuoc;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private HBox paneBottomNV;

    @FXML
    private HBox paneBottomNT;

    @FXML
    private VBox infoPaneNV1;

    @FXML
    private VBox infoPaneNV2;

    @FXML
    private VBox infoPaneNV3;

    @FXML
    private VBox infoPaneNT;

    @FXML
    private PieChart chartTyLeNamNu;

    @FXML
    private BarChart<?, ?> chartSLNhanVien;

    @FXML
    private TableView<TableNhanThan> tblNhanThan;

    @FXML
    private TableView<TableNhanVien> tblNhanVien;

    @FXML
    private TextField txtTimKiem;

    @FXML
    private JFXTextField txtMaNV;

    @FXML
    private JFXTextField txtHoTenNV;

    @FXML
    private JFXTextField txtSoCM;

    @FXML
    private JFXTextField txtDienThoai;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtDiaChi;

    @FXML
    private JFXTextField txtTrinhDoHV;

    @FXML
    private JFXTextField txtMaHD;

    @FXML
    private JFXTextField txtHeSoLuong;

    @FXML
    private JFXComboBox cboGioiTinh;

    @FXML
    private JFXComboBox cboTrangThai;

    @FXML
    private JFXComboBox<PhongBan> cboPhongBan;

    @FXML
    private JFXComboBox<ChucVu> cboChucVu;

    @FXML
    private DatePicker DPickerNgaySinh;

    @FXML
    private DatePicker DPickerNgayBatDau;

    @FXML
    private DatePicker DPickerNgayKetThuc;

    @FXML
    private ImageView imgHinh;

    @FXML
    private JFXButton btnThemNV;

    @FXML
    private JFXButton btnCapNhatNV;

    @FXML
    private JFXButton btnXoaNV;

    @FXML
    private JFXButton btnTaoMoiNV;

    @FXML
    private JFXTextField txtMaNT;

    @FXML
    private JFXTextField txtHoTenNT;

    @FXML
    private JFXTextField txtMoiQuanHeNT;

    @FXML
    private JFXTextField txtNgheNghiepNT;

    @FXML
    private JFXComboBox cboGiamTruPhuThuoc;

    @FXML
    private JFXButton btnThemNT;

    @FXML
    private JFXButton btnCapNhatNT;

    @FXML
    private JFXButton btnXoaNT;

    @FXML
    private JFXButton btnTaoMoiNT;

    private class deleteNhanVienHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            deleteNV(nv);
        }

        @Override
        public void onCancel() {

        }

    }

    class deleteThanNhanHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            deleteNT(tn);
        }

        @Override
        public void onCancel() {

        }

    }
}
