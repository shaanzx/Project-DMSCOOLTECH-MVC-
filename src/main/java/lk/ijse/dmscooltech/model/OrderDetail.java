package lk.ijse.dmscooltech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetail {
    private Date date;
    private int qty;
    private double unitPrice;
    private String orderId;
    private String itemCode;
}
