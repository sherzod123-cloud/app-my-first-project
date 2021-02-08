package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.real.entity.NewsBottomMenu;

import java.util.List;

@Repository
public interface NewsBottomMenuRepository extends JpaRepository<NewsBottomMenu, Long> {


    @Query(value = "select * from news_bottom_menu where is_publish='true' order by id desc limit 2 ", nativeQuery = true)
    List<NewsBottomMenu> findAllById();

    @Query(value = "select * from  news_bottom_menu where is_publish='false' order by id desc limit 1", nativeQuery = true)
    NewsBottomMenu getById();




}
