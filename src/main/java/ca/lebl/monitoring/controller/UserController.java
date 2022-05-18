package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsername(@RequestParam("username") String username) {
        // TODO: if user is not found, handle through @ControllerAdvice
        return userService.getUserByUsername(username);
    }
}
