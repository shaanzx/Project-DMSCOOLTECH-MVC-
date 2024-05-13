package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.ViewOrders;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewOrderRepo {

    public static List<ViewOrders> getViewOrders() throws SQLException {
        String sql =  "SELECT cId,orders.oId,ODate,iCode,qty,unitPrice,oPrice FROM orderdetails JOIN orders ON orderdetails.oid = orders.oid";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        List<ViewOrders> viewOrdersList = new ArrayList<>();

        while(resultSet.next()){
            viewOrdersList.add(new ViewOrders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7)
            ));
        }
        return viewOrdersList;
    }
}