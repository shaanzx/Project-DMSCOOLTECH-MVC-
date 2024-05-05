package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.*;
import lk.ijse.dmscooltech.model.tm.RepairTm;
import lk.ijse.dmscooltech.repository.*;
import lk.ijse.dmscooltech.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static lk.ijse.dmscooltech.repository.RepairRepo.generateRepairId;

public class RepairFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colIetmQty;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemUnitPrice;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colRepairCost;

    @FXML
    private TableColumn<?, ?> colRepairDate;

    @FXML
    private TableColumn<?, ?> colRepairDescription;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private DatePicker dpRepairDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemQtyOnHand;

    @FXML
    private Label lblNetAmount;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblRepairId;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<RepairTm> tblRepairDetails;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtRepairCost;

    @FXML
    private TextField txtRepairDescription;
    RepairRepo repairRepo = new RepairRepo();
    VehicleRepo vehicleRepo = new VehicleRepo();
    EmployeeRepo employeeRepo = new EmployeeRepo();
    OrderRepo orderRepo = new OrderRepo();
    Vehicle vehicle = new Vehicle();
    Order order = new Order();
    RepairTm repairTm = new RepairTm();
    Payment payment = new Payment();

    private ObservableList<RepairTm> addToCartRepairList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
           lblRepairId.setText(repairRepo.generateRepairId());
            lblOrderId.setText(orderRepo.generateNextOrderId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getVehicleNo();
        getEmployeeId();
        getItemCode();
        setDate();
        setCellValueFactory();

    }

    private void setCellValueFactory() {
       colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
       colRepairDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
       colRepairDate.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
       colRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
       colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
       colIetmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
       colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
       colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
       colRemove.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    private void setDate() {
        LocalDate date = LocalDate.now();
        lblDate.setText(String.valueOf(date));
    }

    private void getItemCode() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        try{
            List<String> idList = OrderRepo.getItemCodes();
            for (String id : idList) {
                itemCodeList.add(id);
            }
            cmbItemCode.setItems(itemCodeList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeId() {
        ObservableList<String> employeeList = FXCollections.observableArrayList();
        try {
            List<String> employeeIdList = employeeRepo.getEmployeeId();
            for(String id : employeeIdList){
                employeeList.add(id);
            }
            cmbEmployeeId.setItems(employeeList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getVehicleNo() {
        ObservableList<String> vehicleList = FXCollections.observableArrayList();

        try {
            List<String> vehicleNoList = vehicleRepo.getVehicleNo();
            for(String no : vehicleNoList){
                vehicleList.add(no);
            }
            cmbVehicleNo.setItems(vehicleList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewVehicleOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"vehicle_form.fxml");
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String vehicleNo = cmbVehicleNo.getValue();
        String description = txtRepairDescription.getText();
        Date repairDate = Date.valueOf(dpRepairDate.getValue());
/*        double repairCost = 0;
        for(int i = 0; i < 10; i++){
            if(i==0){
                repairCost = Double.parseDouble(txtRepairCost.getText());
            }else{
                repairCost = 0;
            }
        }
        double repairCost = 0;
        for(int i = 0; i < tblRepairDetails.getItems().size(); i++) {
            if(i == 0) {
                repairCost = Double.parseDouble(txtRepairCost.getText());
                System.out.println(repairCost);
            } else if (i > 0) {
                repairCost = 0;
            }
        }*/
        double repairCost = Double.parseDouble(txtRepairCost.getText());
        String itemCode = cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double totalAmount = repairCost+(qty*unitPrice);
        JFXButton btnDelete = new JFXButton("Delete");
        btnDelete.setCursor(Cursor.HAND);

        btnDelete.setOnAction(e -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int index = tblRepairDetails.getSelectionModel().getSelectedIndex();
                addToCartRepairList.remove(index);
                tblRepairDetails.refresh();
                calculateNetAmount();
            }
        });
        for (int i = 0; i < addToCartRepairList.size(); i++) {
            /*if(i == 0){
                totalAmount = repairCost+(qty*unitPrice);
            } else if (i > 0) {
                totalAmount = qty*unitPrice;
            }*/
            if (itemCode.equals(colItemCode.getCellData(i))) {
                qty += addToCartRepairList.get(i).getQty();
                totalAmount = unitPrice * qty;
                addToCartRepairList.get(i).setQty(qty);
                addToCartRepairList.get(i).setTotalAmount(totalAmount);
                tblRepairDetails.refresh();
                calculateNetAmount();
                txtQty.clear();
                cmbItemCode.requestFocus();
                return;
            }
        }
        RepairTm repair = new RepairTm(vehicleNo, description, repairDate,   repairCost, itemCode, qty, unitPrice, totalAmount, btnDelete);
        System.out.println(repair);
        addToCartRepairList.add(repair);
        tblRepairDetails.setItems(addToCartRepairList);
        txtQty.clear();
        calculateNetAmount();
    }

    private void calculateNetAmount() {
        double netAmount = 0;
        for(int i = 0; i < tblRepairDetails.getItems().size(); i++){
            netAmount += (double) colTotalPrice.getCellData(i);
        }
        lblNetAmount.setText(String.valueOf(netAmount));
    }

    @FXML
    void btnConfirmRepairBillOnAction(ActionEvent event) throws SQLException {
        String orderId = lblOrderId.getText();
        String customerId = vehicle.getCustomerId();
        Date date = Date.valueOf(lblDate.getText());

        order = new Order(orderId, customerId, date);

        List<OrderDetails> orderList = new ArrayList<>();
        double netAmount = 0;

        for(int i=0; i < tblRepairDetails.getItems().size(); i++){
            RepairTm repairTm = addToCartRepairList.get(i);

            OrderDetails orderDetails = new OrderDetails(
                    date,
                    repairTm.getQty(),
                    repairTm.getTotalAmount(),
                    orderId,
                    cmbItemCode.getValue()
            );
            orderList.add(orderDetails);
            netAmount += repairTm.getTotalAmount();
        }


        Repair repair = new Repair(
                lblRepairId.getText(),
                repairTm.getVehicleNo(),
                repairTm.getDescription(),
                Date.valueOf(dpRepairDate.getValue()),
                repairTm.getRepairCost(),
                cmbEmployeeId.getValue(),
                cmbItemCode.getValue(),
                repairTm.getQty(),
                repairTm.getUnitPrice(),
                netAmount
        );

        payment = new Payment(
                payment.getPaymentId(),
                customerId,
                orderId,
                lblRepairId.getText(),
                netAmount,
                Date.valueOf(lblDate.getText())
        );

        RepairDetails repairDetails = new RepairDetails(order, orderList, repair,payment);

        try {
            boolean isRepairDone = RepairDetailsRepo.addNewRepair(repairDetails);
            if (isRepairDone) {
                new Alert(Alert.AlertType.CONFIRMATION, "Repair Done And Generate Your Bill").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnViewRepairDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbEmployeeIdOnAction(ActionEvent event) {
        String employeeId = cmbEmployeeId.getValue();

        try {
            Employee employee = EmployeeRepo.searchEmployee(employeeId);
            lblEmployeeName.setText(employee.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {
        String itemCode = cmbItemCode.getValue();
        try {
            Item item = ItemRepo.searchByItemCode(itemCode);
            if(item != null){
                lblItemName.setText(item.getDescription());
                lblItemQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
                lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbVehicleNoOnAction(ActionEvent event) {
        String vehicleNo = cmbVehicleNo.getValue();
        try {
            Vehicle vehicle = vehicleRepo.searchVehicle(vehicleNo);
            Customer customer = CustomerRepo.searchCustomer(vehicle.getCustomerId());
            String name = customer.getName();
            lblCustomerName.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
}
