package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.dmscooltech.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GlobalFormController implements Initializable {

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgLogOut;

    @FXML
    private Pane pagingPane;

    @FXML
    private JFXButton txtCustomer;

    @FXML
    private JFXButton txtDashboard;

    @FXML
    private JFXButton txtEmployee;

    @FXML
    private JFXButton txtOrder;

    @FXML
    private Text txtShowUserName;

    @FXML
    private JFXButton txtStoke;

    @FXML
    void btnCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "dashboard_form.fxml");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnGotoHomePageOnAction(MouseEvent event) {

    }

    @FXML
    void btnLogoutOnAction(MouseEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnStokeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "item_from.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pagingPane.setVisible(true);
        try {
            Navigation.switchPaging(pagingPane, "dashboard_form.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
