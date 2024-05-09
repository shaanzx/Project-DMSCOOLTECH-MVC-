package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.DashBoard;
import lk.ijse.dmscooltech.model.User;
import lk.ijse.dmscooltech.repository.UserRepo;
import lk.ijse.dmscooltech.util.DataValidateController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {

    @FXML
    private AnchorPane registerPane;

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
    private Label passwordValidate;

    @FXML
    private Label userNameValidate;

    UserRepo userRepo = new UserRepo();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtUserId.setText(userRepo.generateUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws SQLException {
        String uId = txtUserId.getText();
        String uName = txtUserName.getText();
        String password = txtPassword.getText();
        String rePw = txtRePassword.getText();

        User user = new User(uId, uName, password);

        if(DataValidateController.validateUserName(txtUserName.getText())) {
            userNameValidate.setText("");
            if (DataValidateController.validateUserPassword(txtPassword.getText())) {
                passwordValidate.setText("");
                try {
                    boolean isSaved = UserRepo.userSave(user);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Successfully Saved!").show();
                        txtUserId.clear();
                        txtUserName.clear();
                        txtPassword.clear();
                        txtRePassword.clear();
                        txtUserId.requestFocus();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Already exists").show();
                }
            } else {
                passwordValidate.setText("Input any character and try again!");
            }
        }else{
            userNameValidate.setText("Invalid User Name and try again!");
        }
    }



/*    private void userSave(String uId, String uName, String password) {
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
    }*/
}
