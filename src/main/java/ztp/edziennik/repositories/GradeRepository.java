package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztp.edziennik.models.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
