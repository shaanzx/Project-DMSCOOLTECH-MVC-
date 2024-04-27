package lk.ijse.dmscooltech.repository;

import javafx.collections.ObservableList;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Item;

import java.sql.*;
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


    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE item SET iName = ?, iCategory = ?, qtyOnHand = ?, iPrice = ?, date = ? WHERE iCode = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, item.getDescription());
        preparedStatement.setString(2, item.getModel());
        preparedStatement.setInt(3, item.getQtyOnHand());
        preparedStatement.setDouble(4, item.getUnitPrice());
        preparedStatement.setString(5, item.getDate());
        preparedStatement.setString(6, item.getCode());

        return preparedStatement.executeUpdate() > 0;
    }

    public Item searchByItemCode(String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE iCode = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, code);
        ResultSet resultSet = preparedStatement.executeQuery();

        Item item = null;

        if(resultSet.next()) {
            String iCode = resultSet.getString(1);
            String description = resultSet.getString(2);
            String model = resultSet.getString(3);
            int qtyOnHand = resultSet.getInt(4);
            double unitPrice = resultSet.getDouble(5);
            String date = resultSet.getString(6);

            item = new Item(iCode, description, model, qtyOnHand, unitPrice, date);
        }
        return item;
    }

    public boolean deleteItem(String code) throws SQLException {
        String sql = "DELETE FROM item WHERE iCode = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, code);
        return preparedStatement.executeUpdate() > 0;
    }
}
