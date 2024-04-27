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
import lk.ijse.dmscooltech.repository.UserRepo;
import lk.ijse.dmscooltech.util.Navigation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private AnchorPane ancLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private CheckBox txtSave;

    @FXML
    public TextField txtUserId;

    public String userId;

    private static LoginFormController controller;

    public LoginFormController(){
        controller = this;
    }
    public static LoginFormController getInstance(){
        return controller;
    }

    @FXML
    void btnForgetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveToDeviceOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignInAction(ActionEvent event) {
        userId = txtUserId.getText();
        String pw = txtPassword.getText();

        try {
            UserRepo.checkUserNamePassword(userId, pw, ancLogin);
        } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"User Id Or password doesn't match.Try aging!").show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* private void checkUserNamePassword(String userId, String pw) throws SQLException, IOException {
        String sqlQuery = "SELECT uId, uPassword FROM user WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setObject(1,userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            String psw = resultSet.getString(2);
            if(psw.equals(pw)){
                ancLogin.getScene().getWindow().hide();
                gotoDashBoard();
            }else {
                new Alert(Alert.AlertType.ERROR,"Incorrect Password!").show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Incorrect user ID.Check and try again.").show();
        }
    }
*/
    public void gotoDashBoard() throws IOException {
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
        stage.centerOnScreen();
        stage.setTitle("Register Form");
        stage.show();
    }
}
