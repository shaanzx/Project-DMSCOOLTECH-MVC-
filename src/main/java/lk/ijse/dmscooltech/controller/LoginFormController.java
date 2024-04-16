package lk.ijse.dmscooltech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dmscooltech.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private CheckBox txtSave;

    @FXML
    private TextField txtUserId;

    @FXML
    void btnForgetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveToDeviceOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignInAction(ActionEvent event) throws IOException {
        String userId = txtUserId.getText();
        String pw = txtPassword.getText();

        try {
            checkUserNamePassword(userId, pw);
        } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"User Id Or password doesn't match.Try aging!").show();
        }
    }

    private void checkUserNamePassword(String userId, String pw) throws SQLException, IOException {
        String sqlQuery = "SELECT uId, uPassword FROM user WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setObject(1,userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            String psw = resultSet.getString(2);
            if(psw.equals(pw)){
                gotoDashBoard();
            }else {
                new Alert(Alert.AlertType.ERROR,"Incorrect Password!");
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Incorrect user ID.Check and try again.");
        }
    }

    private void gotoDashBoard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/global_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
        stage.show();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Register Form");
        stage.show();
    }
}
