package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.Subject;
import ztp.edziennik.services.SubjectService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubjectController {

    SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/subject", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<Subject>> findAll(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ){
        HttpHeaders headers = new HttpHeaders();
        Page<Subject> subjects = subjectService.findAll(page, size, sort, dir);
        if (subjects == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(subjects, headers, HttpStatus.OK);
    }
}
