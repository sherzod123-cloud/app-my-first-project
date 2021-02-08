package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.real.entity.OurGoal;

public interface OurGoalRepository extends JpaRepository<OurGoal, Long> {
}
