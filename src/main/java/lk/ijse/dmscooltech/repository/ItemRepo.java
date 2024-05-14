package lk.ijse.dmscooltech.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Item;
import lk.ijse.dmscooltech.model.OrderDetails;

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
        preparedStatement.setString(4, String.valueOf(item.getQtyOnHand()));
        preparedStatement.setDouble(5, item.getUnitPrice());
        preparedStatement.setString(6, item.getDate());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateItemQty(List<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails orderDetail : orderDetails) {
            if(!updateItemQty(orderDetail)){
                return false;
            }
        }
        return true;
    }

    private static boolean updateItemQty(OrderDetails orderDetail) throws SQLException {
        String sql = "UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE iCode = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setInt(1, orderDetail.getQty());
        preparedStatement.setString(2, orderDetail.getItemCode());
        return preparedStatement.executeUpdate() > 0;
    }

    public static ObservableList<XYChart.Series<String, Integer>> getDataToBarChart() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql="SELECT iName,qtyOnHand FROM item ";

            ObservableList<XYChart.Series<String, Integer>> datalist = FXCollections.observableArrayList();

            PreparedStatement pstm= connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            // Creating a new series object
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            while(resultSet.next()){
                String itemName = resultSet.getString("iName");
                int itemQty = resultSet.getInt("qtyOnHand");
                series.getData().add(new XYChart.Data<>(itemName, itemQty));
            }

            datalist.add(series);
            return datalist;
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

    public static Item searchByItemCode(String code) throws SQLException {
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
