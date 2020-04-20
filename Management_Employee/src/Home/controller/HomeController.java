package Home.controller;

import Home.DAO.BangLuongDAO;
import Home.DAO.ChamCongDAO;
import Home.DAO.NhanVienDAO;
import Home.helper.FormatNumber;
import Home.helper.TransitionHelper;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable, Runnable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TransitionHelper.createTransition(300, 1000, -1 * anchorPane.getPrefWidth(), anchorPane).play();
            
            nvdao = new NhanVienDAO();
            ccdao = new ChamCongDAO();
            bldao = new BangLuongDAO();
            
            runThread();
            loadCboNam();
            yearStatistic = cboNam.getSelectionModel().getSelectedItem();
            loadChartTangTruongNV();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Khởi tạo và chạy Thread
    private void runThread() {
        Thread thread = new Thread(this);
        thread.start();
    }

    private void loadCboNam() {
        cboNam.setItems(ccdao.getListYear());
        cboNam.getSelectionModel().select(0);

        //Sự kiện khi chọn combobox
        cboNam.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue ov, Integer oldValue, Integer newValue) {
                yearStatistic = newValue;
                runThread();
                loadChartTangTruongNV();
            }
        });
    }

    private void loadChartTangTruongNV() {
        chartTangTruongNV.getData().clear();
        chartTangTruongNV.setTitle("Biểu đồ số lượng nhân viên năm " + yearStatistic);

        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Giám đốc");
        series1.setData(nvdao.getSLNVTheoPBVaThang("GD", yearStatistic));

        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Kỹ thuật");
        series2.setData(nvdao.getSLNVTheoPBVaThang("IT", yearStatistic));

        XYChart.Series series3 = new XYChart.Series<>();
        series3.setName("Kế toán");
        series3.setData(nvdao.getSLNVTheoPBVaThang("KT", yearStatistic));

        XYChart.Series series4 = new XYChart.Series<>();
        series4.setName("Marketing");
        series4.setData(nvdao.getSLNVTheoPBVaThang("MK", yearStatistic));

        XYChart.Series series5 = new XYChart.Series<>();
        series5.setName("Nhân sự");
        series5.setData(nvdao.getSLNVTheoPBVaThang("NS", yearStatistic));

        XYChart.Series series6 = new XYChart.Series<>();
        series6.setName("Bán hàng");
        series6.setData(nvdao.getSLNVTheoPBVaThang("SL", yearStatistic));

        XYChart.Series series7 = new XYChart.Series<>();
        series7.setName("Tất cả");
        series7.setData(nvdao.getSLNVTheoPBVaThang(null, yearStatistic));

        //add series to chart
        chartTangTruongNV.getData().addAll(series1, series2, series3, series4, series5, series6, series7);
    }

    private NhanVienDAO nvdao;
    private ChamCongDAO ccdao;
    private BangLuongDAO bldao;
    private int yearStatistic;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label lblThuNhap;

    @FXML
    private Label lblSoGIoLamViec;

    @FXML
    private Label lblSLNhanVien;

    @FXML
    private LineChart chartTangTruongNV;

    @FXML
    private JFXComboBox<Integer> cboNam;

    //Thread 
    @Override
    public void run() {
        try {
            Thread.sleep(1500);
            double plusNum1 = nvdao.getSLNVTheoPBVaNam(yearStatistic)/ 20.0;
            double plusNum2 = bldao.getTongTienLuongTrongNam(yearStatistic) / 20.0;
            double plusNum3 = ccdao.getSoGioLamViecTrongNam(yearStatistic) / 20.0;
            for (int i = 1; i <= 20; i++) {
                int value1 = (int) (i * plusNum1);
                double value2 = i * plusNum2;
                int value3 = (int) (i * plusNum3);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblSLNhanVien.setText(value1 + "");
                        lblThuNhap.setText(FormatNumber.formatDouble(value2, "###,###.###"));
                        lblSoGIoLamViec.setText(value3 + "");

                    }
                });

                Thread.sleep(40);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}
