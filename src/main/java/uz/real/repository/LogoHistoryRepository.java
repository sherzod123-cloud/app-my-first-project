package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.LogoHistory;

@Repository
public interface LogoHistoryRepository extends JpaRepository<LogoHistory, Long> {
}
