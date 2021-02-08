package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
