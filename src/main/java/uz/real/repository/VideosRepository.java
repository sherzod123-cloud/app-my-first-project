package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.real.entity.Teacher;
import uz.real.entity.Videos;

import java.util.List;

public interface VideosRepository extends JpaRepository<Videos, Long> {

    @Query(value = "select * from videos order by id desc limit 1", nativeQuery = true)
    List<Videos> findAllById(Long id);

}
