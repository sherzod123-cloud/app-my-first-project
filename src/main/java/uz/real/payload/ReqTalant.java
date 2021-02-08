package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqTalant {

    private String fullName;

    private String address;

    private String position;

    private Integer attachmentId;

}
