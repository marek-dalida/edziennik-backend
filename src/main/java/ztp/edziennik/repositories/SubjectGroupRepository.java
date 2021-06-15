package ztp.edziennik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.SubjectGroup;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SubjectGroupRepository extends PagingAndSortingRepository<SubjectGroup, Long> {
    Page<SubjectGroup> findBySubjectId(Long subjectId, Pageable pageable);

    @Query("select sg from SubjectGroup as sg\n" +
            "join UserGroup as ug on ug.userGroupId.groupId = sg.id\n" +
            " where ug.userGroupId.userId = :userId")
    Page<SubjectGroup> findUserGroups(@Param("userId") Long userId, Pageable pageable);

    Page<SubjectGroup> findByGroupTeacherId(Long groupTeacherId, Pageable pageable);

    @Query("select sg from GradeType as gt\n" +
            " join  SubjectGroup as sg on gt.groupId = sg.id\n" +
            " where gt.id = :gradeType")
    Optional<SubjectGroup> findGroupByGradeType(@Param("gradeType") Long gradeType);

}
