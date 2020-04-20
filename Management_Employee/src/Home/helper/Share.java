
package Home.helper;

import Home.controller.BangLuongController;
import Home.controller.NhanVienController;
import Home.controller.ToChucController;
import Home.model.TaiKhoan;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Share {
    
    public static Stage primaryStage = null;
    public static Stage secondStage = null;
    public static StackPane mainPane;
    public static BorderPane blurPane;
    public static NhanVienController nvController;
    public static ToChucController tcController;
    public static BangLuongController blController;
    
    /**
     * Đối tượng này chứa thông tin người sử dụng sau khi đăng nhập
     */
    public static TaiKhoan USER = null;

    /**
     * Đối tượng này chứa mã Phòng ban mà tài khoản quản lý
     */
    public static Object MAPB = null;
    
    /**
     * Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
     */
    public static void logOut() {
        Share.USER = null;
    }
}
