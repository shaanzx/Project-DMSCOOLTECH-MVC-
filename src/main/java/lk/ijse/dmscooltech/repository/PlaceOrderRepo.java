package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Order;
import lk.ijse.dmscooltech.model.OrderPlace;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean orderPlace(OrderPlace orderPlace) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            boolean isOdSaved = OrderRepo.saveOrder(orderPlace.getOrder());
            if(isOdSaved){
                boolean isOdDetailsSaved = OrderDetailsRepo.saveOrderDetails(orderPlace.getOrderDetails());
                if(isOdDetailsSaved){
                    boolean isItemUpadeted = ItemRepo.updateItemQty(orderPlace.getOrderDetails());
                    if(isItemUpadeted){
                        boolean isPaymentSaved = PaymentRepo.savePayment(orderPlace.getPayment());
                        if(isPaymentSaved){
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        }catch (SQLException e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
