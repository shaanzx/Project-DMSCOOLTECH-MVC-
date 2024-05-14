package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.ViewRepair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRepairRepo {
    public static List<ViewRepair> getViewRepair() throws SQLException {
        String sql = "SELECT rId,vNo,repairDate,orderdetails.iCode,orderdetails.qty,item.iPrice,repairCost,totalPrice FROM repair JOIN orderdetails ON repair.iCode = orderdetails.iCode JOIN Item ON repair.iCode = Item.iCode";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        List<ViewRepair> viewRepairList = new ArrayList<>();

        while(resultSet.next()){
            viewRepairList.add(new ViewRepair(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getDouble(8)
            ));
        }
        return viewRepairList;
    }

    public static List<ViewRepair> getViewRepairByRepairId(String repairId) throws SQLException {
        String sql = "SELECT rId,vNo,repairDate,orderdetails.iCode,orderdetails.qty,item.iPrice,repairCost,totalPrice FROM repair JOIN orderdetails ON repair.iCode = orderdetails.iCode JOIN Item ON repair.iCode = Item.iCode WHERE repair.rId = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, repairId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<ViewRepair> viewRepairList = new ArrayList<>();

        while(resultSet.next()){
            viewRepairList.add(new ViewRepair(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getDouble(8)
            ));
        }
        return viewRepairList;
    }
}
