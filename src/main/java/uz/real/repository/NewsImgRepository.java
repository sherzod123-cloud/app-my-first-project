package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.News;
import uz.real.entity.NewsImg;

import java.util.List;

@Repository
public interface NewsImgRepository extends JpaRepository<NewsImg, Long> {

    List<NewsImg> findAllById(Long id);

}
