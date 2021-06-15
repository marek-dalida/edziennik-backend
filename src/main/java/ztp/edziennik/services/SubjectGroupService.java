package ztp.edziennik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.SubjectGroup;
import ztp.edziennik.models.User;
import ztp.edziennik.models.UserGroup;
import ztp.edziennik.repositories.SubjectGroupRepository;
import ztp.edziennik.repositories.UserGroupRepository;
import ztp.edziennik.utils.Role;

import java.util.Optional;

@Service
public class SubjectGroupService {
    private final SubjectGroupRepository subjectGroupRepository;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public SubjectGroupService(SubjectGroupRepository subjectGroupRepository, UserGroupRepository userGroupRepository) {
        this.subjectGroupRepository = subjectGroupRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public Page<SubjectGroup> findAll(int page, int size, String sort, String dir) {
        Pageable pageable = ServiceUtils.setPageableWithSort(page, size, sort, dir);
        return subjectGroupRepository.findAll(pageable);
    }

    public Page<SubjectGroup> findBySubjectId(Long subjectId, int page, int size, String sort, String dir) {
        Pageable pageable = ServiceUtils.setPageableWithSort(page, size, sort, dir);
        return subjectGroupRepository.findBySubjectId(subjectId, pageable);
    }

    public Optional<SubjectGroup> findById(Long id){
        return subjectGroupRepository.findById(id);
    }

    public SubjectGroup createSubjectGroup(SubjectGroup model){
        return subjectGroupRepository.save(model);
    }

    public void  deleteSubjectGroup(Long id){
        SubjectGroup sg = findById(id).orElseThrow(() -> new RuntimeException("Not found subject group= " + id ));
        subjectGroupRepository.delete(sg);
    }

    public Page<SubjectGroup> findUserGroups(User user, int page, int size, String sort, String dir){
        Pageable pageable = ServiceUtils.setPageableWithSort(page, size, sort, dir);
        Page<SubjectGroup> groups;
        if ( user.getRole() == Role.TEACHER){
            groups = subjectGroupRepository.findByGroupTeacherId(user.getUserId(), pageable);
        } else {
             groups =subjectGroupRepository.findUserGroups(user.getUserId(), pageable);
        }
        return groups;
    }

    public void removeUserFromGroup(Long userId, Long groupId){
        userGroupRepository.deleteUserFromGroup(userId, groupId);
    }

    public void addUserToGroup(UserGroup model){
         userGroupRepository.save(model);
    }

    public Optional<SubjectGroup> findGroupByGradeType(Long gradeType){
        return subjectGroupRepository.findGroupByGradeType(gradeType);
    }
}
