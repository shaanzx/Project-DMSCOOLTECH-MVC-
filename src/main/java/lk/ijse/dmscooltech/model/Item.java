package lk.ijse.dmscooltech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String code;
    private String description;
    private String model;
    private double unitPrice;
    private int qtyOnHand;
    private String date;
}
