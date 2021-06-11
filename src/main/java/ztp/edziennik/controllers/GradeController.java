package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.Grade;
import ztp.edziennik.services.GradeService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GradeController {
    GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @RequestMapping(value = "/grades", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Grade> createGrade(
            @RequestBody Grade grade,
            Principal principal
    ) {
        gradeService.createGrade(grade);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/grades/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Grade> findByGradeId(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Grade grade = gradeService.findGradeById(id).orElse(null);
        if (grade == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Grade> deleteGrade(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        gradeService.deleteGradeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
