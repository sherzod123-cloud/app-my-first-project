package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.LessonSchedule;

@Repository
public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Long> {

}
