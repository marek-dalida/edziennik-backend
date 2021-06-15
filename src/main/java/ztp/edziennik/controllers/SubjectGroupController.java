package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.exceptions.NoPermissionException;
import ztp.edziennik.exceptions.NotGroupMemberException;
import ztp.edziennik.exceptions.ObjectNotFoundException;
import ztp.edziennik.exceptions.UserNotFoundException;
import ztp.edziennik.models.*;
import ztp.edziennik.services.SubjectGroupService;
import ztp.edziennik.services.SubjectService;
import ztp.edziennik.services.UserService;
import ztp.edziennik.utils.Role;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubjectGroupController {

    SubjectGroupService subjectGroupService;
    UserService userService;
    SubjectService subjectService;

    @Autowired
    public SubjectGroupController(SubjectGroupService subjectGroupService, UserService userService, SubjectService subjectService) {
        this.subjectGroupService = subjectGroupService;
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/subject/{subjectId}/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<SubjectGroup>> findSubjectBySubjectId(
            @PathVariable("subjectId") Long subjectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Principal principal

    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Page<SubjectGroup> subjectsGroups = subjectGroupService.findBySubjectId(subjectId, page, size, sort, dir);
        return new ResponseEntity<>(subjectsGroups, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<SubjectGroup> findSubjectGroupById(
            @PathVariable("groupId") Long groupId,
            Principal principal

    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        SubjectGroup group = subjectGroupService.findById(groupId).orElseThrow(() -> new ObjectNotFoundException(groupId, "SubjectGroup"));
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<SubjectGroup>> findAllSubjectsGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Principal principal

    ) {
        userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Page<SubjectGroup> subjectsGroups = subjectGroupService.findAll(page, size, sort, dir);
        return new ResponseEntity<>(subjectsGroups, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/groups", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SubjectGroup> findAllSubjectsGroups(
            @RequestBody SubjectGroup model,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);

        //check if subject given in SubjectGroup model exists
        subjectService.findById(model.getSubjectId()).orElseThrow(() -> new ObjectNotFoundException(model.getSubjectId(), "Subject"));

        SubjectGroup newGroup = subjectGroupService.createSubjectGroup(model);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<SubjectGroup> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        //Use only when group does not have any members, gradeTypes and grades
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);

        SubjectGroup group = subjectGroupService.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "SubjectGroup"));
        subjectGroupService.deleteSubjectGroup(group);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @RequestMapping(value = "/user/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<SubjectGroup>> getUserSubjectGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        Page<SubjectGroup> groups = subjectGroupService.findUserGroups(user, page, size, sort, dir);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/group/users", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<SubjectGroup> deleteUserFromGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);

        SubjectGroup group = subjectGroupService.findById(groupId).orElseThrow(() -> new ObjectNotFoundException(groupId, "SubjectGroup"));
        if (!group.getGroupTeacherId().equals(user.getUserId())) throw new NoPermissionException(email);

        userService.findUserGroupById(new UserGroupId(userId, groupId))
                .orElseThrow(() -> new NotGroupMemberException(userId));

        subjectGroupService.removeUserFromGroup(userId, groupId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/group/users", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SubjectGroup> addUserToGroup(
            @RequestBody UserGroupId userGroupId,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getRole().equals(Role.TEACHER)) throw new NoPermissionException(email);

        SubjectGroup group = subjectGroupService.findById(userGroupId.getGroupId())
                .orElseThrow(() -> new ObjectNotFoundException(userGroupId.getGroupId(), "SubjectGroup"));
        if (!group.getGroupTeacherId().equals(user.getUserId())) throw new NoPermissionException(email);

        UserGroup userGroup = new UserGroup(userGroupId);
        subjectGroupService.addUserToGroup(userGroup);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
