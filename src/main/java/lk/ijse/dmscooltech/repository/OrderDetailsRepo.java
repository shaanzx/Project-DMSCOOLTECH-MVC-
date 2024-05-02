package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.OrderDetails;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsRepo {
    public static boolean saveOrderDetails(List<OrderDetails> orderDetails) throws SQLException {
        for(OrderDetails orderDetail : orderDetails){
            if(!orderSave(orderDetail)){
                return false;
            }
        }
        return true;
    }

    private static boolean orderSave(OrderDetails orderDetail) throws SQLException {
        String sql = "INSERT INTO orderdetails VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(orderDetail.getOrderDate()));
        preparedStatement.setString(2, String.valueOf(orderDetail.getQty()));
        preparedStatement.setDouble(3, orderDetail.getOrderAmount());
        preparedStatement.setString(4, orderDetail.getOrderId());
        preparedStatement.setString(5, orderDetail.getItemCode());
        return preparedStatement.executeUpdate() > 0;
    }
}
