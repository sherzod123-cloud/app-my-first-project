package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.TeacherImages;

import java.util.List;

@Repository
public interface TeacherImagesRepository extends JpaRepository<TeacherImages, Integer> {
 List<TeacherImages> findAllById(Integer id);

}
