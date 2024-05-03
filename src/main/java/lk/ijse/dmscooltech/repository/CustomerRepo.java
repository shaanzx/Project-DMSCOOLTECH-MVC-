package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Customer;
import lk.ijse.dmscooltech.model.tm.CustomerTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerRepo {
    public static String generateNextCustomerId() throws SQLException {
        String sql = "SELECT cId FROM customer ORDER BY cId DESC LIMIT 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCustomerId = null;
        if(resultSet.next()){
            currentCustomerId = resultSet.getString(1);
            return nextid(currentCustomerId);
        }
        return nextid(null);
    }

    public static String nextid(String currentid){
        String next=null;
        if (currentid==null){
            next="C001";
        }else {
            String data = currentid.replace("C","");
            int num = Integer.parseInt(data);
            num++;

            if (num>= 1 && num<= 9){
                next = "C00"+num;
            }else if (num>= 10 && num<= 99){
                next = "C0"+num;
            }else if (num>= 100 && num<= 999){
                next = "C"+num;
            }
        }
        return next;
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
            preparedStatement.setString(6, customer.getUserId());
            return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name=?, address=?, tel=?, email=?, uId=? WHERE cId=?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getTel());
        preparedStatement.setString(4, customer.getEmail());
        preparedStatement.setString(5, customer.getUserId());
        preparedStatement.setString(6, customer.getId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static Customer searchCustomer(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cId = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        Customer customer = null;

        if(resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String email = resultSet.getString(5);
            String userId = resultSet.getString(6);

            customer = new Customer(id, name, address, tel, email, userId);
        }
        return customer;

    }

    public boolean deleteCustomer(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE cId = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate() > 0;
    }

    public List<Customer> getCustomer() throws SQLException {
        String sql = "SELECT * FROM customer";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        List<Customer> customerList = new ArrayList<>();

        while (resultSet.next()) {
            customerList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return customerList;
    }
}
