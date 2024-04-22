package lk.ijse.dmscooltech.repository;

import javafx.collections.ObservableList;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean saveItem(Item item) throws SQLException {
        String sql = "INSERT INTO item VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, item.getCode());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setString(3, item.getModel());
        preparedStatement.setInt(4, item.getQtyOnHand());
        preparedStatement.setDouble(5, item.getUnitPrice());
        preparedStatement.setString(6, item.getDate());
        return preparedStatement.executeUpdate() > 0;
    }
    public String generateNextItemId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT iCode FROM item ORDER BY iCode DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentItemId = null;
        if(resultSet.next()){
            currentItemId = resultSet.getString(1);
            return splitItemId(currentItemId);
        }
        return splitItemId(null);
    }
    private String splitItemId(String currentItemId){
        if(currentItemId!=null){
            String[] split = currentItemId.split("I");
            int id = Integer.parseInt(split[1]);
            id++;
            return "I00"+id;
        }
        return "I001";
    }

    public static List<Item> getItem() throws SQLException {
        String sql = "SELECT * FROM item";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            itemList.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return itemList;
    }
}
