package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.Grade;

import javax.transaction.Transactional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Transactional
    @Modifying
    @Query("delete from Grade where gradeTypeId =:gradeTypeId ")
    void deleteByGradeTypeId(@Param("gradeTypeId") Long gradeTypeId);

}
