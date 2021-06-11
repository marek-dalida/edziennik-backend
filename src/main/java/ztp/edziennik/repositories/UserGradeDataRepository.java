package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztp.edziennik.models.UserGradeData;

import java.util.List;


public interface UserGradeDataRepository extends JpaRepository<UserGradeData, Long> {
    public List<UserGradeData> findByGradeType_Group_Id(Long groupId);
}
