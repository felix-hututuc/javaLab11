package com.fii.springTesting.friends;

import com.fii.springTesting.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @GetMapping()
    public List<Friends> getFriends() {
        return friendsService.findAllFriends();
    }

    @PostMapping()
    public ResponseEntity<String> postFriends(@RequestParam Integer friend1, @RequestParam Integer friend2) {
        friendsService.addFriends(new Friends(friend1, friend2));
        friendsService.addFriends(new Friends(friend2, friend1));
        return new ResponseEntity<>("Friendship relations added.", HttpStatus.OK);
    }

    @GetMapping("/popular")
    public List<User> findMostPopular(@RequestParam Integer count) {
        return friendsService.findMostPopular(count);
    }
}
