package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.dmscooltech.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GlobalFormController implements Initializable {

    public AnchorPane mainpane;
    @FXML
    private Pane pagingPane;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnRepair;

    @FXML
    private JFXButton btnStoke;

    @FXML
    private JFXButton btnVehicle;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "customer_form.fxml");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "dashboard_form.fxml");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "employee_form.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation( "login_form.fxml",mainpane);
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "placeOrder_form.fxml");
    }

    @FXML
    void btnStokeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "item_from.fxml");
    }

    @FXML
    void btnVehicleOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "vehicle_form.fxml");
    }

    @FXML
    void btnRepairOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "repair_form.fxml");
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

    public void imgUserOnAction(MouseEvent mouseEvent) {

    }
}
