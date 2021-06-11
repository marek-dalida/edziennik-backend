package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztp.edziennik.models.GradeData;

import java.util.List;

public interface GradeDataRepository extends JpaRepository<GradeData, Long> {

    List<GradeData> findByUserId(Long userId);

    List<GradeData> findByUserIdAndGradeType_Group_Id(Long userId, Long groupId);
}
