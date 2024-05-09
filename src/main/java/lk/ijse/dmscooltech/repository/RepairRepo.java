package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Repair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepairRepo {

    public static String generateRepairId() throws SQLException {
        String sql = "SELECT rId FROM repair ORDER BY rId DESC LIMIT 1";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        String currentRepairId = null;
        if(resultSet.next()){
            currentRepairId = resultSet.getString(1);
            return nextRepairId(currentRepairId);
        }
        return nextRepairId(null);
    }

    private static String nextRepairId(String currentRepairId) {
        String nextRepairId=null;
        if (currentRepairId==null){
            nextRepairId="R001";
        }else {
            String data = currentRepairId.replace("R", "");
            int num = Integer.parseInt(data);
            num++;

            if (num >= 1 && num <= 9) {
                nextRepairId = "R00" + num;
            } else if (num >= 10 && num <= 99) {
                nextRepairId = "R0" + num;
            } else if (num >= 100 && num <= 999) {
                nextRepairId = "R" + num;
            }
        }
        return nextRepairId;
    }

    public static boolean saveRepair(Repair repair) throws SQLException {
        String sql = "INSERT INTO repair VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, repair.getRepairId());
        preparedStatement.setString(2,repair.getVehicleNo());
        preparedStatement.setString(3,repair.getDescription());
        preparedStatement.setString(4, String.valueOf(repair.getRepairDate()));
        preparedStatement.setDouble(5, repair.getRepairCost());
        preparedStatement.setString(6,repair.getEmployeeId());
        preparedStatement.setString(7,repair.getItemCode());
        preparedStatement.setString(8, String.valueOf(repair.getTotalAmount()));

        return preparedStatement.executeUpdate()>0;
    }

    public int countRepairId() throws SQLException {
        String sql = "SELECT COUNT(eId) from repair";

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
