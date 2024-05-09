package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.OrderDetails;

import java.sql.Date;
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
        String sql = "INSERT INTO orderdetails VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1,orderDetail.getOrderId());
        preparedStatement.setString(2,orderDetail.getItemCode());
        preparedStatement.setDate(3, Date.valueOf(orderDetail.getOrderDate().toLocalDate()));
        preparedStatement.setInt(4,orderDetail.getQty());
        preparedStatement.setDouble(5,orderDetail.getUnitPrice());
        preparedStatement.setDouble(6,orderDetail.getOrderAmount());
        return preparedStatement.executeUpdate() > 0;
    }
}
