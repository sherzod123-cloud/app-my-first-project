package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
