package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
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
import java.util.Optional;
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
    private TableColumn<?, ?> colTotalAmount;

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
    private TableView<AddToCartTm> tblOrderDetail;

    @FXML
    private TextField txtQty;

    private double netAmount = 0;

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
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colDeleteItem.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String itemCode = cmbItemCode.getValue();
        String itemName = lblItemName.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalAmount = unitPrice * qty;
        JFXButton btnDelete = new JFXButton("Delete");
        btnDelete.setCursor(Cursor.HAND);

        btnDelete.setOnAction((e)->{
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
            if(type.orElse(no) == yes){
                int index = tblOrderDetail.getSelectionModel().getSelectedIndex();
                cartList.remove(index);
                tblOrderDetail.refresh();
                calculateNetAmount();
            }
        });
        for(int i = 0; i < cartList.size(); i++){
            if(itemCode.equals(colItemCode.getCellData(i))){
                qty += cartList.get(i).getQty();
                totalAmount = unitPrice * qty;
                cartList.get(i).setQty(qty);
                cartList.get(i).setTotalAmount(totalAmount);
                tblOrderDetail.refresh();
                calculateNetAmount();
                txtQty.clear();
                cmbItemCode.requestFocus();
                return;
            }
        }
        AddToCartTm act = new AddToCartTm(itemCode, itemName, unitPrice, qty, totalAmount, btnDelete);
        cartList.add(act);
        tblOrderDetail.setItems(cartList);
        txtQty.clear();
        calculateNetAmount();
    }

    private void calculateNetAmount() {
        netAmount = 0;
        for(int i = 0; i < tblOrderDetail.getItems().size(); i++){
            netAmount += (double) colTotalAmount.getCellData(i);
        }
        lblNetAmount.setText(String.valueOf(netAmount));
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        String date = dpOrderDate.getValue().toString();


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
        btnAddToCartOnAction(event);
    }
}
