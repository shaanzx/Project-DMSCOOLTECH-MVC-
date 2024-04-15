package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.dmscooltech.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormControoler {

    @FXML
    private JFXButton txtConfirm;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRePassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        String uId = txtUserId.getText();
        String uName = txtUserName.getText();
        String password = txtPassword.getText();
        String rePw = txtRePassword.getText();

        if(!password.equals(rePw)) {
            new Alert(Alert.AlertType.ERROR, "Password doesn't match.Try again!").show();
        } else {
            userSave(uId, uName, password);
        }
    }



    private void userSave(String uId, String uName, String password) {
        try {
            String sql = "INSERT INTO user VALUES(?, ?, ?)";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,uId);
            preparedStatement.setObject(2,uName);
            preparedStatement.setObject(3,password);

            if(preparedStatement.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User password Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING,"Already exists").show();
        }
    }
}
