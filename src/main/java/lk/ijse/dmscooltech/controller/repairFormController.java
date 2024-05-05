package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.Employee;
import lk.ijse.dmscooltech.model.Item;
import lk.ijse.dmscooltech.model.Vehicle;
import lk.ijse.dmscooltech.repository.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class repairFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colIetmQty;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemUnitPrice;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colRepairCost;

    @FXML
    private TableColumn<?, ?> colRepairDate;

    @FXML
    private TableColumn<?, ?> colRepairDescription;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private DatePicker dpRepairDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemQtyOnHand;

    @FXML
    private Label lblNetAmount;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblRepairDetails;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtRepairCost;

    @FXML
    private TextField txtRepairDescription;

    PaymentRepo paymentRepo = new PaymentRepo();
    VehicleRepo vehicleRepo = new VehicleRepo();
    EmployeeRepo employeeRepo = new EmployeeRepo();
    OrderRepo orderRepo = new OrderRepo();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblPaymentId.setText(paymentRepo.generatePaymentId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getVehicleNo();
        getEmployeeId();
        getItemCode();

    }

    private void getItemCode() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        try{
            List<String> idList = OrderRepo.getItemCodes();
            for (String id : idList) {
                itemCodeList.add(id);
            }
            cmbItemCode.setItems(itemCodeList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeId() {
        ObservableList<String> employeeList = FXCollections.observableArrayList();
        try {
            List<String> employeeIdList = employeeRepo.getEmployeeId();
            for(String id : employeeIdList){
                employeeList.add(id);
            }
            cmbEmployeeId.setItems(employeeList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getVehicleNo() {
        ObservableList<String> vehicleList = FXCollections.observableArrayList();

        try {
            List<String> vehicleNoList = vehicleRepo.getVehicleNo();
            for(String no : vehicleNoList){
                vehicleList.add(no);
            }
            cmbVehicleNo.setItems(vehicleList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewVehicleOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnConfirmRepairBillOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewRepairDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbEmployeeIdOnAction(ActionEvent event) {
        String employeeId = cmbEmployeeId.getValue();

        try {
            Employee employee = EmployeeRepo.searchEmployee(employeeId);
            lblEmployeeName.setText(employee.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {
        String itemCode = cmbItemCode.getValue();
        try {
            Item item = ItemRepo.searchByItemCode(itemCode);
            if(item != null){
                lblItemName.setText(item.getDescription());
                lblItemQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
                lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbVehicleNoOnAction(ActionEvent event) {
        String vehicleNo = cmbVehicleNo.getValue();
        try {
            Vehicle vehicle = vehicleRepo.searchVehicle(vehicleNo);
            Customer customer = CustomerRepo.searchCustomer(vehicle.getCustomerId());
            String name = customer.getName();
            lblCustomerName.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
}
