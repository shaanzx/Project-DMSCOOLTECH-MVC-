package lk.ijse.dmscooltech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class ViewOrdersFormController {

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPayemntId;

    @FXML
    private Pane orderViewPane;

    @FXML
    private TableView<?> tblOrderView;

    @FXML
    private TableColumn<?, ?> txtTotalAmount;

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}
