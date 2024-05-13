package lk.ijse.dmscooltech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.dmscooltech.repository.UserRepo;
import lk.ijse.dmscooltech.util.DataValidateController;

import java.io.IOException;
import java.sql.SQLException;

public class NewLoginFormController {

    @FXML
    private Pane ancLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private CheckBox txtSave;

    @FXML
    public TextField txtUserId;

    public String userId;

    @FXML
    private Label passwordValidate;

    @FXML
    private Label userIdValidate;

    private static NewLoginFormController controller;

    public NewLoginFormController(){
        controller = this;
    }
    public static NewLoginFormController getInstance(){
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
        String userId = txtUserId.getText();
        String pw = txtPassword.getText();

        if(DataValidateController.validateUserName(userId)) {
            userIdValidate.setText("");
          /*  if (DataValidateController.validateUserPassword(txtPassword.getText())) {
                passwordValidate.setText("");*/
                try {
                    UserRepo.checkUserNamePassword(userId, pw, ancLogin);
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "User Id Or password doesn't match.Try aging!").show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
      /*      } else {
                passwordValidate.setText("Invalid Password");
            }*/
        }else{
            userIdValidate.setText("Invalid User Id");
        }
    }

    public void gotoDashBoard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/global_new_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
      //  stage.initStyle(StageStyle.UNDECORATED);
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

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnSignInAction(event);
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

}
