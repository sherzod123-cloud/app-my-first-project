package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.HistoryImg;

@Repository
public interface HistoryImgRepository extends JpaRepository<HistoryImg, Long> {
}
