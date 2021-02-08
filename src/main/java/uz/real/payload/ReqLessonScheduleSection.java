package uz.real.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.real.entity.LessonSchedule;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqLessonScheduleSection {

    private String sectionName;

    private String mondayTime;

    private String tuesdayTime;

    private String wednesdayTime;

    private String thursdayTime;

    private String fridayTime;

    private String saturdayTime;




}
