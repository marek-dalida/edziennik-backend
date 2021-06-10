package ztp.edziennik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.GradeType;
import ztp.edziennik.repositories.GradeTypeRepository;

import java.util.Optional;


@Service
public class GradeTypeService {
    private final GradeTypeRepository gradeTypeRepository;

    @Autowired
    public GradeTypeService(GradeTypeRepository gradeTypeRepository) {
        this.gradeTypeRepository = gradeTypeRepository;
    }

    public void createGradeType(GradeType model){
        gradeTypeRepository.save(model);
    }

    public Optional<GradeType> getGradeTypeById(Long id){
        return gradeTypeRepository.findById(id);
    }

    public void deleteGradeType(Long id){
        GradeType gradeType = getGradeTypeById(id).get();
        gradeTypeRepository.delete(gradeType);
    }


}
