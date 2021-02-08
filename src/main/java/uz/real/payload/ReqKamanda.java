package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqKamanda {

    public String fullname;

    private String address;

    private String phone;

    private String position;

    private String biography;

    private Long image_id;


}
