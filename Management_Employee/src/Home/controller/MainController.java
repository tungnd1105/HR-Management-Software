package Home.controller;

import Home.helper.CustomDialog;
import Home.helper.IConfirmationDialog;
import Home.helper.Share;
import Home.helper.TransitionHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customDialog = new CustomDialog();
        Share.primaryStage.centerOnScreen();
        Share.mainPane = mainPane;
        Share.blurPane = blurPane;
        clipChildren(contentPane);
        setGUIHome();
        accessPermission();
    }

    private void accessPermission() {
        if (Share.MAPB != null) {
            lblToChuc.setDisable(true);
            lblTaiKhoan.setDisable(true);
            lblKhoiPhuc.setDisable(true);
        }
    }

    @FXML
    public void setGUIHome() {
        setChildForContentPane("/Home/gui/Home.fxml", lblHome);
    }

    @FXML
    public void setGUINhanVien() {
        setChildForContentPane("/Home/gui/NhanVien.fxml", lblNhanVien);
    }

    @FXML
    public void setGUIToChuc() {
        setChildForContentPane("/Home/gui/ToChuc.fxml", lblToChuc);
    }

    @FXML
    public void setGUIChamCong() {
        setChildForContentPane("/Home/gui/ChamCong.fxml", lblChamCong);
    }

    @FXML
    public void setGUIBangLuong() {
        setChildForContentPane("/Home/gui/BangLuong.fxml", lblBangLuong);
    }

    @FXML
    public void setGUIKhoiPhuc() {
        setChildForContentPane("/Home/gui/KhoiPhuc.fxml", lblKhoiPhuc);
    }

    @FXML
    public void setGUITaikhoan() {
        setChildForContentPane("/Home/gui/TaiKhoan.fxml", lblTaiKhoan);
    }

    @FXML
    public void setGUIGioithieu() {
        setChildForContentPane("/Home/gui/GioiThieu.fxml", lblGioiThieu);
    }

    @FXML
    public void openGUIDoimatkhau() {
        openSecondStage("/Home/gui/DoiMatKhau.fxml");
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
    public void logOut() {
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn có muốn đăng xuất", new logoutHandler());
    }

    private void clipChildren(Pane pane) {
        final Rectangle outputClip = new Rectangle(0, 0, pane.getWidth(), pane.getHeight());
        pane.setClip(outputClip);
        pane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    private void setChildForContentPane(String fxmlPath, Label label) {
        try {
            contentPane.getChildren().clear();
            contentPane.getChildren().add(FXMLLoader.load(getClass().getResource(fxmlPath)));
            removeAllStyleClass(leftPane, "hover_menuActive");
            label.getStyleClass().add("hover_menuActive");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openSecondStage(String fxmlPath) {
        try {
            Stage secondStage = new Stage();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlPath)));

            secondStage.setScene(scene);
            secondStage.setResizable(false);
            secondStage.initStyle(StageStyle.TRANSPARENT);
            secondStage.setAlwaysOnTop(true);
            secondStage.show();

            secondStage.getScene().getRoot().setOpacity(0);
            TransitionHelper.fadeInStage(1000, secondStage);

            Share.secondStage = secondStage;
            Share.mainPane.setDisable(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void removeAllStyleClass(Pane pane, String className) {
        try {
            for (Node node : pane.getChildren()) {
                if (node instanceof Label) {
                    ((Label) node).getStyleClass().remove(className);
                }
                if (node instanceof Pane) {
                    removeAllStyleClass((Pane) node, className);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void closeWindow() {
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn có muốn thoát chương trình", new exitHandler());
    }

    @FXML
    public void minimizeWindow() {
        Share.primaryStage.setIconified(true);
    }

    //tọa độ con trỏ chuột
    double xMouse;
    double yMouse;

    private CustomDialog customDialog;

    @FXML
    private StackPane mainPane;

    @FXML
    private BorderPane blurPane;

    @FXML
    private StackPane contentPane;

    @FXML
    private VBox leftPane;

    @FXML
    HBox AccountBox;

    @FXML
    Label lblHome;

    @FXML
    private Label lblNhanVien;

    @FXML
    private Label lblToChuc;

    @FXML
    private Label lblBangLuong;

    @FXML
    private Label lblChamCong;

    @FXML
    private Label lblTaiKhoan;

    @FXML
    private Label lblKhoiPhuc;

    @FXML
    private Label lblGioiThieu;

    @FXML
    Label lblDoiMatKhau;

    @FXML
    Label lblDangXuat;

    @FXML
    Label lblUsername;

    class exitHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            TransitionHelper.fadeOutStage(600, Share.primaryStage);
        }

        @Override
        public void onCancel() {

        }

    }

    class logoutHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            try {
                Share.logOut();
                Share.primaryStage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Home/gui/Login.fxml")));
                Share.primaryStage.setScene(scene);
                Share.primaryStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onCancel() {

        }

    }
}
