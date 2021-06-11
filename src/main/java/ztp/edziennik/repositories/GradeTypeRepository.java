package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztp.edziennik.models.GradeType;

import java.util.List;

public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {
    List<GradeType> findByGroupId(Long groupId);
}
