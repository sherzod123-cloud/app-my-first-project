package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCarouselImg {

    private String fileName;

    private String originalFileName;

    private String contentType;

    private Long size;

}
