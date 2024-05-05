package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepairRepo {

    public String generateRepairId() throws SQLException {
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

    private String nextRepairId(String currentRepairId) {
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
}
