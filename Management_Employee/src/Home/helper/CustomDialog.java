package Home.helper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class CustomDialog {

    public void showDialog(StackPane stackPane, Pane blurPane, boolean successMessage, String message) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OK");
        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP, true);

        dialog.getStylesheets().add(getClass().getResource("/Libraries/CssMenu.css").toExternalForm());
        button.getStyleClass().addAll("btn", "btn-primary");
        button.setPrefSize(80, 30);
        button.setOnAction((event) -> {
            dialog.close();
        });

        dialogLayout.setPrefSize(400, 80);

        Text text = new Text(message);
        text.setWrappingWidth(380);
        
        text.setStyle("-fx-font: 12px System;");
        text.setStyle("-fx-font-weight: Bold;");
        if (!successMessage) {
            text.setStyle("-fx-fill: #dc3545");
        }
        dialogLayout.setHeading(text);
        dialogLayout.setActions(button);

        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            blurPane.setEffect(null);
            blurPane.setDisable(false);
        });
        dialog.show();
        blurPane.setEffect(blur);
        blurPane.setDisable(true);
    }

    public void showAndWaitDialog(StackPane stackPane, Pane blurPane, boolean successMessage, String message, IConfirmationDialog confirm) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OK");
        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP, true);

        dialog.getStylesheets().add(getClass().getResource("/Libraries/CssMenu.css").toExternalForm());
        button.getStyleClass().addAll("btn", "btn-primary");
        button.setPrefSize(80, 30);
        button.setOnAction((event) -> {
            dialog.close();
        });

        dialogLayout.setPrefSize(400, 80);

        Text text = new Text(message);
        text.setWrappingWidth(380);
        
        text.setStyle("-fx-font: 12px System;");
        text.setStyle("-fx-font-weight: Bold;");
        if (!successMessage) {
            text.setStyle("-fx-fill: #dc3545");
        }
        dialogLayout.setHeading(text);
        dialogLayout.setActions(button);

        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            blurPane.setEffect(null);
            blurPane.setDisable(false);
            confirm.onConfirm();
        });
        dialog.show();
        blurPane.setEffect(blur);
        blurPane.setDisable(true);
    }

    public void confirmDialog(StackPane stackPane, Pane blurPane, String message, IConfirmationDialog confirm) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton yesButton = new JFXButton("Xác nhận");
        JFXButton cancelButton = new JFXButton("Hủy");

        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP, false);
        dialog.getStylesheets().add(getClass().getResource("/Libraries/CssMenu.css").toExternalForm());
        yesButton.getStyleClass().addAll("btn", "btn-primary");
        yesButton.setPrefSize(110, 30);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialog.close();
                confirm.onConfirm();
            }
        });

        cancelButton.getStyleClass().addAll("btn", "btn-danger");
        cancelButton.setPrefSize(80, 30);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialog.close();
                confirm.onCancel();
            }
        });

        dialogLayout.setPrefSize(400, 80);
        Text text = new Text(message);
        text.setWrappingWidth(380);      
        text.setStyle("-fx-font: 12px System;");
        text.setStyle("-fx-font-weight: Bold;");
        dialogLayout.setHeading(text);
        dialogLayout.setActions(yesButton, cancelButton);

        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            blurPane.setEffect(null);
            blurPane.setDisable(false);
        });

        dialog.show();
        blurPane.setEffect(blur);
        blurPane.setDisable(true);
    }

}
