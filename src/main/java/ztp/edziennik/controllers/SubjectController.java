package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.exceptions.NoPermissionException;
import ztp.edziennik.exceptions.ObjectNotFoundException;
import ztp.edziennik.exceptions.UserNotFoundException;
import ztp.edziennik.models.Subject;
import ztp.edziennik.models.User;
import ztp.edziennik.services.SubjectService;
import ztp.edziennik.services.UserService;
import ztp.edziennik.utils.Role;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubjectController {

    SubjectService subjectService;
    UserService userService;

    @Autowired
    public SubjectController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/subjects", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<Subject>> findSubjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            @RequestParam(defaultValue = "") String name,
            Principal principal

    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Page<Subject> subjects;
        if (name.isEmpty()) {
            subjects = subjectService.findAll(page, size, sort, dir);
        } else {
            subjects = subjectService.findByNameContains(name, page, size, sort, dir);
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/subjects", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Subject> createSubject(
            @RequestBody Subject subject,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);
        Subject newSubject = subjectService.saveSubject(subject);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Subject> findById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Subject subject = subjectService.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Subject"));
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Subject> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        // Use only when subject does not have any group, members, gradeTypes and grades
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);
        Subject subject = subjectService.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Subject"));

        subjectService.deleteSubject(subject);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

}
