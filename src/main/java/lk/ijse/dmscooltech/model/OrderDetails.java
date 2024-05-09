package lk.ijse.dmscooltech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.desktop.ScreenSleepEvent;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails {
    private String orderId;
    private String itemCode;
    private Date orderDate;
    private int qty;
    private  double unitPrice;
    private double OrderAmount;
}
