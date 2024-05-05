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
    private String description;
    private String repairDate;
    private double repairCost;
    private JFXButton deleteButton;
}
