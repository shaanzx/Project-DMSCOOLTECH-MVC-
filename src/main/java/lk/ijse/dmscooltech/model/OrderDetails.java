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
    private Date orderDate;
    private int qty;
    private Double OrderAmount;
    private String orderId;
    private String itemCode;
}
