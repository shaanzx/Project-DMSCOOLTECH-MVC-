package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRepo {

    public static String generatePaymentId() throws SQLException {
        String sql = "SELECT pId FROM payment ORDER BY pId DESC LIMIT 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentPaymentId = null;
        if(resultSet.next()){
            currentPaymentId = resultSet.getString(1);
            return nextPaymentId(currentPaymentId);
        }
        return nextPaymentId(null);
    }

    private static String nextPaymentId(String currentPaymentId) {
        String next = null;
        if (currentPaymentId == null) {
            next = "P001";
        } else {
            String data = currentPaymentId.replace("P", "");
            int num = Integer.parseInt(data);
            num++;

            if (num >= 1 && num <= 9) {
                next = "P00" + num;
            } else if (num >= 10 && num <= 99) {
                next = "P0" + num;
            } else if (num >= 100 && num <= 999) {
                next = "P" + num;
            }
        }
        return next;
    }

    public static boolean savePayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, payment.getPaymentId());
        preparedStatement.setString(2, payment.getCustomerId());
        preparedStatement.setString(3, payment.getOrderId());
        preparedStatement.setDouble(4, payment.getTotalAmount());
        preparedStatement.setString(5, String.valueOf(payment.getPaymentDate()));
        return preparedStatement.executeUpdate() > 0;
    }
}
