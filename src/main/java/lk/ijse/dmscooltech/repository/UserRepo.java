//package lk.ijse.dmscooltech.repository;
//
//import javafx.scene.control.Alert;
//import lk.ijse.dmscooltech.db.DbConnection;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserRepo {
//    private void checkUserNamePassword(String userId, String pw) throws SQLException, IOException {
//        String sqlQuery = "SELECT uId, uPassword FROM user WHERE uId = ?";
//
//        Connection connection = DbConnection.getInstance().getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
//        preparedStatement.setObject(1,userId);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        if(resultSet.next()){
//            String psw = resultSet.getString(2);
//            if(psw.equals(pw)){
//                ancLogin.getScene().getWindow().hide();
//                gotoDashBoard();
//            }else {
//                new Alert(Alert.AlertType.ERROR,"Incorrect Password!").show();
//            }
//        }else{
//            new Alert(Alert.AlertType.INFORMATION,"Incorrect user ID.Check and try again.").show();
//        }
//    }
//
//}
