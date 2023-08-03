package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.user.UserCreateDto;
import com.ramongarver.poppy.api.dto.user.UserUpdateDto;
import com.ramongarver.poppy.api.entity.User;

import java.util.List;

public interface UserService {

    User createUser(UserCreateDto userCreateDto);

    User getUserById(Long userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Long userId, UserUpdateDto userUpdateDto);

    void deleteUser(Long userId);

    boolean doesEmailExist(String email);

}
