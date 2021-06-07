package ztp.edziennik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ztp.edziennik.models.SubjectGroup;

public interface SubjectGroupRepository extends PagingAndSortingRepository<SubjectGroup, Long> {
 Page<SubjectGroup> findBySubjectId(Long subjectId,  Pageable pageable);
}
