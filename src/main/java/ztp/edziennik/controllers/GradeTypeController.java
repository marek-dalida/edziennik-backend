package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.exceptions.ObjectNotFoundException;
import ztp.edziennik.exceptions.UserNotFoundException;
import ztp.edziennik.models.GradeType;
import ztp.edziennik.models.User;
import ztp.edziennik.services.GradeService;
import ztp.edziennik.services.GradeTypeService;
import ztp.edziennik.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GradeTypeController {

    GradeTypeService gradeTypeService;
    UserService userService;
    GradeService gradeService;

    @Autowired
    public GradeTypeController(GradeTypeService gradeTypeService, UserService userService, GradeService gradeService) {
        this.gradeTypeService = gradeTypeService;
        this.userService = userService;
        this.gradeService = gradeService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/grade/types", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<GradeType> createGrade(
            @RequestBody GradeType gradeType,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        GradeType newGradeType = gradeTypeService.createGradeType(gradeType);
        return new ResponseEntity<>(newGradeType, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/grade/types/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<GradeType> findById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        GradeType gradeType = gradeTypeService.getGradeTypeById(id).orElseThrow(() -> new ObjectNotFoundException(id, "GradeType"));
        return new ResponseEntity<>(gradeType, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/grade/types/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<GradeType> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();

        GradeType gradeType = gradeTypeService.getGradeTypeById(id).orElseThrow(() -> new ObjectNotFoundException(id, "GradeType"));

        //delete all grades with this gradeType
        gradeService.deleteGradesByGradeType(gradeType.getId());

        gradeTypeService.deleteGradeType(gradeType);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/group/{groupId}/grade/types", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GradeType>> findGradeTypesByGroupId(
            @PathVariable("groupId") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        List<GradeType> gradeTypes = gradeTypeService.findGradeTypesByGroupId(id);
        return new ResponseEntity<>(gradeTypes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/grade/types", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GradeType>> getAllGradeTypes(
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        List<GradeType> gradeTypes = gradeTypeService.getAllGradeTypes();
        return new ResponseEntity<>(gradeTypes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/teacher/grade/types", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GradeType>> findTeacherGradeTypes(
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        List<GradeType> teacherGradeTypes = gradeTypeService.findTeacherGradeTypes(user.getUserId());
        return new ResponseEntity<>(teacherGradeTypes, HttpStatus.OK);
    }
}
