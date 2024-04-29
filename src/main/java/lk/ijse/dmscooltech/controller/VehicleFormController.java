package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.Vehicle;
import lk.ijse.dmscooltech.model.tm.CustomerTm;
import lk.ijse.dmscooltech.model.tm.VehicleTm;
import lk.ijse.dmscooltech.repository.CustomerRepo;
import lk.ijse.dmscooltech.repository.VehicleRepo;
import lk.ijse.dmscooltech.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VehicleFormController implements Initializable {

    @FXML
    private TextField txtVehicleType;

    @FXML
    private JFXComboBox<String> cmbCusId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colVehicleModel;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableColumn<?, ?> colVehicleType;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtVehicleModel;

    @FXML
    private TextField txtVehicleNo;

    VehicleRepo vehicleRepo = new VehicleRepo();

    private List<Vehicle> vehicleList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        vehicleList = getAllVehicle();
        setCellValueFactory();
        loadVehicleTable();
        getCustomerId();

    }

    private List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicleList = null;
        try {

            vehicleList = vehicleRepo.getVehicle();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return vehicleList;
    }

    private void loadVehicleTable() {
        vehicleRepo = new VehicleRepo();
        ObservableList<VehicleTm> tmVehicleLIst  = FXCollections.observableArrayList();

        try {
            List<Vehicle> vehicleList = vehicleRepo.getVehicle();
            for(Vehicle vehicle : vehicleList){
                VehicleTm vehicleTm = new VehicleTm(
                        vehicle.getVehicleNo(),
                        vehicle.getModel(),
                        vehicle.getType(),
                        vehicle.getCustomerId()
                );
                tmVehicleLIst.add(vehicleTm);
            }
            tblVehicle.setItems(tmVehicleLIst);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void getCustomerId() {
        ObservableList<String> customerList = FXCollections.observableArrayList();
        try {
            List<String> idList = VehicleRepo.getCustomerId();
            for (String id : idList) {
                customerList.add(id);
            }
            cmbCusId.setItems(customerList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(now.toString());
    }

    private void clearTextFields(){
        txtVehicleNo.clear();
        txtVehicleModel.clear();
        txtVehicleType.clear();
        cmbCusId.setValue("");
        lblCustomerName.setText("");
    }

    @FXML
    void btnAddNewCustomerOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "customer_form.fxml");
    }

    @FXML
    void btnVehicleDeleteOnAction(ActionEvent event) {
        String vehicleNo = txtVehicleNo.getText();

        try {
            boolean isDeleted = vehicleRepo.deleteVehicle(vehicleNo);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Deleted").show();
                loadVehicleTable();
                clearTextFields();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnVehicleSaveOnAction(ActionEvent event) {
        String vehicleNo = txtVehicleNo.getText();
        String model = txtVehicleModel.getText();
        String type = txtVehicleType.getText();
        String customerId = cmbCusId.getValue();

        Vehicle vehicle = new Vehicle(vehicleNo, model, type, customerId);
        try{
            boolean isSaved = vehicleRepo.saveVehicle(vehicle);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Vehicle is Saved!").show();
                loadVehicleTable();
                clearTextFields();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnVehicleUpdateOnAction(ActionEvent event) {
        String vehicleNo = txtVehicleNo.getText();
        String model = txtVehicleModel.getText();
        String type = txtVehicleType.getText();
        String customerId = cmbCusId.getValue();

        Vehicle vehicle = new Vehicle(vehicleNo,model,type,customerId);
        try {
            boolean isUpdated = vehicleRepo.updateVehicle(vehicle);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Updated").show();
                loadVehicleTable();
                clearTextFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchVehicleOnAction(ActionEvent event) {
        String vehicleNo = txtVehicleNo.getText();
        try {
            Vehicle vehicle = vehicleRepo.searchVehicle(vehicleNo);
            if (vehicle != null) {
                txtVehicleNo.setText(vehicle.getVehicleNo());
                txtVehicleModel.setText(vehicle.getModel());
                txtVehicleType.setText(vehicle.getType());
                cmbCusId.setValue(vehicle.getCustomerId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String customerId = cmbCusId.getValue();
        try {
            Customer customer = CustomerRepo.searchCustomer(customerId);
            lblCustomerName.setText(customer.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tblVehicleClickOnAction(ActionEvent event) {
        TablePosition tp = tblVehicle.getSelectionModel().getSelectedCells().get(0);
        int row = tp.getRow();

        ObservableList<TableColumn<VehicleTm,?> > columns = tblVehicle.getColumns();

        txtVehicleNo.setText(columns.get(0).getCellData(row).toString());
        txtVehicleModel.setText(columns.get(1).getCellData(row).toString());
        txtVehicleType.setText(columns.get(2).getCellData(row).toString());
        cmbCusId.setValue(cmbCusId.getValue());
    }
}
