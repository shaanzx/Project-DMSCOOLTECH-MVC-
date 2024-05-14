package lk.ijse.dmscooltech.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.ViewRepair;
import lk.ijse.dmscooltech.model.tm.ViewRepairTm;
import lk.ijse.dmscooltech.repository.ViewRepairRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewRepairFormController implements Initializable {

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
    private TableView<ViewRepairTm> tblRepairDetailsTable;

    @FXML
    private TextField txtRepairId;

    private List<ViewRepair> viewRepairList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewRepairList = getAllRepair();
        setCellValueFactory();
        loadRepairDetailsTable();
    }

    private List<ViewRepair> getAllRepair() {
        List<ViewRepair> repairList = null;

        try {
            repairList = ViewRepairRepo.getViewRepair();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return repairList;
    }

    private void loadRepairDetailsTable() {
        ObservableList<ViewRepairTm> viewRepairList = FXCollections.observableArrayList();

        try {
            List<ViewRepair> repairList = ViewRepairRepo.getViewRepair();
            for(ViewRepair viewRepair : repairList){
                ViewRepairTm viewRepairTm = new ViewRepairTm(
                        viewRepair.getRepairId(),
                        viewRepair.getVehicleNo(),
                        viewRepair.getRepairDate(),
                        viewRepair.getItemCode(),
                        viewRepair.getItemQty(),
                        viewRepair.getItemUnitPrice(),
                        viewRepair.getRepairCost(),
                        viewRepair.getTotalPrice()
                );
                viewRepairList.add(viewRepairTm);
            }
            tblRepairDetailsTable.setItems(viewRepairList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colRepairDate.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        colRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }
}
