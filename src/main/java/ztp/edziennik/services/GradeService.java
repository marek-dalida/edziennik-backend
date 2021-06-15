package ztp.edziennik.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.Grade;
import ztp.edziennik.models.GradeData;
import ztp.edziennik.models.UserGradeData;
import ztp.edziennik.repositories.GradeDataRepository;
import ztp.edziennik.repositories.GradeRepository;
import ztp.edziennik.repositories.UserGradeDataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final GradeDataRepository gradeDataRepository;
    private final UserGradeDataRepository userGradeDataRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository,
                        GradeDataRepository gradeDataRepository,
                        UserGradeDataRepository userGradeDataRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeDataRepository = gradeDataRepository;
        this.userGradeDataRepository = userGradeDataRepository;
    }

    public Optional<Grade> findGradeById(Long id) {
        return gradeRepository.findById(id);
    }

    public Grade createGrade(Grade model) {
        return gradeRepository.save(model);
    }

    public void deleteGradeById(Grade grade) {
        gradeRepository.delete(grade);
    }

    public Optional<GradeData> findGradeDataById(Long id) {
        return gradeDataRepository.findById(id);
    }

    public List<GradeData> findUserGrades(Long userId) {
        return gradeDataRepository.findByUserId(userId);
    }

    public List<GradeData> findUserGradesByGroupId(Long userId, Long groupId) {
        return gradeDataRepository.findByUserIdAndGradeType_Group_Id(userId, groupId);
    }

    public List<UserGradeData> findGradesByGroupId(Long groupId) {
        return userGradeDataRepository.findByGradeType_Group_Id(groupId);
    }

    public void deleteGradesByGradeType(Long gradeTypeId){
        gradeRepository.deleteByGradeTypeId(gradeTypeId);
    }

}
