package lk.ijse.dmscooltech.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class EmployeeFormController {

    @FXML
    private JFXComboBox<?> cmbUserId;

    @FXML
    private TableColumn<?, ?> colEmpAddress;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpJobRole;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpTel;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtEmpAddres;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpJobRole;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpTel;

    @FXML
    void btnEmpSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmpDeleteOnAction(ActionEvent event) {

    }

}
