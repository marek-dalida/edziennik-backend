package ztp.edziennik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.Subject;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
    Page<Subject> findSubjectBySubjectNameContains(@Param("name") String name, Pageable pageable);
}
