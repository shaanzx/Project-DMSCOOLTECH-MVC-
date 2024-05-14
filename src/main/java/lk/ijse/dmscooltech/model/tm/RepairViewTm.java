package lk.ijse.dmscooltech.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepairViewTm {
    private String repairId;
    private String vehicleNo;
    private String repairDate;
    private String itemCode;
    private int itemQty;
    private double itemUnitPrice;
    private double repairCost;
    private double totalPrice;
}
