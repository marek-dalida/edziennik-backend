package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.GradeType;

import java.util.List;

public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {
    List<GradeType> findByGroupId(Long groupId);
    @Query("select  gt from GradeType as gt\n" +
            "join SubjectGroup as sg on sg.id = gt.groupId \n" +
            "where sg.groupTeacherId = :teacherId")
    List<GradeType> findTeacherGradeTypes(@Param("teacherId") Long teacherId);
}
