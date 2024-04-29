package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepo {
    public static List<String> getCustomerId() throws SQLException {
        String sql = "SELECT cId FROM customer";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> customerIds = new ArrayList<>();
        while (resultSet.next()) {
            customerIds.add(resultSet.getString(1));
        }
        return customerIds;
    }

    public boolean saveVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicle VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,vehicle.getVehicleNo());
        preparedStatement.setString(2,vehicle.getModel());
        preparedStatement.setString(3,vehicle.getType());
        preparedStatement.setString(4,vehicle.getCustomerId());

        return preparedStatement.executeUpdate() > 0;
    }

    public List<Vehicle> getVehicle() throws SQLException {
        String sql = "SELECT * FROM vehicle";
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        List<Vehicle> vehicleList = new ArrayList<>();
        while (resultSet.next()){
            vehicleList.add(new Vehicle(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return vehicleList;
    }
}
