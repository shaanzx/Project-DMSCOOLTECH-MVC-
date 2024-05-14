package lk.ijse.dmscooltech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ViewRepairFormController {

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colItemUnitPrice;

    @FXML
    private TableColumn<?, ?> colRepairCost;

    @FXML
    private TableColumn<?, ?> colRepairDate;

    @FXML
    private TableColumn<?, ?> colRepairId;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private Pane orderViewPane;

    @FXML
    private TableView<?> tblRepairDetailsTable;

    @FXML
    private TextField txtRepairId;

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}
