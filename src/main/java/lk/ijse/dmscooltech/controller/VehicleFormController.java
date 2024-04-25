package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class VehicleFormController {

    @FXML
    private TextField VehicleType;

    @FXML
    private JFXComboBox<?> cmbCusId;

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
    private TableView<?> tblVehicle;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtVehicleModel;

    @FXML
    private TextField txtVehicleNo;

    @FXML
    void btnAddNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnVehicleDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnVehicleOnAction(ActionEvent event) {

    }

    @FXML
    void btnVehicleUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {

    }

}
