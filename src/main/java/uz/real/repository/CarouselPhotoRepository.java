package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.real.entity.CarouselPhoto;
import uz.real.entity.Talant;

import java.util.List;

@Repository
public interface CarouselPhotoRepository extends JpaRepository<CarouselPhoto, Long> {

    @Query(value = "select * from carousel_photo order by id desc limit 3", nativeQuery = true)
    List<CarouselPhoto> findAllById(Long id);
}
