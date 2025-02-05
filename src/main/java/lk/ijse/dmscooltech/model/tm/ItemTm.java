package lk.ijse.dmscooltech.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTm {
    private String code;
    private String description;
    private String model;
    private int qtyOnHand;
    private double unitPrice;
    private String date;
}
