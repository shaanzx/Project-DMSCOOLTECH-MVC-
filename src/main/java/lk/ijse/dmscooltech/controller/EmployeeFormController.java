package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.Employee;
import lk.ijse.dmscooltech.model.tm.EmployeeTm;
import lk.ijse.dmscooltech.repository.EmployeeRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXComboBox<?> cmbUserId;

    @FXML
    private TableColumn<String, String> colEmpAddress;

    @FXML
    private TableColumn<String, String> colEmpId;

    @FXML
    private TableColumn<String, String> colEmpJobRole;

    @FXML
    private TableColumn<String, String> colEmpName;

    @FXML
    private TableColumn<String, String> colEmpTel;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpJobRole;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpTel;

    EmployeeRepo employeeRepo = new EmployeeRepo();

    private List<Employee> employeeList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtEmpId.setText(employeeRepo.generateNextEmployeeId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        employeeList = getAllEmployee();
        setCellValueFactory();
        loadEmployeeTable();


    }

    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;
        try {
            employeeList = employeeRepo.getEmployee();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return employeeList;
    }

    private void loadEmployeeTable() {
        employeeRepo = new EmployeeRepo();
        ObservableList<EmployeeTm> tmEmployeeList = FXCollections.observableArrayList();
        try {
            List<Employee> EmployeeList = employeeRepo.getEmployee();
            for (Employee employee : EmployeeList) {
                EmployeeTm employeeTm = new EmployeeTm(
                        employee.getId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getTel(),
                        employee.getJobRole()
                );
                tmEmployeeList.add(employeeTm);
            }
            tblEmployee.setItems(tmEmployeeList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colEmpTel.setCellValueFactory(new PropertyValueFactory<>("empTel"));
        colEmpJobRole.setCellValueFactory(new PropertyValueFactory<>("empJobRole"));
    }

    @FXML
    void btnEmpDeleteOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();

        try {
            boolean isDeleted = employeeRepo.deleteEmployee(empId);
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            loadEmployeeTable();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnEmpSaveOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        String empName = txtEmpName.getText();
        String empAddress = txtEmpAddress.getText();
        String empTel = txtEmpTel.getText();
        String empJobRole = txtEmpJobRole.getText();
        String userId = LoginFormController.getInstance().userId;

        Employee employee = new Employee(empId, empName, empAddress, empTel, empJobRole, userId);
        try {
            boolean isSaved = employeeRepo.saveEmployee(employee);
            new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
            loadEmployeeTable();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        String empName = txtEmpName.getText();
        String empAddress = txtEmpAddress.getText();
        String empTel = txtEmpTel.getText();
        String empJobRole = txtEmpJobRole.getText();
        String userId = LoginFormController.getInstance().userId;

        Employee employee = new Employee(empId, empName, empAddress, empTel, empJobRole, userId);
        try {
            boolean isSaved = employeeRepo.updateEmployee(employee);
            new Alert(Alert.AlertType.CONFIRMATION, "Updated").show();
            loadEmployeeTable();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchEmployeeOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        try {
            Employee employee = employeeRepo.searchEmployee(empId);
            if (employee != null) {
                txtEmpId.setText(employee.getId());
                txtEmpName.setText(employee.getName());
                txtEmpAddress.setText(employee.getAddress());
                txtEmpTel.setText(employee.getTel());
                txtEmpJobRole.setText(employee.getJobRole());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
