package uz.real.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TeacherImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;

    private String originalFileName;

    private Long size;

    private String contentType;


    public TeacherImages(String fileName, String originalFileName, Long size, String contentType) {
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
    }
}
