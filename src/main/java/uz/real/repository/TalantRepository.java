package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.real.entity.Talant;
import uz.real.entity.Teacher;

import java.util.List;

@Repository
public interface TalantRepository extends JpaRepository<Talant, Integer> {

    @Query(value = "select * from talant order by id desc limit 3", nativeQuery = true)
    List<Talant> findAllById(Integer id);
}
