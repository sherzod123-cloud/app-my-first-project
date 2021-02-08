package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.real.entity.Kamanda;

import java.util.List;

public interface KamandaRepository extends JpaRepository<Kamanda, Long> {

    @Query(value = "select * from kamanda order by id desc limit 3", nativeQuery = true)
    List<Kamanda> findAllById(Long id);

}
