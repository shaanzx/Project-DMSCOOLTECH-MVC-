package lk.ijse.dmscooltech.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.dmscooltech.model.DashBoard;
import lk.ijse.dmscooltech.repository.ItemRepo;
import lk.ijse.dmscooltech.repository.OrderRepo;
import lk.ijse.dmscooltech.repository.RepairRepo;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class DashboardFormController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblRepairCount;

    @FXML
    private Pane pagingPane;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countOrder();
        getRepairCount();
        setPieChart();
        setDate();
        //timenow();
      //  setBarChart();
    }

/*    private void timenow(){
        Thread thread =new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM,  dd, yyyy");
            while (true){
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                String timenow1 = sdf1.format(new Date());

                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                    lblDate.setText(timenow1);
                });
            }
        });
        thread.start();
    }*/

    private void setDate() {
        LocalDate date = LocalDate.now();
        lblDate.setText(String.valueOf(date));
    }

    private void setPieChart() {
        try {
            ObservableList<PieChart.Data> obList = FXCollections.observableArrayList();
            ArrayList<PieChart.Data> data = DashBoard.getPieChartData();
            for (PieChart.Data datum : data) {
                obList.add(datum);
            }
            pieChart.getData().addAll(obList);
            pieChart.setTitle("Most Trending Products");
            pieChart.setStartAngle(180);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void getRepairCount() {
        RepairRepo repairRepo = new RepairRepo();
        int count = 0;
        try {
            count = repairRepo.countRepairId();
            lblRepairCount.setText(String.valueOf("0" + count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void countOrder() {
        OrderRepo orderRepo = new OrderRepo();
        try {
            int count = orderRepo.countOrderId();
            lblOrderCount.setText(String.valueOf("0" + count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setBarChart() throws SQLException {
        ObservableList<XYChart.Series<String, Integer>> barChartData = ItemRepo.getDataToBarChart();

        barChart.setData(barChartData);
    }
}
