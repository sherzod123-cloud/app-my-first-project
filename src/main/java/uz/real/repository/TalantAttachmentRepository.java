package uz.real.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.real.entity.TalantAttachment;

import java.util.List;

@Repository
public interface TalantAttachmentRepository extends JpaRepository<TalantAttachment, Integer> {

    List<TalantAttachment> findAllById(Integer id);
}
