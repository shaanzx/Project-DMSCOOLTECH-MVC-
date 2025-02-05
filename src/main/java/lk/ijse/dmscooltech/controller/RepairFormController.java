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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.*;
import lk.ijse.dmscooltech.model.tm.RepairTm;
import lk.ijse.dmscooltech.repository.*;
import lk.ijse.dmscooltech.util.Navigation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static lk.ijse.dmscooltech.repository.RepairRepo.generateRepairId;

public class RepairFormController implements Initializable {

    @FXML
    private JFXButton btnOrderPlace;

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
    private Label lblBalance;

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
    private Label lblMoreMoney;

    @FXML
    private Label lblNeeded;

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

    @FXML
    private TextField txtPayment;

    private ObservableList<RepairTm> addToCartRepairList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
           lblRepairId.setText(RepairRepo.generateRepairId());
            lblOrderId.setText(OrderRepo.generateNextOrderId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getVehicleNo();
        getEmployeeId();
        getItemCode();
        setDate();
        setCellValueFactory();
        lblNeeded.setVisible(false);
        btnOrderPlace.setDisable(true);
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
            List<String> employeeIdList = EmployeeRepo.getEmployeeId();
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
            List<String> vehicleNoList = VehicleRepo.getVehicleNo();
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
        JFXButton btnDelete = new JFXButton("Remove");
        btnDelete.setCursor(Cursor.HAND);

        btnDelete.setOnAction((e)->{
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
            if(type.orElse(no) == yes){
                int index = tblRepairDetails.getSelectionModel().getSelectedIndex();
                addToCartRepairList.remove(index);
                tblRepairDetails.refresh();
                calculateNetAmount();
            }
        });
        for (int i = 0; i < tblRepairDetails.getItems().size(); i++) {
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
        Vehicle vehicle = VehicleRepo.searchVehicle(cmbVehicleNo.getValue());
        Date date = Date.valueOf(lblDate.getText());

       Order order = new Order(orderId, vehicle.getCustomerId(), date);

        List<OrderDetails> orderList = new ArrayList<>();
        double netAmount = 0;
        double orderAmount = 0;

        for(int i=0; i < tblRepairDetails.getItems().size(); i++){
            RepairTm repairTm = addToCartRepairList.get(i);

            OrderDetails orderDetails = new OrderDetails(
                    orderId,
                    repairTm.getItemCode(),
                    date,
                    repairTm.getQty(),
                    repairTm.getUnitPrice(),
                    orderAmount += repairTm.getQty() * repairTm.getUnitPrice()
            );
            orderList.add(orderDetails);
            netAmount += repairTm.getTotalAmount();
        }


        Repair repair = new Repair(
                lblRepairId.getText(),
                vehicle.getVehicleNo(),
                txtRepairDescription.getText(),
                Date.valueOf(dpRepairDate.getValue()),
                Double.valueOf(txtRepairCost.getText()),
                cmbEmployeeId.getValue(),
                cmbItemCode.getValue(),
                netAmount
        );

        double customerPayment= Double.parseDouble(txtPayment.getText());
        double balance =  Double.parseDouble(lblBalance.getText());

      Payment  payment = new Payment(
                PaymentRepo.generatePaymentId(),
                vehicle.getCustomerId(),
                orderId,
                lblRepairId.getText(),
                netAmount,
              Date.valueOf(lblDate.getText()),
              customerPayment,
              balance
        );

        RepairDetails repairDetails = new RepairDetails(order, orderList, repair,payment);

        try {
            boolean isRepairDone = RepairDetailsRepo.addNewRepair(repairDetails);
            if (isRepairDone) {
                //new Alert(Alert.AlertType.CONFIRMATION, "Repair Process Done").show();
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Repair Process Done.Do You want To Bill?", yes, no).showAndWait();
                if(type.orElse(no) == yes) {
                    try {
                        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/Payment.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

                        Map<String, Object> data = new HashMap<>();
                        data.put("RepairId", lblRepairId.getText());

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
                        JasperViewer.viewReport(jasperPrint, false);

                    } catch (JRException | SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error generating receipt");
                        alert.setContentText("An error occurred while generating the receipt. Check the logs for more details.");
                        alert.showAndWait();
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnViewRepairDetailsOnAction(ActionEvent event) {
        Navigation.changeStage("/view/viewRepair_Form.fxml","View Repair Details Form");
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
            Vehicle vehicle = VehicleRepo.searchVehicle(vehicleNo);
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

    @FXML
    void keyCash(KeyEvent event) {
        if (!txtPayment.getText().isEmpty()) {
            double balance = Double.parseDouble(txtPayment.getText()) - Double.parseDouble(lblNetAmount.getText());
            if (balance >= 0) {
                txtPayment.setStyle("-fx-text-fill: black");
                lblBalance.setText(String.valueOf(balance));
                lblNeeded.setVisible(false);
                lblMoreMoney.setText("");
                btnOrderPlace.setDisable(false);
                lblBalance.setVisible(true);
            } else if (balance < 0) {
                txtPayment.setStyle("-fx-text-fill: black");
                btnOrderPlace.setDisable(true);
                double positbalance = Math.abs(balance);
                lblNeeded.setVisible(true);
                lblMoreMoney.setText(positbalance + "/=");
                lblBalance.setVisible(false);
            }
        } else {
            btnOrderPlace.setDisable(true);
            txtPayment.setStyle("-fx-text-fill: red");
            lblBalance.setVisible(false);
            lblNeeded.setVisible(false);
            lblMoreMoney.setText("");
        }
    }
}
