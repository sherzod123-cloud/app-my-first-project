package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.AboutCarousel;

@Repository
public interface AboutCarouselRepository extends JpaRepository<AboutCarousel, Long> {
}
