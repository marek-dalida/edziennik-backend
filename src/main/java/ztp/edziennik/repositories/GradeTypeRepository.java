package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztp.edziennik.models.GradeType;

public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {
}
