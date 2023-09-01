package com.ramongarver.poppy.api.auth;

import com.ramongarver.poppy.api.controller.ControllerConstants;
import com.ramongarver.poppy.api.dto.user.UserReadDto;
import com.ramongarver.poppy.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.AUTH_ROUTE)
@Tag(name = "Authentication", description = "Authentication of application volunteers")
public class AuthenticationController {

    private final UserMapper userMapper;

    private final AuthenticationService authService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ControllerConstants.AUTH_REGISTER_ROUTE)
    @GetMapping("{userId}")
    @Operation(summary = "Register an user in the application",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
            },
            security = @SecurityRequirement(name = "JWT Bearer Authentication"),
            hidden = true
    )
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(ControllerConstants.AUTH_AUTHENTICATE_ROUTE)
    @Operation(summary = "Login an user in the application",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
            }
    )
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ControllerConstants.AUTH_MY_ACCOUNT_ROUTE)
    @Operation(summary = "Get my account information",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserReadDto.class)))
            },
            security = @SecurityRequirement(name = "JWT Bearer Authentication")
    )
    public ResponseEntity<UserReadDto> myAccount() {
        return ResponseEntity.ok(userMapper.toReadDto(authService.getAuthenticatedUser()));
    }

}
