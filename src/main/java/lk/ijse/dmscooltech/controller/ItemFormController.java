package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Item;
import lk.ijse.dmscooltech.repository.ItemRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private Pane pagingPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblDate1;

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

    @FXML
    void btnItemDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemSaveOnAction(ActionEvent event) {
        String code = txtItemCode.getText();
        String name = txtItemName.getText();
        String model = txtVehicleModel.getText();
        int qty = Integer.parseInt(txtQytOnHand.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        String date = txtDate.getText();

        Item item = new Item(code, name, model, price, qty, date);
        try {
            boolean isSaved = ItemRepo.saveItem(item);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item is Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnItemUpdateOnAction(ActionEvent event) {

    }
    @FXML
    void cmbWarrantyPeriodOnAction(ActionEvent event) {
        String sql = "SELECT * FROM warranty WHERE warrantyPeriod = ?";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtItemCode.setText(itemRepo.generateNextItemId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
