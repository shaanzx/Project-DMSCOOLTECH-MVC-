package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.tm.CustomerTm;
import lk.ijse.dmscooltech.repository.CustomerRepo;
import lk.ijse.dmscooltech.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private TableView<CustomerTm> tblCustomer;

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
    private Label lblDate;

    CustomerRepo customerRepo = new CustomerRepo();

    private List<Customer> customerList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtCusId.setText(customerRepo.generateNextCustomerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerList = getAllCustomer();
        setCellValueFactory();
        loadCustomerTable();
        setDate();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    private List<Customer>  getAllCustomer() {
        List<Customer> customerList = null;
        try{
            customerList = customerRepo.getCustomer();
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return customerList;
    }

    private void loadCustomerTable() {
        customerRepo =new CustomerRepo();
        ObservableList<CustomerTm> tmCustomerList = FXCollections.observableArrayList();
        try{
            List<Customer> customersList = customerRepo.getCustomer();
            for(Customer customer : customersList) {
                CustomerTm customertm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getTel(),
                        customer.getEmail()
                );
                tmCustomerList.add(customertm);
            }
            tblCustomer.setItems(tmCustomerList);
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    void btnCusSaveOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String tel = txtCusTel.getText();
        String email = txtCusEmail.getText();
        String userId = LoginFormController.getInstance().userId;

        Customer customer = new Customer(id, name, address, tel, email, userId);
        try {
            boolean isSaved = customerRepo.saveCustomer(customer);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Saved").show();
                loadCustomerTable();
                clearTextFields();
                CustomerRepo.generateNextCustomerId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearTextFields() {
        txtCusId.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusTel.clear();
        txtCusEmail.clear();
    }

    @FXML
    void btnCusUpdateOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String tel = txtCusTel.getText();
        String email = txtCusEmail.getText();
        String userId = LoginFormController.getInstance().userId;

        Customer customer = new Customer(id, name, address, tel, email, userId);
        try {
            boolean isUpdated = customerRepo.updateCustomer(customer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Updated").show();
                loadCustomerTable();
                clearTextFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchCustomerOnAction(ActionEvent event) {
        String customerId = txtCusId.getText();
        try {
            Customer customer = customerRepo.searchCustomer(customerId);
            if (customer != null) {
                txtCusId.setText(customer.getId());
                txtCusName.setText(customer.getName());
                txtCusAddress.setText(customer.getAddress());
                txtCusTel.setText(customer.getTel());
                txtCusEmail.setText(customer.getEmail());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnCusDeleteOnAction(ActionEvent event) {
        String id = txtCusId.getText();

        try {
            boolean isDeleted = customerRepo.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Deleted").show();
                loadCustomerTable();
                clearTextFields();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbUserIdOnAction(ActionEvent event) {
    }
    @FXML
    void btnAddVehicleOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"vehicle_form.fxml");
    }

    @FXML
    void tblCustomerClick(MouseEvent event) {
        TablePosition tp = tblCustomer.getSelectionModel().getSelectedCells().get(0);
        int row = tp.getRow();
        ObservableList<TableColumn<CustomerTm,?> > columns = tblCustomer.getColumns();

        txtCusId.setText(columns.get(0).getCellData(row).toString());
        txtCusName.setText(columns.get(1).getCellData(row).toString());
        txtCusAddress.setText(columns.get(2).getCellData(row).toString());
        txtCusTel.setText(columns.get(3).getCellData(row).toString());
        txtCusEmail.setText(columns.get(4).getCellData(row).toString());
        tblCustomer.setCursor(Cursor.HAND);
    }
}
