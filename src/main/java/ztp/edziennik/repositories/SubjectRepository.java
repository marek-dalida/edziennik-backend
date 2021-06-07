package ztp.edziennik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ztp.edziennik.models.Subject;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
    Page<Subject> findSubjectBySubjectNameContains(String subjectName, Pageable pageable);
}
