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

    @RequestMapping(value = "/subjects", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<Subject>> findSubjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            @RequestParam(defaultValue = "") String name,
            Principal principal

    ) {
        HttpHeaders headers = new HttpHeaders();
        Page<Subject> subjects;
        if (name.isEmpty()) {
            subjects = subjectService.findAll(page, size, sort, dir);
        } else {
            subjects = subjectService.findByNameContains(name, page, size, sort, dir);
        }
        if (subjects == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(subjects, headers, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/subjects", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Subject> createSubject(
            @RequestBody Subject subject,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        subjectService.saveSubject(subject);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Subject> findById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        Subject subject = subjectService.findById(id).orElseThrow(() -> new RuntimeException("Subject not found id= " + id));
        return new ResponseEntity<>(subject, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Subject> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        subjectService.deleteSubject(id);
        //TODO: delete all other dependencies
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
