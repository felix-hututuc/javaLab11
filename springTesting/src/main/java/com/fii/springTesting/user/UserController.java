package com.fii.springTesting.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public String registerUser(@RequestParam String username) {
        User user = new User(username);
        userService.addUser(user);
        return "User " + username + " was registered with the id " + user.getId();
    }

    @GetMapping("/count")
    public long countUsers() {
        return userService.countUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") int id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Integer userId,
                                     @RequestParam String username) {
        userService.updateUser(userId, username);

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer id) {
        userService.deleteUser(id);

        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
