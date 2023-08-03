package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.user.UserCreateDto;
import com.ramongarver.poppy.api.dto.user.UserUpdateDto;
import com.ramongarver.poppy.api.entity.User;
import com.ramongarver.poppy.api.exception.EmailExistsException;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.UserMapper;
import com.ramongarver.poppy.api.repository.UserRepository;
import com.ramongarver.poppy.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        if (doesEmailExist(userCreateDto.getEmail())) {
            throw new EmailExistsException(userCreateDto.getEmail());
        }

        final User user = userMapper.fromCreateDto(userCreateDto);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), "id", userId));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), "email", email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long userId, UserUpdateDto userUpdateDto) {
        final User existingUser = getUserById(userId);
        userMapper.fromUpdateDto(existingUser, userUpdateDto);
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean doesEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
