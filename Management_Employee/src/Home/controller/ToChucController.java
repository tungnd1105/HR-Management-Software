package Home.controller;

import Home.DAO.ChucVuDAO;
import Home.DAO.PhongBanDAO;
import Home.DAO.TableChucVuDAO;
import Home.DAO.TablePhongBanDAO;
import Home.helper.Share;
import Home.helper.FormatNumber;
import Home.helper.CustomDialog;
import Home.helper.TransitionHelper;
import Home.helper.Validate;
import Home.model.ChucVu;
import Home.model.PhongBan;
import Home.model.table.TableChucVu;
import Home.model.table.TablePhongBan;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Home.helper.IConfirmationDialog;

public class ToChucController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TransitionHelper.createTransition(0, 1000, -1 * anchorPane.getPrefWidth(), anchorPane).play();
            Share.tcController = this;

            //tạo các đối tượng DAO
            pbdao = new PhongBanDAO();
            cvdao = new ChucVuDAO();
            tbl_PBdao = new TablePhongBanDAO();
            tbl_CVdao = new TableChucVuDAO();
            customDialog = new CustomDialog();

            pb = new PhongBan();
            cv = new ChucVu();

            //chạy các phương thức khi khởi tạo
            setTableColumn_PB();
            setTableColumn_CV();

            loadDataToTblPhongBan();
            loadDataToTblChucVu();

            newPB();
            newCV();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setTableColumn_PB() {
        //Tạo và định dạng cột
        deleteColumn_PB = new TableColumn<>("");
        deleteColumn_PB.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        deleteColumn_PB.setStyle("-fx-alignment: CENTER-RIGHT; "
                + "-fx-border-width: 1 0 1 1; "
                + "-fx-border-color: transparent");

        updateColumn_PB = new TableColumn<>("");
        updateColumn_PB.setCellValueFactory(new PropertyValueFactory<>("Update"));
        updateColumn_PB.setStyle("-fx-alignment: CENTER-LEFT;");

        col1_PB = new TableColumn<>("Mã PB");
        col1_PB.setCellValueFactory(new PropertyValueFactory<>("MaPB"));
        col1_PB.setPrefWidth(160);

        col2_PB = new TableColumn<>("Tên PB");
        col2_PB.setCellValueFactory(new PropertyValueFactory<>("TenPB"));
        col2_PB.setPrefWidth(220);

        //thêm các cột vào table
        tblPhongBan.getColumns().addAll(deleteColumn_PB, updateColumn_PB, col1_PB, col2_PB);
    }

    private void setTableColumn_CV() {
        //Tạo và định dạng cột
        deleteColumn_CV = new TableColumn<>("");
        deleteColumn_CV.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        deleteColumn_CV.setStyle("-fx-alignment: CENTER-RIGHT; "
                + "-fx-border-width: 1 0 1 1; "
                + "-fx-border-color: transparent");

        updateColumn_CV = new TableColumn<>("");
        updateColumn_CV.setCellValueFactory(new PropertyValueFactory<>("Update"));
        updateColumn_CV.setStyle("-fx-alignment: CENTER-LEFT;");

        col1_CV = new TableColumn<>("Mã CV");
        col1_CV.setCellValueFactory(new PropertyValueFactory<>("MaCV"));
        col1_CV.setPrefWidth(90);

        col2_CV = new TableColumn<>("Tên CV");
        col2_CV.setCellValueFactory(new PropertyValueFactory<>("TenCV"));
        col2_CV.setPrefWidth(180);

        col3_CV = new TableColumn<>("Phụ cấp");
        col3_CV.setCellValueFactory(new PropertyValueFactory<>("PhuCap"));
        col3_CV.setPrefWidth(106);

        //thêm các cột vào table
        tblChucVu.getColumns().addAll(deleteColumn_CV, updateColumn_CV, col1_CV, col2_CV, col3_CV);
    }

    private void loadDataToTblPhongBan() {
        tblPhongBan.setItems(tbl_PBdao.getData());
    }

    private void loadDataToTblChucVu() {
        tblChucVu.setItems(tbl_CVdao.getData());
    }

    public void setStatusPB(boolean insertablePB) {
        txtMaPB.setDisable(!insertablePB);
        btnInsertPB.setDisable(!insertablePB);
        btnUpdatePB.setDisable(insertablePB);
        btnNewPB.setDisable(insertablePB);
    }

    protected void setStatusCV(boolean insertableCV) {
        txtMaCV.setDisable(!insertableCV);
        btnInsertCV.setDisable(!insertableCV);
        btnUpdateCV.setDisable(insertableCV);
        btnNewCV.setDisable(insertableCV);
    }

    private boolean checknullPB() {
        if (Validate.isNull(txtMaPB, "Vui lòng nhập mã phòng ban")) {
            return false;
        }
        if (Validate.isNull(txtTenPB, "Vui lòng nhập tên phòng ban")) {
            return false;
        }
        return true;
    }

    private boolean checknullCV() {
        if (Validate.isNull(txtMaCV, "Vui lòng nhập mã chức vụ")) {
            return false;
        }
        if (Validate.isNull(txtTenCV, "Vui lòng nhập tên chức vụ")) {
            return false;
        }
        if (Validate.isNull(txtPhuCap, "Vui lòng nhập phụ cấp")) {
            return false;
        }
        return true;
    }

    private boolean checkDuplicationPhongBan() {
        if (pbdao.findByCode(txtMaPB.getText().trim()).size() > 0) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Mã phòng ban đã tồn tại");
            txtMaPB.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkDuplicationChucVu() {
        if (cvdao.findByCode(txtMaCV.getText().trim()).size() > 0) {
            customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Mã chức vụ đã tồn tại");
            txtMaCV.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkContentPhongBan() {
        if (Validate.isNotMatches(txtMaPB, "[A-Z]{2}", "Mã phòng ban chỉ chứa 2 chữ cái in hoa")) {
            return false;
        }
        String regexVietnamese = "[\\p{L}\\p{M} ]+";
        if (Validate.isNotMatches(txtTenPB, regexVietnamese, "Tên phòng ban không được chứa số và các ký tự đặc biệt")) {
            return false;
        }
        return true;
    }

    private boolean checkContentChucVu() {
        if (Validate.isNotMatches(txtMaCV, "[A-Z]{2}", "Mã chức vụ chỉ chứa 2 chữ cái in hoa")) {
            return false;
        }
        String regexVietnamese = "[\\p{L}\\p{M} ]+";
        if (Validate.isNotMatches(txtTenCV, regexVietnamese, "Tên chức vụ không được chứa số và các ký tự đặc biệt")) {
            return false;
        }
        if (Validate.isNotMatches(txtPhuCap, "[0-9]+(\\.[0-9]+)?", "Phụ cấp không hợp lệ")) {
            return false;
        }
        return true;
    }

    public void setModel(PhongBan pb) {
        txtMaPB.setText(pb.getMaPB());
        txtTenPB.setText(pb.getTenPB());
    }

    private PhongBan getModelPhongBan() {
        PhongBan pb = new PhongBan();
        pb.setMaPB(txtMaPB.getText());
        pb.setTenPB(txtTenPB.getText());
        return pb;

    }

    public void setModel(ChucVu cv) {
        txtMaCV.setText(cv.getMaCV());
        txtTenCV.setText(cv.getTenCV());

        if (cv.getPhuCap() != null) {
            txtPhuCap.setText(FormatNumber.formatDouble(cv.getPhuCap()));
        } else {
            txtPhuCap.setText(null);
        }

    }

    protected ChucVu getModelChucVu() {
        ChucVu cv = new ChucVu();
        cv.setMaCV(txtMaCV.getText());
        cv.setTenCV(txtMaCV.getText());
        cv.setPhuCap(Double.valueOf(txtPhuCap.getText()));
        return cv;

    }

    @FXML
    private void selectPhongBan(MouseEvent event) {
        TablePhongBan tableModel = tblPhongBan.getSelectionModel().getSelectedItem();
        if (tableModel != null) {
            pb = pbdao.findByCode(tableModel.getMaPB()).get(0);
            setModel(pb);
            setStatusPB(false);
        }
    }

    @FXML
    private void selectChucVu(MouseEvent event) {
        TableChucVu tableModel = tblChucVu.getSelectionModel().getSelectedItem();
        if (tableModel != null) {
            cv = cvdao.findByCode(tableModel.getMaCV()).get(0);
            setModel(cv);
            setStatusCV(false);
        }

    }

    @FXML
    private void newPB() {
        pb = null;
        setModel(new PhongBan());
        setStatusPB(true);
    }

    @FXML
    private void insertPB() {
        if (checknullPB() && checkDuplicationPhongBan() && checkContentPhongBan()) {
            pb = getModelPhongBan();
            try {
                if (pbdao.insert(pb) > 0) {
                    loadDataToTblPhongBan();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Thêm mới phòng ban thành công ");
                    newPB();
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Thêm mới phòng ban thất bại! vui lòng kiểm tra lại ");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void updatePB() {
        if (checknullPB() && checkContentPhongBan()) {
            pb = getModelPhongBan();
            try {
                if (pbdao.update(pb) > 0) {
                    loadDataToTblPhongBan();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Cập nhật phòng ban thành công ");
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Cập nhật phòng ban thất bại! vui lòng kiểm tra lại ");
                e.printStackTrace();
            }
        }
    }

    public void deletePB(PhongBan pb) {
        this.pb = pb;
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn xóa Phòng ban " + pb.getTenPB(), new deletePBHandler());
    }

    @FXML
    private void newCV() {
        setModel(new ChucVu());
        setStatusCV(true);
    }

    @FXML
    private void insertCV() {
        if (checknullCV() && checkDuplicationChucVu() && checkContentChucVu()) {
            cv = getModelChucVu();
            try {
                if (cvdao.insert(cv) > 0) {
                    loadDataToTblChucVu();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Thêm mới chức vụ thành công ");
                    newCV();
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Thêm mới chức vụ thất bại ! vui lòng kiểm tra lại ");
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void updateCV() {
        if (checknullCV() && checkContentChucVu()) {
            cv = getModelChucVu();
            try {
                if (cvdao.update(cv) > 0) {
                    loadDataToTblChucVu();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Cập nhật chức vụ thành công ");
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Cập nhật chức vụ thất bại ! vui lòng kiểm tra lại");
                e.printStackTrace();
            }
        }
    }

    public void deleteCV(ChucVu cv) {
        this.cv = cv;
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn xóa Chức vụ " + cv.getTenCV(), new deleteCVHandler());
    }

    //Khai báo các lớp DAO
    private PhongBanDAO pbdao;
    private ChucVuDAO cvdao;
    private TablePhongBanDAO tbl_PBdao;
    private TableChucVuDAO tbl_CVdao;
    private CustomDialog customDialog;

    public PhongBan pb;
    public ChucVu cv;

    //Khai báo  các cột cho bảng Phòng ban
    private TableColumn<TablePhongBan, Button> deleteColumn_PB;
    private TableColumn<TablePhongBan, Button> updateColumn_PB;
    private TableColumn<TablePhongBan, String> col1_PB;
    private TableColumn<TablePhongBan, String> col2_PB;

    //Khai báo các cột cho bảng Chức vụ
    private TableColumn<TableChucVu, Button> deleteColumn_CV;
    private TableColumn<TableChucVu, Button> updateColumn_CV;
    private TableColumn<TableChucVu, String> col1_CV;
    private TableColumn<TableChucVu, String> col2_CV;
    private TableColumn<TableChucVu, String> col3_CV;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton btnInsertPB;
    @FXML
    private JFXButton btnUpdatePB;
    @FXML
    private JFXButton btnNewPB;
    @FXML
    private JFXButton btnInsertCV;
    @FXML
    private JFXButton btnUpdateCV;
    @FXML
    private JFXButton btnNewCV;
    @FXML
    private JFXTextField txtMaPB;
    @FXML
    private JFXTextField txtTenPB;
    @FXML
    private JFXTextField txtMaCV;
    @FXML
    private JFXTextField txtTenCV;
    @FXML
    private JFXTextField txtPhuCap;
    @FXML
    private TableView<TablePhongBan> tblPhongBan;
    @FXML
    private TableView<TableChucVu> tblChucVu;

    class deletePBHandler implements IConfirmationDialog {

        @Override
        public void onConfirm(){
            try {
                if (pbdao.delete(pb) > 0) {
                    loadDataToTblPhongBan();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Xóa phòng ban thành công ");
                    newPB();
                } else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Xóa phòng ban thất bại! \nVui lòng kiểm tra lại");
            }
        }

        @Override
        public void onCancel() {

        }

    }

    class deleteCVHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            try {
                if (cvdao.delete(cv) > 0) {
                    loadDataToTblChucVu();
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Xóa chức vụ thành công ");
                    newPB();
                } else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Xóa chức vụ thất bại! \nVui lòng kiểm tra lại");
                ex.printStackTrace();
            }
        }

        @Override
        public void onCancel() {

        }

    }
}
