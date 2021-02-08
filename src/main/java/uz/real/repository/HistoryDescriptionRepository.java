package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.HistoryDescription;

@Repository
public interface HistoryDescriptionRepository extends JpaRepository<HistoryDescription, Long> {
}
