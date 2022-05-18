package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsername(@RequestParam("username") String username) {
        // TODO: if user is not found, handle through @ControllerAdvice
        return userService.getUserByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestParam("accessToken") String accessToken
    ) {
        return userService.createUser(username, email, accessToken);
    }

}
