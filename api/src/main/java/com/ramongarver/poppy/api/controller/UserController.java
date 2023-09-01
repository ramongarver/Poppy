package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.user.UserCreateDto;
import com.ramongarver.poppy.api.dto.user.UserReadDto;
import com.ramongarver.poppy.api.dto.user.UserUpdateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReadDto;
import com.ramongarver.poppy.api.entity.User;
import com.ramongarver.poppy.api.mapper.UserMapper;
import com.ramongarver.poppy.api.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.USERS_ROUTE)
@Hidden
@Tag(name = "Users", description = "User management resource")
@SecurityRequirement(name = "JWT Bearer Authentication")
public class UserController {

    private final UserMapper userMapper;

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{userId}")
    @Operation(summary = "Get an user by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VolunteerReadDto.class)))
            }
    )
    public ResponseEntity<UserReadDto> getUserById(@PathVariable("userId") Long userId) {
        final User user = userService.getUserById(userId);
        return new ResponseEntity<>(userMapper.toReadDto(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<List<UserReadDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(userMapper.toListReadDto(users), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create an user",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = UserReadDto.class)))
            }
    )
    public ResponseEntity<UserReadDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        final User savedUser = userService.createUser(userCreateDto);
        return new ResponseEntity<>(userMapper.toReadDto(savedUser), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{userId}")
    @Operation(summary = "Update an user by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserReadDto.class)))
            }
    )
    public ResponseEntity<UserReadDto> updateUser(@PathVariable("userId") Long userId,
                                                  @RequestBody UserUpdateDto userUpdateDto) {
        final User updatedUser = userService.updateUser(userId, userUpdateDto);
        return new ResponseEntity<>(userMapper.toReadDto(updatedUser), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{userId}")
    @Operation(summary = "Delete an user by id",
            responses = {
                    @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.NO_CONTENT);
    }

}
