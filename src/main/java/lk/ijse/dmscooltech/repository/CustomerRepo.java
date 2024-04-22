package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepo {
    public static String generateNextCustomerId() throws SQLException {
        String sql = "SELECT cId FROM customer ORDER BY cId DESC LIMIT 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCustomerId = null;
        if(resultSet.next()){
            currentCustomerId = resultSet.getString(1);
            return splitCustomerId(currentCustomerId);
        }
        return splitCustomerId(null);
    }

    private static String splitCustomerId(String currentCustomerId) {
        if(currentCustomerId != null){
            String[] split = currentCustomerId.split("C");
            int id = Integer.parseInt(split[1]);
            id++;
            return "C00"+id;
        }
        return "C001";
    }

    public boolean saveCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getTel());
            preparedStatement.setString(5, customer.getEmail());
          //  preparedStatement.setString(6,);
            return preparedStatement.executeUpdate() > 0;
    }
}
