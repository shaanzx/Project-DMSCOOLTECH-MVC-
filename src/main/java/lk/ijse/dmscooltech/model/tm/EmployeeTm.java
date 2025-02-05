package lk.ijse.dmscooltech.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTm {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String jobRole;
}
