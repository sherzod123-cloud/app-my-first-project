package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCourse {

    private String first_name;

    private String last_name;

    private String middle_name;

    private Date date_of_birth;

    private String phone;



}
