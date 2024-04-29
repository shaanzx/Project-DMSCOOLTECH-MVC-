package lk.ijse.dmscooltech.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepairTm {
    private String vehicleNo;
    private String employeeId;
    private String description;
    private double repairCost;
    private String repairDate;
    private double totalAmount;
    private JFXButton deleteButton;
}
