package ztp.edziennik.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.Grade;
import ztp.edziennik.models.GradeData;
import ztp.edziennik.repositories.GradeDataRepository;
import ztp.edziennik.repositories.GradeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final GradeDataRepository gradeDataRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository, GradeDataRepository gradeDataRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeDataRepository = gradeDataRepository;
    }

    public Optional<Grade> findGradeById(Long id){
        return gradeRepository.findById(id);
    }

    public Grade createGrade(Grade model){
        return gradeRepository.save(model);
    }

    public void deleteGradeById(Long id){
        Grade grade = findGradeById(id).orElseThrow(() -> new RuntimeException("grade not found"));
        gradeRepository.delete(grade);
    }

    public Optional<GradeData> findGradeDataById(Long id){
        return gradeDataRepository.findById(id);
    }

    public List<GradeData> findUserGrades(Long userId){
        return gradeDataRepository.findByUserId(userId);
    }

    public List<GradeData> findUserGradesByGroupId(Long userId, Long groupId){
        return gradeDataRepository.findByUserIdAndGradeType_Group_Id(userId, groupId);
    }

}
