package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

    public String generateNextEmployeeId() throws SQLException {
        String sql = "SELECT eId FROM employee ORDER BY eId DESC LIMIT 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentEmployeeId = null;
        if(resultSet.next()){
            currentEmployeeId = resultSet.getString(1);
            return nextEmpId(currentEmployeeId);
        }
        return nextEmpId(null);
    }

    private String nextEmpId(String currentEmployeeId) {
        String next=null;
        if (currentEmployeeId==null){
            next="E001";
        }else {
            String data = currentEmployeeId.replace("E","");
            int num = Integer.parseInt(data);
            num++;

            if (num>= 1 && num<= 9){
                next = "E00"+num;
            }else if (num>= 10 && num<= 99){
                next = "E0"+num;
            }else if (num>= 100 && num<= 999){
                next = "E"+num;
            }
        }
        return next;
    }

    public List<Employee> getEmployee() throws SQLException {
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        List<Employee> employeeList = new ArrayList<>();

            while (resultSet.next()){
                employeeList.add(new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));
            }
        return employeeList;
    }

    public boolean saveEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,employee.getId());
        preparedStatement.setString(2,employee.getName());
        preparedStatement.setString(3,employee.getAddress());
        preparedStatement.setString(4,employee.getTel());
        preparedStatement.setString(5,employee.getJobRole());
        preparedStatement.setString(6,employee.getUserId());
        return preparedStatement.executeUpdate()>0;

    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET eName=?, eAddress=?, eTel=?, ejobRole=?, uId=? WHERE eId=?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1,employee.getName());
        preparedStatement.setString(2,employee.getAddress());
        preparedStatement.setString(3,employee.getTel());
        preparedStatement.setString(4,employee.getJobRole());
        preparedStatement.setString(5,employee.getUserId());
        preparedStatement.setString(6,employee.getId());
        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteEmployee(String empId) throws SQLException {
        String sql = "DELETE FROM employee WHERE eId = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1,empId);
        return preparedStatement.executeUpdate()>0;
    }

    public Employee searchEmployee(String empId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE eId = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1,empId);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;

        if(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String jobRole = resultSet.getString(5);
            String userId = resultSet.getString(6);

            employee = new Employee(id, name, address, tel, jobRole, userId);
        }
        return employee;
    }
}