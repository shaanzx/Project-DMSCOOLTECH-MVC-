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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.Employee;
import lk.ijse.dmscooltech.model.Vehicle;
import lk.ijse.dmscooltech.repository.CustomerRepo;
import lk.ijse.dmscooltech.repository.EmployeeRepo;
import lk.ijse.dmscooltech.repository.VehicleRepo;

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
    private TableColumn<?, ?> colRepairDescription;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colRepairCost;

    @FXML
    private TableColumn<?, ?> colRepairDate;

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
    private Label lblNetAmount;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblRepairCost;

    @FXML
    private Label lblRepairDescription;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblRepairDetails;

    @FXML
    private TextField txtQty;

    VehicleRepo vehicleRepo = new VehicleRepo();
    EmployeeRepo employeeRepo = new EmployeeRepo();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getVehicleNo();
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

    private void setCellValueFactory() {
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("description"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    @FXML
    void btnAddNewVehicleOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnConfirmBillOnAction(ActionEvent event) {

    }

    @FXML
    void cmbEmployeeIdOnAction(ActionEvent event) {
        String employeeId = cmbEmployeeId.getValue();

      //  Employee employee = EmployeeRepo.searchEmployee(employeeId);
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

    }
}
