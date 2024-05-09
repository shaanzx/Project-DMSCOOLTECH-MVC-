package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.dmscooltech.model.Item;
import lk.ijse.dmscooltech.model.tm.ItemTm;
import lk.ijse.dmscooltech.repository.ItemRepo;
import lk.ijse.dmscooltech.util.DataValidateController;
import lombok.Data;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<String, String> colItemCode;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<String, String> colItemName;

    @FXML
    private TableColumn<String, String> colModel;

    @FXML
    private TableColumn<Integer, Integer> colQtyOnHand;

    @FXML
    private TableColumn<Double, Double> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private Label lblDateItem;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQytOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtVehicleModel;

    @FXML
    private Label itemNameValidate;

    @FXML
    private Label itemQtyValidate;

    @FXML
    private Label itemUnitPriceValidate;

    @FXML
    private Label itemVehicleModelValidate;


    ItemRepo itemRepo = new ItemRepo();

    private List<Item> itemList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtItemCode.setText(itemRepo.generateNextItemId());
            itemRepo.getItem();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.itemList = getAllItem();
        setCellValueFactory();
        loadItemsTable();
        setDate();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDateItem.setText(String.valueOf(now));
    }

    private List<Item> getAllItem() {
        List<Item> itemList = null;
        try {
            itemList = itemRepo.getItem();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return itemList;
    }

    private void loadItemsTable() {
        itemRepo = new ItemRepo();
        ObservableList<ItemTm> tmItemList = FXCollections.observableArrayList();
        try {
            List<Item> itemList = itemRepo.getItem();
            for(Item item : itemList) {
                ItemTm itemTm = new ItemTm(
                        item.getCode(),
                        item.getDescription(),
                        item.getModel(),
                        item.getQtyOnHand(),
                        item.getUnitPrice(),
                        item.getDate()
                );
                tmItemList.add(itemTm);
            }
            tblItem.setItems(tmItemList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    void btnItemSaveOnAction(ActionEvent event) {
        String code = txtItemCode.getText();
        String name = txtItemName.getText();
        String model = txtVehicleModel.getText();
        int qty = Integer.parseInt(txtQytOnHand.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        String date = Date.valueOf(dpDate.getValue()).toString();

        Item item = new Item(code, name, model, qty, price, date);

        if(DataValidateController.validateItemName(txtItemName.getText())) {
            itemNameValidate.setText("");
            if (DataValidateController.validateVehicleModel(txtVehicleModel.getText())) {
                itemVehicleModelValidate.setText("");
                if (DataValidateController.validateItemQty(txtQytOnHand.getText())) {
                    itemQtyValidate.setText("");
                    if (DataValidateController.validateItemPrice(txtUnitPrice.getText())) {
                        itemNameValidate.setText("");
                        try {
                            boolean isSaved = itemRepo.saveItem(item);
                            if (isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                                loadItemsTable();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        itemNameValidate.setText("Invalid Price");
                    }
                } else {
                    itemQtyValidate.setText("Invalid Quantity");
                }
            } else {
                itemVehicleModelValidate.setText("Invalid Vehicle Model");
            }
        }else{
            itemNameValidate.setText("Invalid Name");
        }
    }

    @FXML
    void btnItemUpdateOnAction(ActionEvent event) {
        String code = txtItemCode.getText();
        String name = txtItemName.getText();
        String model = txtVehicleModel.getText();
        int qty = Integer.parseInt(txtQytOnHand.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        String date = String.valueOf(Date.valueOf(dpDate.getValue()));

        Item item = new Item(code, name, model, qty, price, date);

        try {
            boolean isUpdated = itemRepo.updateItem(item);
            if(isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated").show();
                loadItemsTable();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnItemDeleteOnAction(ActionEvent event) {
        String code = txtItemCode.getText();

        try {
            boolean isDeleted = itemRepo.deleteItem(code);
            if(isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted").show();
                loadItemsTable();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchItemOnAction(ActionEvent event) {
        String code = txtItemCode.getText();
        try {
            Item item = itemRepo.searchByItemCode(code);
            if(item != null) {
                txtItemCode.setText(item.getCode());
                txtItemName.setText(item.getDescription());
                txtVehicleModel.setText(item.getModel());
                txtQytOnHand.setText(String.valueOf(item.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                dpDate.setValue(LocalDate.parse(item.getDate()));
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void tblItemClickOnAction(MouseEvent event) {
        TablePosition pos = tblItem.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<ItemTm,?>> columns = tblItem.getColumns();

        txtItemCode.setText(columns.get(0).getCellData(row).toString());
        txtItemName.setText(columns.get(1).getCellData(row).toString());
        txtVehicleModel.setText(columns.get(2).getCellData(row).toString());
        txtQytOnHand.setText(columns.get(3).getCellData(row).toString());
        txtUnitPrice.setText(columns.get(4).getCellData(row).toString());
        dpDate.setValue(LocalDate.parse(columns.get(5).getCellData(row).toString()));

        tblItem.setCursor(Cursor.HAND);
    }
}
