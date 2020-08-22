package b_yousefi.bookshop.usersws.ui.controllers;

import b_yousefi.bookshop.usersws.data.User;
import b_yousefi.bookshop.usersws.data.UserRepository;
import b_yousefi.bookshop.usersws.service.UserService;
import b_yousefi.bookshop.usersws.ui.UserModelAssembler;
import b_yousefi.bookshop.usersws.ui.model.UserModel;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by: b.yousefi
 * Date: 8/9/2020
 */

@RepositoryRestController
public class UserController {

    private UserService userDetailsService;
    private UserModelAssembler userModelAssembler;
    private UserRepository userRepository;

    public UserController(UserService userDetailsService,
                          UserModelAssembler userModelAssembler,
                          UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userModelAssembler = userModelAssembler;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<EntityModel<?>> updateUser(@PathVariable String userId, @RequestBody @Valid User usr
            , PersistentEntityResourceAssembler assembler) {
        Optional<User> opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User user = opUser.get();
            user.setPassword(usr.getPassword());
            user.setEmail(usr.getEmail());
            user.setPhoneNumber(usr.getPhoneNumber());
            user.setFirstName(usr.getFirstName());
            user.setLastName(usr.getLastName());
            return new ResponseEntity<>(assembler.toFullResource(userDetailsService.updateUser(opUser.get())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserModel> getUserById(@PathVariable String userId) {
        return userRepository.findById(userId)
                .map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
