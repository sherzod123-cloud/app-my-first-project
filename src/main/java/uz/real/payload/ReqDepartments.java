package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDepartments {

    private String name;

    private String address;

    private String phoneOne;

    private String phoneTwo;

    private String phoneThree;

    private String directFullName;


}
