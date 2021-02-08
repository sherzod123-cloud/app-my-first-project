package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.real.entity.PhotoGalery;

public interface PhotoGaleryRepository extends JpaRepository<PhotoGalery, Long> {
}
