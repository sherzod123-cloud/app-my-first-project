package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.real.entity.News;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "select * from news where is_published='true' limit 1", nativeQuery = true)
    News getById(Long id);

    @Query(value = "select * from news where is_view='true' limit 4", nativeQuery = true)
    List<News> findAllById(Long id);

    @Query(value = "select * from news order by id desc limit 3", nativeQuery = true)
    List<News> getAllById(Long id);

}
