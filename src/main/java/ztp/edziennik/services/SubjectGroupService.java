package ztp.edziennik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.SubjectGroup;
import ztp.edziennik.repositories.SubjectGroupRepository;

import java.util.Optional;

@Service
public class SubjectGroupService {
    private final SubjectGroupRepository subjectGroupRepository;

    @Autowired
    public SubjectGroupService(SubjectGroupRepository subjectGroupRepository) {
        this.subjectGroupRepository = subjectGroupRepository;
    }

    public Page<SubjectGroup> findAll(int page, int size, String sort, String dir) {
        Pageable pageable = ServiceUtils.setPageableWithSort(page, size, sort, dir);
        return subjectGroupRepository.findAll(pageable);
    }

    public Page<SubjectGroup> findBySubjectId(Long subjectId, int page, int size, String sort, String dir) {
        Pageable pageable = ServiceUtils.setPageableWithSort(page, size, sort, dir);
        return subjectGroupRepository.findAll(pageable);
    }

    public Optional<SubjectGroup> findById(Long id){
        return subjectGroupRepository.findById(id);
    }

    public void createSubjectGroup(SubjectGroup model){
        subjectGroupRepository.save(model);
    }

    public void  deleteSubjectGroup(Long id){
        SubjectGroup sg = findById(id).orElseThrow(() -> new RuntimeException("Not found subject group= " + id ));
        subjectGroupRepository.delete(sg);
    }
}
