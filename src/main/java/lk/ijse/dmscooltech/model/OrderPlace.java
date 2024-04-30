package lk.ijse.dmscooltech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPlace {
    private Order order;
    private List<OrderDetail> orderDetails;
}
