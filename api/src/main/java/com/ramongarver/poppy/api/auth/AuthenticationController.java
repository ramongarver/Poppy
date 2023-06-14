package com.ramongarver.poppy.api.auth;

import com.ramongarver.poppy.api.controller.ControllerConstants;
import com.ramongarver.poppy.api.dto.UserDto;
import com.ramongarver.poppy.api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.AUTH_ROUTE)
public class AuthenticationController {

    private final UserMapper userMapper;

    private final AuthenticationService authService;

    @PostMapping(ControllerConstants.AUTH_REGISTER_ROUTE)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(ControllerConstants.AUTH_AUTHENTICATE_ROUTE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping(ControllerConstants.AUTH_MY_ACCOUNT_ROUTE)
    public ResponseEntity<UserDto> myAccount() {
        return ResponseEntity.ok(userMapper.toDto(authService.getAuthenticatedUser()));
    }

}
