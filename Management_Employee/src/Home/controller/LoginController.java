package Home.controller;

import Home.DAO.TaiKhoanDAO;
import Home.helper.Share;
import Home.helper.CustomDialog;
import Home.helper.Validate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import Home.helper.IConfirmationDialog;
import Home.helper.TransitionHelper;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tkdao = new TaiKhoanDAO();
            customDialog = new CustomDialog();
            validatorInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Phương thức khởi tạo validatorJFX
    public void validatorInit() {
        txtTaiKhoan.getValidators().add(Validate.createValidatorJFX("Vui lòng nhập tài khoản"));
        txtTaiKhoan.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtTaiKhoan.validate();
                }
            }

        });

        txtMatKhau.getValidators().add(Validate.createValidatorJFX("Vui lòng nhập mật khẩu"));
        txtMatKhau.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtMatKhau.validate();
                }
            }

        });
    }

    private boolean checkNull() {
        if (Validate.isNull(txtTaiKhoan, "Vui lòng nhập tài khoản", stackPane, mainPane)) {
            return false;
        }
        if (Validate.isNull(txtMatKhau, "Vui lòng nhập mật khẩu", stackPane, mainPane)) {
            return false;
        }
        return true;
    }

    @FXML
    void login() {
        if (checkNull()) {
            int result = tkdao.checkAccount(txtTaiKhoan.getText(), txtMatKhau.getText());
            switch (result) {
                case 0:
                    textField = txtTaiKhoan;
                    customDialog.showAndWaitDialog(stackPane, mainPane, false, "Sai tên đăng nhập", new requestFocusHandler());
                    break;
                case 1:
                    textField = txtMatKhau;
                    customDialog.showAndWaitDialog(stackPane, mainPane, false, "Sai mật khẩu", new requestFocusHandler());
                    break;
                case 2:
                    customDialog.showAndWaitDialog(stackPane, mainPane, true, "Đăng nhập thành công", new openMainHandler());
                    break;
                default:
                    customDialog.showDialog(stackPane, mainPane, false, "Có lỗi xảy ra, không thể đăng nhập");
                    break;
            }
        }
    }

    @FXML
    void enterToLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnLogin.fire();
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
        Share.primaryStage.setX(x - xMouse);
        Share.primaryStage.setY(y - yMouse);
    }

    @FXML
    void exit() {
        customDialog.confirmDialog(stackPane, mainPane, "Bạn có muốn thoát chương trình", new exitHandler());
    }

    public void openMain() {
        try {
            Share.primaryStage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Home/gui/Main.fxml")));
            Share.primaryStage.setScene(scene);
            Share.primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private TaiKhoanDAO tkdao;
    private CustomDialog customDialog;
    private TextField textField;
    //tọa độ con trỏ chuột
    double xMouse;
    double yMouse;
    
    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField txtTaiKhoan;

    @FXML
    private JFXPasswordField txtMatKhau;

    @FXML
    private JFXButton btnLogin;

    class openMainHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            openMain();
        }

        @Override
        public void onCancel() {

        }

    }

    class exitHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            TransitionHelper.fadeOutStage(600, Share.primaryStage);
        }

        @Override
        public void onCancel() {

        }

    }
    
    class requestFocusHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            textField.requestFocus();
        }

        @Override
        public void onCancel() {
            
        }
        
    }
}
