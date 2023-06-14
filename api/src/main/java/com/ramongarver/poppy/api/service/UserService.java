package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.entity.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);

    boolean doesEmailExist(String email);

}
