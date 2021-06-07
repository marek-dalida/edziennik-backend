package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.Subject;
import ztp.edziennik.models.SubjectGroup;
import ztp.edziennik.services.SubjectGroupService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubjectGroupController {

    SubjectGroupService subjectGroupService;

    @Autowired
    public SubjectGroupController(SubjectGroupService subjectGroupService) {
        this.subjectGroupService = subjectGroupService;
    }

    @RequestMapping(value = "/subject/groups/{subjectId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<SubjectGroup>> findSubjectBySubjectId(
            @PathVariable("subjectId") Long subjectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Principal principal

    ) {
        HttpHeaders headers = new HttpHeaders();
        Page<SubjectGroup> subjectsGroups = subjectGroupService.findBySubjectId(subjectId, page, size, sort, dir);
        return new ResponseEntity<>(subjectsGroups, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<SubjectGroup> findSubjectGroupById(
            @PathVariable("groupId") Long groupId,
            Principal principal

    ) {
        HttpHeaders headers = new HttpHeaders();
        SubjectGroup group = subjectGroupService.findById(groupId).orElseThrow(() -> new RuntimeException("Not found subject group= " + groupId ));
        return new ResponseEntity<>(group, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<SubjectGroup>> findAllSubjectsGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Principal principal

    ) {
        HttpHeaders headers = new HttpHeaders();
        Page<SubjectGroup> subjectsGroups = subjectGroupService.findAll( page, size, sort, dir);
        return new ResponseEntity<>(subjectsGroups, headers, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/groups", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SubjectGroup> findAllSubjectsGroups(
            @RequestBody SubjectGroup model,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        subjectGroupService.createSubjectGroup( model);
        return new ResponseEntity<>(model, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Subject> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        subjectGroupService.deleteSubjectGroup(id);
        //TODO: delete all other dependencies
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
