package b_yousefi.bookshop.usersws.ui.controllers;

import b_yousefi.bookshop.usersws.data.User;
import b_yousefi.bookshop.usersws.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Created by: b.yousefi
 * Date: 5/11/2020
 */
@RestController
public class JwtAuthenticationController {
    private Environment env;
    private UserService userService;

    public JwtAuthenticationController(Environment env, UserService userService) {
        this.userService = userService;
        this.env = env;
    }

    @RequestMapping(value = "/users/status/check", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> status() {
        return ResponseEntity.status(HttpStatus.OK).body("Working on Port " + env.getProperty("local.server.port")
                + " with token =" + env.getProperty("token.secret"));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> createUser(@Valid @RequestBody User userDetails) {
        userDetails.setRole("ROLE_USER");
        User createdUser = userService.createUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
