package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.AboutImg;

@Repository
public interface AboutImageRepository extends JpaRepository<AboutImg, Long> {
}
