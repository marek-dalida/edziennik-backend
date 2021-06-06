package ztp.edziennik.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ztp.edziennik.models.Subject;

public interface SubjectGroupRepository extends PagingAndSortingRepository<Subject, Long> {
}
