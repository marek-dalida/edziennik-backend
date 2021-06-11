package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.Grade;
import ztp.edziennik.models.User;
import ztp.edziennik.services.GradeService;
import ztp.edziennik.services.UserService;
import ztp.edziennik.utils.Role;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GradeController {
    GradeService gradeService;
    UserService userService;

    @Autowired
    public GradeController(GradeService gradeService, UserService userService) {
        this.gradeService = gradeService;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/grades", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Grade> createGrade(
            @RequestBody Grade grade,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElse(null);

        if(user != null && user.getRole().equals(Role.TEACHER)){
            grade.setCreationDate(new Date());
            grade.setTeacherId(user.getUserId());
            Grade newGrade = gradeService.createGrade(grade);
            return new ResponseEntity<>(newGrade, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


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

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/grades/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Grade> deleteGrade(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        gradeService.deleteGradeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
