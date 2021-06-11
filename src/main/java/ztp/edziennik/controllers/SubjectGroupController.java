package ztp.edziennik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.SubjectGroup;
import ztp.edziennik.models.User;
import ztp.edziennik.models.UserGroup;
import ztp.edziennik.models.UserGroupId;
import ztp.edziennik.services.SubjectGroupService;
import ztp.edziennik.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubjectGroupController {

    SubjectGroupService subjectGroupService;
    UserService userService;

    @Autowired
    public SubjectGroupController(SubjectGroupService subjectGroupService, UserService userService) {
        this.subjectGroupService = subjectGroupService;
        this.userService = userService;
    }

    @RequestMapping(value = "/subject/{subjectId}/groups", method = RequestMethod.GET, produces = "application/json")
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
    public ResponseEntity<SubjectGroup> deleteById(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders headers = new HttpHeaders();
        subjectGroupService.deleteSubjectGroup(id);
        //TODO: delete all other dependencies
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/groups", method = RequestMethod.GET, produces = "application/json" )
    public ResponseEntity<Page<SubjectGroup>> getUserSubjectGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Principal principal
    ){
        String email = principal.getName();
        HttpHeaders headers = new HttpHeaders();

        User user = userService.findByEmail(email).orElse(null);
        if( user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Page<SubjectGroup> groups = subjectGroupService.findUserGroups(user, page, size, sort, dir);
        return new ResponseEntity<>(groups, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/group/users", method = RequestMethod.DELETE, produces = "application/json" )
    public ResponseEntity<SubjectGroup> deleteUserFromGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            Principal principal
    ){
        HttpHeaders headers = new HttpHeaders();
        String email = principal.getName();
        User teacher = userService.findByEmail(email).orElse(null);
        if( teacher == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        SubjectGroup subjectGroup = subjectGroupService.findById(groupId).orElse(null);
        if(subjectGroup.getGroupTeacherId().equals(teacher.getUserId())){
            subjectGroupService.removeUserFromGroup(userId, groupId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/group/users", method = RequestMethod.POST, produces = "application/json" )
    public ResponseEntity<SubjectGroup> addUserToGroup(
            @RequestBody UserGroupId userGroupId,
            Principal principal
    ){
        HttpHeaders headers = new HttpHeaders();
        String email = principal.getName();
        User teacher = userService.findByEmail(email).orElse(null);
        if( teacher == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        SubjectGroup subjectGroup = subjectGroupService.findById(userGroupId.getGroupId()).orElse(null);
        if(subjectGroup.getGroupTeacherId().equals(teacher.getUserId())){
            UserGroup userGroup = new UserGroup(userGroupId);
            subjectGroupService.addUserToGroup(userGroup);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
