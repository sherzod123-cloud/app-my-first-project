package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.NewsBottomMenuImg;

@Repository
public interface NewsBottomMenuImgRepository extends JpaRepository<NewsBottomMenuImg, Long> {
}
