package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.exceptions.NoPermissionException;
import ztp.edziennik.exceptions.NotGroupMemberException;
import ztp.edziennik.exceptions.ObjectNotFoundException;
import ztp.edziennik.exceptions.UserNotFoundException;
import ztp.edziennik.models.*;
import ztp.edziennik.services.GradeService;
import ztp.edziennik.services.SubjectGroupService;
import ztp.edziennik.services.UserService;
import ztp.edziennik.utils.Role;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GradeController {
    GradeService gradeService;
    UserService userService;
    SubjectGroupService subjectGroupService;

    @Autowired
    public GradeController(GradeService gradeService, UserService userService, SubjectGroupService subjectGroupService) {
        this.gradeService = gradeService;
        this.userService = userService;
        this.subjectGroupService = subjectGroupService;
    }


    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/grades", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Grade> createGrade(
            @RequestBody Grade grade,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);
        SubjectGroup gradeSubjectGroup = subjectGroupService
                .findGroupByGradeType(grade.getGradeTypeId())
                .orElseThrow(() -> new ObjectNotFoundException("Group by GradeType with id= " + grade.getGradeTypeId() + " not found"));
        if (!user.getUserId().equals(gradeSubjectGroup.getGroupTeacherId())) throw new NoPermissionException(email);
        userService.findUserGroupById(new UserGroupId(grade.getUserId(), gradeSubjectGroup.getId()))
                .orElseThrow(() -> new NotGroupMemberException(grade.getUserId()));


        grade.setCreationDate(new Date());
        grade.setTeacherId(user.getUserId());
        Grade newGrade = gradeService.createGrade(grade);
        return new ResponseEntity<>(newGrade, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/grades/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Grade> findByGradeId(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Grade grade = gradeService.findGradeById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Grade"));
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/grades/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Grade> deleteGrade(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Grade grade = gradeService.findGradeById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Grade"));
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        SubjectGroup gradeSubjectGroup = subjectGroupService
                .findGroupByGradeType(grade.getGradeTypeId())
                .orElseThrow(() -> new ObjectNotFoundException("Group by GradeType with id= " + grade.getGradeTypeId() + " not found"));
        if ((!user.getRole().equals(Role.TEACHER)) || (!user.getUserId().equals(gradeSubjectGroup.getGroupTeacherId()))) {
            throw new NoPermissionException(email);
        }

        gradeService.deleteGradeById(grade);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/grades/{id}/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<GradeData> findGradeDataById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        GradeData gradeData = gradeService.findGradeDataById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Grade"));
        return new ResponseEntity<>(gradeData, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/user/{userId}/grades", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GradeData>> findUserGrades(
            @PathVariable("userId") Long userId,
            Principal principal
    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<GradeData> userGrades = gradeService.findUserGrades(userId);
        return new ResponseEntity<>(userGrades, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/user/{userId}/group/{groupId}/grades", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GradeData>> findUserGradesByGroupId(
            @PathVariable("userId") Long userId,
            @PathVariable("groupId") Long groupId,
            Principal principal
    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<GradeData> userGrades = gradeService.findUserGradesByGroupId(userId, groupId);
        return new ResponseEntity<>(userGrades, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/group/{groupId}/grades", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<UserGradeData>> findGroupGrades(
            @PathVariable("groupId") Long groupId,
            Principal principal
    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<UserGradeData> groupGrades = gradeService.findGradesByGroupId(groupId);
        return new ResponseEntity<>(groupGrades, HttpStatus.OK);
    }
}
