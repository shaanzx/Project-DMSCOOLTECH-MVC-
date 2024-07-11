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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.*;
import lk.ijse.dmscooltech.model.tm.AddToCartTm;
import lk.ijse.dmscooltech.repository.*;
import lk.ijse.dmscooltech.util.Mail;
import lk.ijse.dmscooltech.util.Navigation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXButton btnOrderConfirm;

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
    private Label lblBalance;

    @FXML
    private Label lblNeeded;

    @FXML
    private Label lblMoreMoney;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<AddToCartTm> tblOrderDetail;

    @FXML
    private TextField txtCustomerMobile;

    @FXML
    private TextField txtCash;

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
        lblNeeded.setVisible(false);
        btnOrderConfirm.setDisable(true);
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
        for(int i = 0; i < tblOrderDetail.getItems().size(); i++){
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

    private void calculateNetAmount()    {
        netAmount = 0;
        for(int i = 0; i < tblOrderDetail.getItems().size(); i++){
            netAmount += (double) colTotalAmount.getCellData(i);
        }
        lblNetAmount.setText(String.valueOf(netAmount));
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"customer_form.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        Date date = Date.valueOf(lblDate.getText());

        Order order = new Order(orderId, customerId, date);

        Customer customer = CustomerRepo.searchCustomer(customerId);
        String cusEmail = customer.getEmail();

        List<OrderDetails> orderList = new ArrayList<>();
        double netAmount = 0;
        double orderAmount = 0;

        for(int i=0; i < tblOrderDetail.getItems().size(); i++){
            AddToCartTm addToCartTm = cartList.get(i);

            OrderDetails orderDetails = new OrderDetails(
                    orderId,
                    addToCartTm.getItemCode(),
                    date,
                    addToCartTm.getQty(),
                    addToCartTm.getUnitPrice(),
                    orderAmount += addToCartTm.getQty()*addToCartTm.getUnitPrice()
            );
            orderList.add(orderDetails);

            netAmount += addToCartTm.getTotalAmount();
        }
        double printcash = Double.parseDouble(txtCash.getText());
        double balance = Double.parseDouble(lblBalance.getText());

        String paymentId = PaymentRepo.generatePaymentId();
        Payment payment = new Payment(paymentId, customerId, orderId,null, netAmount, date,printcash,balance);

        OrderPlace orderPlace = new OrderPlace(order, orderList , payment);
        try {
            boolean isOrderPlaced = PlaceOrderRepo.orderPlace(orderPlace);
            if (isOrderPlaced) {
                //new Alert(Alert.AlertType.CONFIRMATION, "Order Placed Successfully").show();
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Order Completed.Do you want to generate a bill?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("param1",printcash);
                    parameters.put("param2",balance);
                    InputStream resource = this.getClass().getResourceAsStream("/reports/order.jrxml");
                    try {
                        Mail.setMail("Order Completed", "Order Completed", "Thank you for your order. Your order is successfully placed. Your order id is "+orderId+".", cusEmail, getBill());
                    } catch (MessagingException | IOException | JRException e) {
                        throw new RuntimeException(e);
                    }
                    try{
                        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,DbConnection.getInstance().getConnection());
                        JasperViewer.viewReport(jasperPrint, false);
                    }catch (JRException e){
                        throw new RuntimeException(e);
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Something went wrong. Please try again").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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

    @FXML
    void btnViewOrderDetailsOnAction(ActionEvent event)  {
        //FXMLLoader.load(this.getClass().getResource("../view/OrderDetailsForm.fxml"));
        Navigation.changeStage("/view/viewOrders_Form.fxml","Order Details Form");
    }

    @FXML
    void txtSearchCustomerTelephoneOnAction(ActionEvent event) {
        String tel = txtCustomerMobile.getText();

        try {
            Customer customer = CustomerRepo.searchByMobile(tel);
            if(customer != null){
                lblCustomerName.setText(customer.getName());
                cmbCustomerId.setValue(customer.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void lblBalanceOnAction(MouseEvent mouseEvent) {

    }

    @FXML
    void keyCash(KeyEvent event) {
        if (!txtCash.getText().isEmpty()) {
            double balance = Double.parseDouble(txtCash.getText()) - Double.parseDouble(lblNetAmount.getText());
            if (balance >= 0) {
                txtCash.setStyle("-fx-text-fill: black");
                lblBalance.setText(String.valueOf(balance));
                lblNeeded.setVisible(false);
                lblMoreMoney.setText("");
                btnOrderConfirm.setDisable(false);
                lblBalance.setVisible(true);
            } else if (balance < 0) {
                txtCash.setStyle("-fx-text-fill: black");
                btnOrderConfirm.setDisable(true);
                double positbalance = Math.abs(balance);
                lblNeeded.setVisible(true);
                lblMoreMoney.setText(positbalance + "/=");
                lblBalance.setVisible(false);
            }
        } else {
            btnOrderConfirm.setDisable(true);
            txtCash.setStyle("-fx-text-fill: red");
            lblBalance.setVisible(false);
            lblNeeded.setVisible(false);
            lblMoreMoney.setText("");
        }
    }

    private File getBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/order.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        // Export the report to a PDF file
        File pdfFile = new File("Order Receipt.pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

        return pdfFile;
    }
}
