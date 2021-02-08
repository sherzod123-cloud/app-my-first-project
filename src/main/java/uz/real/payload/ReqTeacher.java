package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.TeacherImages;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqTeacher {

    private String fullName;

    private String position;

    private String phoneNumber;

    private String biography;

    private Integer image_id;

}
