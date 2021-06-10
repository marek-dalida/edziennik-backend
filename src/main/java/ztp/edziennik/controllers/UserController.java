package ztp.edziennik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.Member;
import ztp.edziennik.models.User;
import ztp.edziennik.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);

        user.setPassword(null);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> findUser(Principal principal) {
        String email = principal.getName();
        HttpHeaders headers = new HttpHeaders();

        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setPassword("");

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/students", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Member>> findStudents(
            @RequestParam(defaultValue = "") String search,
            Principal principal) {
        HttpHeaders headers = new HttpHeaders();

        List<Member> students = userService.findStudents(search);

        return new ResponseEntity<>(students, headers, HttpStatus.OK);
    }

}
