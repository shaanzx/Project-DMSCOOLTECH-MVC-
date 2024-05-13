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
import lk.ijse.dmscooltech.model.ViewOrders;
import lk.ijse.dmscooltech.model.tm.ViewOrderTm;
import lk.ijse.dmscooltech.repository.ViewOrderRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewOrdersFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colItemUnitPrice;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private Pane orderViewPane;

    @FXML
    private TableView<ViewOrderTm> tblOrderView;

    @FXML
    private TextField txtOrderId;

    private List<ViewOrders>  viewOrderList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewOrderList = getAllOrders();
        setCellValueFactory();
        loadOrderDetailsTable();
    }

    private List<ViewOrders> getAllOrders() {
        List<ViewOrders> ordersList  = null;

        try {
            ordersList = ViewOrderRepo.getViewOrders();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    private void loadOrderDetailsTable() {
        ObservableList<ViewOrderTm> tmOrderList = FXCollections.observableArrayList();

        try {
            List<ViewOrders>  viewOrderList = ViewOrderRepo.getViewOrders();
            for(ViewOrders viewOrders : viewOrderList){
                ViewOrderTm viewOrderTm = new ViewOrderTm(
                        viewOrders.getCustomerId(),
                        viewOrders.getOrderId(),
                        viewOrders.getDate(),
                        viewOrders.getItemCode(),
                        viewOrders.getItemQty(),
                        viewOrders.getItemUnitPrice(),
                        viewOrders.getTotalAmount()
                );
                tmOrderList.add(viewOrderTm);
            }
            tblOrderView.setItems(tmOrderList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}
