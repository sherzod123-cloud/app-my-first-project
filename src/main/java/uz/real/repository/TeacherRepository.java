package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.real.entity.Teacher;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>  {
    Optional<Teacher> findById(Integer id);



    @Query(value = "select * from teacher order by id desc limit 3", nativeQuery = true)
    List<Teacher> findAllById(Integer id);

//    @Query(value = "select * from teacher t where " +
//            "t.full_name like '%s%'", nativeQuery = true)
//    public List<Teacher> findAll(String s);

    @Query(value="select * from teacher t where t.full_name like %:keyword% " +
            "or t.position like %:keyword% " +
            "or t.biography like %:keyword%", nativeQuery=true)
    List<Teacher> findUsersByKeyword(@Param("keyword") String keyword);



}
