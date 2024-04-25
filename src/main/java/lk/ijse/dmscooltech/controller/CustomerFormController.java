package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.repository.CustomerRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox<?> cmbUserId;

    @FXML
    private TableColumn<String,String> colCustomerAddress;

    @FXML
    private TableColumn<String,String> colCustomerEmail;

    @FXML
    private TableColumn<String,String> colCustomerId;

    @FXML
    private TableColumn<String,String> colCustomerName;

    @FXML
    private TableColumn<String,String> colCustomerTel;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblCustomer;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusEmail;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCusTel;

    @FXML
    private Label txtDate;

    CustomerRepo customerRepo = new CustomerRepo();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtCusId.setText(CustomerRepo.generateNextCustomerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnCusSaveOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String tel = txtCusTel.getText();
        String email = txtCusEmail.getText();
        String userId = (String) cmbUserId.getValue();

        Customer customer = new Customer(id, name, address, tel, email, userId);
        try {
            boolean isSaved = customerRepo.saveCustomer(customer);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnCusUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtCusDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void cmbUserIdOnAction(ActionEvent event) {

    }
    @FXML
    void btnAddVehicleOnAction(ActionEvent event) {

    }
}
