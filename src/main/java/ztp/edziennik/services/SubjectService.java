package ztp.edziennik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.Subject;
import ztp.edziennik.repositories.SubjectRepository;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Page<Subject> findAll(int page, int size, String sort, String dir) {
        Pageable pageable = ServiceUtils.setPageableWithSort(page, size, sort, dir);
        return subjectRepository.findAll(pageable);
    }


}
