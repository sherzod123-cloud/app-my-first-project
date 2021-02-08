package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.real.entity.AboutText;

import java.util.List;

@Repository
public interface AboutTextRepository extends JpaRepository<AboutText, Long> {

    @Query(value = "select  * from about_text order by id desc limit 1", nativeQuery = true)
    List<AboutText> findAllById(Long id);
}
