package Home.helper;

import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Validate {

    //Hiện thông báo lỗi của JFX
    public static RequiredFieldValidator createValidatorJFX(String errMessage) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(errMessage);
        validator.setIcon(new Picture().createImageView("warning16px.png"));
        return validator;
    }

    //Kiểm lỗi TextField not null
    public static boolean isNull(TextField txt, String errMessage) {
        textField = txt;
        try {
            if (textField.getText().trim().equals("")) {
                throw new Exception();
            }
        } catch (Exception e) {
            new CustomDialog().showAndWaitDialog(Share.mainPane, Share.blurPane, false, errMessage, new textfieldFocusHandler());
            return true;
        }
        return false;
    }
    
     public static boolean isNull(TextField txt, String errMessage, StackPane stackPane, Pane blurPane) {
        textField = txt;
        try {
            if (textField.getText().trim().equals("")) {
                throw new Exception();
            }
        } catch (Exception e) {
            new CustomDialog().showAndWaitDialog(stackPane, blurPane, false, errMessage, new textfieldFocusHandler());
            return true;
        }
        return false;
    }

    //Kiểm lỗi Combobox chưa được chọn giá trị
    public static boolean isNotSelected(ComboBox cbo, String errMessage) {
        try {
            if (cbo.getSelectionModel().getSelectedIndex() == -1) {
                throw new Exception();
            }
        } catch (Exception e) {
            new CustomDialog().showDialog(Share.mainPane, Share.blurPane, false, errMessage);
            return true;
        }

        return false;
    }

    //Kiểm lỗi TextField không đúng với mẫu
    public static boolean isNotMatches(TextField txt, String regex, String errMessage) {
        textField = txt;
        try {
            if (!textField.getText().matches(regex)) {
                throw new Exception();
            }
        } catch (Exception e) {
            new CustomDialog().showAndWaitDialog(Share.mainPane, Share.blurPane, false, errMessage, new textfieldFocusHandler());
            return true;
        }

        return false;
    }
    
    private static TextField textField;

    static class textfieldFocusHandler implements IConfirmationDialog {

        @Override
        public void onConfirm() {
            textField.requestFocus();
        }

        @Override
        public void onCancel() {
            
        }
        
    }

}
