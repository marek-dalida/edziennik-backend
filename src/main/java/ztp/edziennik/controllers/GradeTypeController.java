package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.GradeType;
import ztp.edziennik.services.GradeTypeService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GradeTypeController {

    GradeTypeService gradeTypeService;

    @Autowired
    public GradeTypeController(GradeTypeService gradeTypeService) {
        this.gradeTypeService = gradeTypeService;
    }

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
        GradeType gradeType = gradeTypeService.getGradeTypeById(id).get();
        return new ResponseEntity<>(gradeType, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/grade/types/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<GradeType> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        gradeTypeService.deleteGradeType(id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/group/{groupId}/grade/types", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GradeType>> findGradeTypesByGroupId(
            @PathVariable("groupId") Long id,
            Principal principal
    ){
        HttpHeaders headers = new HttpHeaders();
        List<GradeType> gradeTypes = gradeTypeService.findGradeTypesByGroupId(id);
        return new ResponseEntity<>(gradeTypes, headers, HttpStatus.OK);
    }
}
