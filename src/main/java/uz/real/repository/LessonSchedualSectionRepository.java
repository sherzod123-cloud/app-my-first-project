package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.LessonSchedualSection;

import java.util.List;

@Repository
public interface LessonSchedualSectionRepository extends JpaRepository<LessonSchedualSection, Long> {

    List<LessonSchedualSection> getAllByLessonSchedule_Id(Long lessonId);
}
