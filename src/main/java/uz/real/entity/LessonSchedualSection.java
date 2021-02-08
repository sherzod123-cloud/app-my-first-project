package uz.real.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LessonSchedualSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sectionName;

    private String mondayTime;

    private String tuesdayTime;

    private String wednesdayTime;

    private String thursdayTime;

    private String fridayTime;

    private String saturdayTime;

    @ManyToOne
    private LessonSchedule lessonSchedule;



}
