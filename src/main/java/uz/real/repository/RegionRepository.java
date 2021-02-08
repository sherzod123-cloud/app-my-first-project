package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
