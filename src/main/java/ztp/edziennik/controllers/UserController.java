package ztp.edziennik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztp.edziennik.models.User;
import ztp.edziennik.services.UserService;

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

}
