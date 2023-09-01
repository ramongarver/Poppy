package com.ramongarver.poppy.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.ROOT_ROUTE)
@Tag(name = "Root", description = "Health check and basic information")
public class RootController {

    @GetMapping
    @Operation(summary = "Get a basic message",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("Good new to see this, I'm working! :)", HttpStatus.OK);
    }

}
