package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
