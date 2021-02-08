package uz.real.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "talant")
public class Talant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Integer attachmentId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TalantAttachment> talantAttachments;

    public Talant(String fullName, String address, String position, Integer attachmentId, List<TalantAttachment> talantAttachments) {
        this.fullName = fullName;
        this.address = address;
        this.position = position;
        this.attachmentId = attachmentId;
        this.talantAttachments = talantAttachments;
    }
}
