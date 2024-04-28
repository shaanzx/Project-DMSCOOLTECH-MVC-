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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.Item;
import lk.ijse.dmscooltech.model.tm.AddToCartTm;
import lk.ijse.dmscooltech.repository.CustomerRepo;
import lk.ijse.dmscooltech.repository.ItemRepo;
import lk.ijse.dmscooltech.repository.OrderRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<?> cmbUserId;

    @FXML
    private TableColumn<?, ?> colDeleteItem;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private DatePicker dpOrderDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblNetAmount;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblOrderDetail;

    @FXML
    private TextField txtQty;

    OrderRepo orderRepo = new OrderRepo();

    private ObservableList<AddToCartTm> cartList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            lblOrderId.setText(orderRepo.generateNextOrderId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        setDate();
        setCellValueFactory();
        getCustomerId();
        getItemCodes();
    }

    private void getItemCodes() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        try{
            List<String> idList = orderRepo.getItemCodes();
            for (String id : idList) {
                itemCodeList.add(id);
            }
            cmbItemCode.setItems(itemCodeList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    private void getCustomerId() {
        ObservableList<String> customerList = FXCollections.observableArrayList();
        try{
            List<String> idList = orderRepo.getCustomerId();
            for (String id : idList) {
                customerList.add(id);
            }
            cmbCustomerId.setItems(customerList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDeleteItem.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String customerId = cmbCustomerId.getValue();

        try{
            Customer customer = CustomerRepo.searchCustomer(customerId);
            lblCustomerName.setText(customer.getName());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {

        String itemCode = cmbItemCode.getValue();
        try{
            Item item = ItemRepo.searchByItemCode(itemCode);
            if(item != null){
                lblItemName.setText(item.getDescription());
                lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbUserOnAction(ActionEvent event) {

    }

    @FXML
    void lblNetAmountOnAction(MouseEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }
}
