package lk.ijse.dmscooltech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    private String paymentId;
    private String customerId;
    private String orderId;
    private double totalAmount;
    private String paymentDate;
}
