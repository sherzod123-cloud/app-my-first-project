package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.Role;

import java.util.List;

@Repository
public interface RoleRepositroy extends JpaRepository<Role, Long> {

    List<Role> findAllByRoleName(String rolename);
}
