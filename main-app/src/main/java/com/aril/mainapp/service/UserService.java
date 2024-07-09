package com.aril.mainapp.service;

import com.aril.mainapp.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return user;
    }

    public User getUser(String id) {
        User user = new User();
        user.setId(id);
        user.setName("Aril");
        user.setEmail("D5C6M@example.com");
        user.setAge(25);
        return user;
    }

    public User updateUser(String id, User updatedUser) {
        User user = getUser(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());
        return user;
    }
}
