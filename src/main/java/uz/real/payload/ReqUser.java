package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.real.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUser {

    private String fullName;

    private String username;

    private String password;

    private Integer age;


}
