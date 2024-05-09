package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo {
    public static boolean saveOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, order.getOrderId());
        preparedStatement.setString(2, order.getCustomerId());
        preparedStatement.setDate(3, (Date) order.getDate());

        return preparedStatement.executeUpdate()>0;
    }

    public static String generateNextOrderId() throws SQLException {
        String sql = "SELECT oId FROM orders ORDER BY oId DESC LIMIT 1";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        String currentOrderId = null;
        if(resultSet.next()){
            currentOrderId = resultSet.getString(1);
            return nextOrderId(currentOrderId);
        }
        return nextOrderId(null);
    }

    private static String nextOrderId(String currentOrderId) {
        String nextOrderId=null;
        if (currentOrderId==null){
            nextOrderId="OR001";
        }else {
            String data = currentOrderId.replace("OR", "");
            int num = Integer.parseInt(data);
            num++;

            if (num >= 1 && num <= 9) {
                nextOrderId = "OR00" + num;
            } else if (num >= 10 && num <= 99) {
                nextOrderId = "OR0" + num;
            } else if (num >= 100 && num <= 999) {
                nextOrderId = "OR" + num;
            }
        }
        return nextOrderId;
    }

    public List<String> getCustomerId() throws SQLException {
        List<String> customerIds = new ArrayList<>();

        String sql = "SELECT cId FROM customer";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            customerIds.add(resultSet.getString(1));
        }
        return customerIds;
    }

    public static List<String> getItemCodes() throws SQLException {
        List<String> itemCodes = new ArrayList<>();
        String sql = "SELECT iCode FROM item";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            itemCodes.add(resultSet.getString(1));
        }
        return itemCodes;
    }

    public int countOrderId() throws SQLException {
        String sql = "SELECT COUNT(oId) from orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(resultSet.getString(1));
            return idd;
        }
        return Integer.parseInt(null);
    }
}
