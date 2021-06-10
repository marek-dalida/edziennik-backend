package ztp.edziennik.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.Grade;
import ztp.edziennik.repositories.GradeRepository;

import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public Optional<Grade> findGradeById(Long id){
        return gradeRepository.findById(id);
    }

    public void createGrade(Grade model){
        gradeRepository.save(model);
    }

    public void deleteGradeById(Long id){
        Grade grade = findGradeById(id).orElseThrow(() -> new RuntimeException("grade not found"));
        gradeRepository.delete(grade);
    }

}
