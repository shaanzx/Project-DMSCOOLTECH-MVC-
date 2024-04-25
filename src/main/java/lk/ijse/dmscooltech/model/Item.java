package lk.ijse.dmscooltech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String code;
    private String description;
    private String model;
    private int qtyOnHand;
    private double unitPrice;
    private String date;

}
