package Home.controller;

import Home.DAO.TaiKhoanDAO;
import Home.helper.Share;
import Home.helper.CustomDialog;
import Home.helper.Validate;
import Home.model.TaiKhoan;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class DoiMatKhauController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            BoxBlur blur = new BoxBlur(3, 3, 3);
            Share.blurPane.setEffect(blur);
            
            tkdao = new TaiKhoanDAO();
            customDialog = new CustomDialog();
            validatorInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void show(WindowEvent event) {
        FadeTransition fadein = new FadeTransition(Duration.seconds(5), mainPane);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }
    
    //Phương thức khởi tạo validatorJFX
    public void validatorInit() {
        txtMatKhauCu.getValidators().add(Validate.createValidatorJFX("Vui lòng nhập tài khoản"));
        txtMatKhauCu.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtMatKhauCu.validate();
                }
            }

        });

        txtMatKhauMoi.getValidators().add(Validate.createValidatorJFX("Vui lòng nhập mật khẩu"));
        txtMatKhauMoi.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtMatKhauMoi.validate();
                }
            }

        });

        txtXacNhanMatKhau.getValidators().add(Validate.createValidatorJFX("Vui lòng nhập mật khẩu"));
        txtXacNhanMatKhau.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtXacNhanMatKhau.validate();
                }
            }

        });
    }

    private boolean checkNull() {
        if (Validate.isNull(txtMatKhauCu, "Vui lòng nhập mật khẩu cũ", stackPane, mainPane)) {
            return false;
        }
        if (Validate.isNull(txtMatKhauMoi, "Vui lòng nhập mật khẩu mới", stackPane, mainPane)) {
            return false;
        }
        if (Validate.isNull(txtXacNhanMatKhau, "Vui lòng nhập xác nhận mật khẩu", stackPane, mainPane)) {
            return false;
        }
        return true;
    }
    
    private boolean checkContent() {
        String regexEnglish = "[\\p{L}0-9 ]+";
        if (!txtMatKhauCu.getText().equals(Share.USER.getMatKhau())) {
            customDialog.showDialog(stackPane, mainPane, false, "Mật khẩu cũ không chính xác");
            txtXacNhanMatKhau.requestFocus();
            return false;
        }
        if (Validate.isNotMatches(txtMatKhauMoi, regexEnglish, "Mật khẩu mới không được chứa chữ cái có dấu, "
                + "và các ký tự đặc biệt")) {
            return false;
        }
        if (!txtMatKhauMoi.getText().equals(txtXacNhanMatKhau.getText())) {
            customDialog.showDialog(stackPane, mainPane, false, "Xác nhận mật khẩu không chính xác");
            return false;
        }
        return true;
    }

    @FXML
    void changePassword() {
        if (checkNull() && checkContent()) {
            TaiKhoan tk = new TaiKhoan();
            tk.setMaNV(Share.USER.getMaNV());
            tk.setTaiKhoan(Share.USER.getTaiKhoan());
            tk.setMatKhau(txtMatKhauMoi.getText());
            int kq = tkdao.update(tk);
            if (kq == 0) {
                customDialog.showDialog(stackPane, mainPane, false, "Có lỗi xảy ra, không thể đổi mật khẩu");
            } else {
                customDialog.showDialog(stackPane, mainPane, true, "Đổi mật khẩu thành công");
                Share.USER = tk;
            }
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Share.secondStage.close();
        Share.secondStage = null;
        Share.blurPane.setEffect(null);
        Share.mainPane.setDisable(false);
    }

    @FXML
    void enterToChange(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnConfirm.fire();
        }
    }

    @FXML
    void getCoorMouse(MouseEvent event) {
        xMouse = event.getSceneX();
        yMouse = event.getSceneY();
    }

    @FXML
    public void movePanel(MouseEvent event) {
        double x = event.getScreenX();
        double y = event.getScreenY();
        //set tọa độ mới cho JDialog khi rê chuột
        Share.secondStage.setX(x - xMouse);
        Share.secondStage.setY(y - yMouse);
    }
    
    private TaiKhoanDAO tkdao;
    private CustomDialog customDialog;
    
    //tọa độ con trỏ chuột
    double xMouse;
    double yMouse;

    @FXML
    private StackPane stackPane;
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton btnConfirm;

    @FXML
    private JFXPasswordField txtMatKhauCu;

    @FXML
    private JFXPasswordField txtXacNhanMatKhau;

    @FXML
    private JFXPasswordField txtMatKhauMoi;

}
