package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.Employee;
import lk.ijse.dmscooltech.model.Vehicle;
import lk.ijse.dmscooltech.model.tm.RepairTm;
import lk.ijse.dmscooltech.repository.CustomerRepo;
import lk.ijse.dmscooltech.repository.EmployeeRepo;
import lk.ijse.dmscooltech.repository.VehicleRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
    private TextField txtRepairCost;

    @FXML
    private TextField txtRepairDescription;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<RepairTm> tblRepairDetails;

    @FXML
    private TextField txtQty;

    VehicleRepo vehicleRepo = new VehicleRepo();

    EmployeeRepo employeeRepo = new EmployeeRepo();

    private ObservableList<RepairTm> tmRepairList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getVehicleNo();
        getEmployeeId();
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
        String vehicleNo = cmbVehicleNo.getValue();
        String description = txtRepairDescription.getText();
        String repairDate = String.valueOf(dpRepairDate.getValue());
        double repairCost = Double.parseDouble(txtRepairCost.getText());
        JFXButton btnDelete = new JFXButton("Delete");
        btnDelete.setCursor(Cursor.HAND);

        btnDelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this item?", yes, no).showAndWait();
            if(type.orElse(no) == yes){
                int row = tblRepairDetails.getSelectionModel().getSelectedIndex();
                tmRepairList.remove(row);
                tblRepairDetails.refresh();
                double netAmount = repairCost;
            }
        });
     /*   for (int i = 0; i < tmRepairList.size(); i++) {
            if(vehicleNo.equals(colVehicleNo.getCellData(i))){
            }
        }*/
        RepairTm tm = new RepairTm(vehicleNo, description, repairCost, repairDate, btnDelete);
        tmRepairList.add(tm);
        tblRepairDetails.setItems(tmRepairList);
    }

    @FXML
    void btnConfirmBillOnAction(ActionEvent event) {

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
