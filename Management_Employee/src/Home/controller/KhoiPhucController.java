package Home.controller;

import Home.DAO.KhoiPhucDAO;
import Home.helper.Share;
import Home.helper.CustomDialog;
import Home.helper.TransitionHelper;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import Home.helper.IConfirmationDialog;

public class KhoiPhucController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TransitionHelper.createTransition(0, 1000, -1*anchorPane.getPrefWidth(), anchorPane).play();
            
            kpdao = new KhoiPhucDAO();
            customDialog = new CustomDialog();
            
            txtFullBackup.setText("Vui lòng chọn File");
            txtDiffBackup.setText("Vui lòng chọn File");
            fullBackupDir = new File("C:\\Program Files\\Microsoft SQL Server\\MSSQL11.MSSQLSERVER\\MSSQL\\Backup");
            diffBackupDir = new File("C:\\Program Files\\Microsoft SQL Server\\MSSQL11.MSSQLSERVER\\MSSQL\\Backup");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void chooseFullBackupFile(ActionEvent event) {
        File temporaryFile = openFolder(fullBackupDir);
        if (temporaryFile != null) {
            fullBackupFile = temporaryFile;
            
            fullBackupDir = temporaryFile.getParentFile();
            txtFullBackup.setText(fullBackupFile.getAbsolutePath());
        }
        if (fullBackupFile == null) {
            txtFullBackup.setText("Vui lòng chọn File");
        }
    }

    @FXML
    private void chooseDiffBackupFile(ActionEvent event) {
        File temporaryFile = openFolder(diffBackupDir);
        if (temporaryFile != null) {
            diffBackupFile = temporaryFile;
            diffBackupDir = temporaryFile.getParentFile();
            txtDiffBackup.setText(diffBackupFile.getAbsolutePath());             
        }
        if (diffBackupFile == null) {
            txtDiffBackup.setText("Vui lòng chọn File");
        }
    }

    @FXML
    private void deleteFullBackupFile(ActionEvent event) {
        if (fullBackupFile != null) {
            customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn bỏ chọn File Differential Backup này", 
                    new deleteFullBackupFileHandler());
        }
    }

    @FXML
    private void deleteDiffBackupFile(ActionEvent event) {
        if (diffBackupFile != null) {
            customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn bỏ chọn File Differential Backup này", 
                    new deleteDiffBackupFileHandler());
        }

    }

    private File openFolder(File initialDirectory) {
        //loc file .bak
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Local Backup File", "*.bak");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setInitialDirectory(initialDirectory);
        return fileChooser.showOpenDialog(Share.primaryStage);
    }

    @FXML
    private void restore(ActionEvent event) {
        if (fullBackupFile == null) {
            new CustomDialog().showDialog(Share.mainPane, Share.blurPane, false, "Vui lòng chọn file full backup");
            return;
        }

        if (fullBackupFile != null && diffBackupFile == null) {
            restoreDBOnlyFullBackup();
        }

        if (fullBackupFile != null && diffBackupFile != null) {
            restoreDBWithDifferential();
        }
    }

    private void restoreDBOnlyFullBackup() {
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn khôi phục Cơ sở dữ liệu chỉ với file Full backup", 
                new restoreDBOnlyFullBackupHandler());
    }

    private void restoreDBWithDifferential() {
        customDialog.confirmDialog(Share.mainPane, Share.blurPane, "Bạn chắc chắn muốn khôi phục Cơ sở dữ liệu với file Differential backup", 
                new restoreDBWithDifferentialHandler());
    }

    private KhoiPhucDAO kpdao;
    private CustomDialog customDialog;
    private File fullBackupFile = null;
    private File fullBackupDir = null;
    private File diffBackupFile = null;
    private File diffBackupDir = null;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txtDiffBackup;

    @FXML
    private TextField txtFullBackup;
    
    class deleteFullBackupFileHandler implements IConfirmationDialog{

        @Override
        public void onConfirm() {
            fullBackupFile = null;
            txtFullBackup.setText("Vui lòng chọn File");
        }

        @Override
        public void onCancel() {
            
        }
     
    }
    
    class deleteDiffBackupFileHandler implements IConfirmationDialog{

        @Override
        public void onConfirm() {
            diffBackupFile = null;
            txtDiffBackup.setText("Vui lòng chọn File");
        }

        @Override
        public void onCancel() {
            
        }
     
    }
    
    class restoreDBOnlyFullBackupHandler implements IConfirmationDialog{

        @Override
        public void onConfirm() {
            try {
                if (kpdao.restoreDBOnlyFullBackup(txtFullBackup.getText())) {
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Khôi phục thành công");
                } else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Khôi phục thất bại. Vui lòng kiểm tra lại");
                ex.printStackTrace();
            }
        }

        @Override
        public void onCancel() {
            
        }
     
    }
    
    class restoreDBWithDifferentialHandler implements IConfirmationDialog{

        @Override
        public void onConfirm() {
            try {
                if (kpdao.restoreDBWithDifferential(txtFullBackup.getText(), txtDiffBackup.getText())) {
                    customDialog.showDialog(Share.mainPane, Share.blurPane, true, "Khôi phục thành công");
                } else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                customDialog.showDialog(Share.mainPane, Share.blurPane, false, "Khôi phục thất bại. Vui lòng kiểm tra lại");
                ex.printStackTrace();
            }
        }

        @Override
        public void onCancel() {
            
        }
     
    }
    
}
