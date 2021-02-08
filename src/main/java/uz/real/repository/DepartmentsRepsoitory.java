package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.Departments;

@Repository
public interface DepartmentsRepsoitory extends JpaRepository<Departments, Long> {

}
