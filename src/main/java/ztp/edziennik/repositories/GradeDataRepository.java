package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztp.edziennik.models.GradeData;

public interface GradeDataRepository extends JpaRepository<GradeData, Long> {
}
