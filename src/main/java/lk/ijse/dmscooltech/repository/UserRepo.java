package lk.ijse.dmscooltech.repository;

import javafx.scene.control.Alert;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
    public static boolean userSave(User user) throws SQLException {

            String sql = "INSERT INTO user VALUES(?, ?, ?)";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());

            return preparedStatement.executeUpdate() > 0;
    }

    public String generateUserId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT uId FROM user ORDER BY uId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentUserId = null;
        if(resultSet.next()){
            currentUserId = resultSet.getString(1);
            return splitUserId(currentUserId);
        }
        return splitUserId(null);
    }

    private String splitUserId(String currentUserId) {
        if(currentUserId != null){
            String[] split = currentUserId.split("U");
            int id = Integer.parseInt(split[1]);
            id++;
            return "U00"+id;
        }
        return "U001";
    }
}
