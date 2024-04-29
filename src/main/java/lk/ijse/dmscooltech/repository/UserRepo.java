package lk.ijse.dmscooltech.repository;

import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dmscooltech.controller.LoginFormController;
import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.User;

import java.io.IOException;
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

    public static void checkUserNamePassword(String userId, String pw, AnchorPane ancLogin) throws SQLException, IOException {
        String sqlQuery = "SELECT uId, uPassword FROM user WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setObject(1,userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            String psw = resultSet.getString(2);
            if(psw.equals(pw)){
                ancLogin.getScene().getWindow().hide();
                new LoginFormController().gotoDashBoard();
            }else {
                new Alert(Alert.AlertType.ERROR,"Incorrect Password!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Incorrect user ID.Check and try again.").show();
        }
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
        String next=null;
        if (currentUserId==null){
            next="U001";
        }else {
            String data = currentUserId.replace("U","");
            int num = Integer.parseInt(data);
            num++;

            if (num>= 1 && num<= 9){
                next = "U00"+num;
            }else if (num>= 10 && num<= 99){
                next = "U0"+num;
            }else if (num>= 100 && num<= 999){
                next = "U"+num;
            }
        }
        return next;
    }
}
