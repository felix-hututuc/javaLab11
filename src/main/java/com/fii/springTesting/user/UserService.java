package com.fii.springTesting.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("The user with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "The user with id " + id + " does not exist"
        ));
    }

    @Transactional
    public void updateUser(Integer userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "The user with id " + userId + " does not exist"
                ));

        if (!user.getName().equals(username)) {
            user.setName(username);
        }
    }
}
