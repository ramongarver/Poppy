package com.ramongarver.poppy.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.ROOT_ROUTE)
public class RootController {

    @GetMapping
    public ResponseEntity<String> getActivityById() {
        return new ResponseEntity<>("Good new to see this! :)", HttpStatus.OK);
    }

}
