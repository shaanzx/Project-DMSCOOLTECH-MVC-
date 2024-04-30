package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Order;
import lk.ijse.dmscooltech.model.OrderPlace;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static void placeOrder(OrderPlace op) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        boolean isOrderSaved = OrderRepo.saveOrder((Order) op.getOrderDetails());
        if(isOrderSaved) {
            ItemRepo.updateQty(op.getOrderDetails());
        }
    }
}
