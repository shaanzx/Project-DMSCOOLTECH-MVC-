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
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Item;
import lk.ijse.dmscooltech.model.tm.ItemTm;
import lk.ijse.dmscooltech.repository.ItemRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<String, String> colItemCode;

    @FXML
    private TableColumn<String, String> colDate;

    @FXML
    private TableColumn<String, String> colItemDelete;

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
    private Pane pagingPane;

    @FXML
    private TextField txtDate;

    @FXML
    private Label txtDate1;

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

    ItemRepo itemRepo = new ItemRepo();

    private List<Item> itemList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtItemCode.setText(itemRepo.generateNextItemId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.itemList = getAllItem();
        setCellValueFactory();
        loadItemsTable();

    }

    private List<Item> getAllItem() {
        System.out.println(itemList.size());
        List<Item> itemList = null;
        try {
            itemList = itemRepo.getItem();
            System.out.println(itemList.size());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        System.out.println(itemList.size());
        return itemList;
    }

    private void loadItemsTable() {
        ObservableList<ItemTm> tmItemList = FXCollections.observableArrayList();

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
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colItemDelete.setCellValueFactory(new PropertyValueFactory<>("btnItemDelete"));
    }
    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            if(result.orElse(no) == yes) {
                //int index = tmItemList.selectionModel().
            }
        });
    }

    @FXML
    void btnItemSaveOnAction(ActionEvent event) {
        String code = txtItemCode.getText();
        String name = txtItemName.getText();
        String model = txtVehicleModel.getText();
        int qty = Integer.parseInt(txtQytOnHand.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        String date = txtDate.getText();

        Item item = new Item(code, name, model, qty, price, date);
        try {
            boolean isSaved = ItemRepo.saveItem(item);
            JFXButton btnRemove = new JFXButton("Remove");
            btnRemove.setCursor(Cursor.HAND);
            btnRemove.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item is Saved").show();
            }else {
                setRemoveBtnOnAction(btnRemove);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnItemUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemDeleteOnAction(ActionEvent event) {

    }
}
