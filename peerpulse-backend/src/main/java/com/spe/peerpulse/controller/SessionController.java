package com.spe.peerpulse.controller;

import com.spe.peerpulse.model.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {
    @GetMapping("/alive")
    public ResponseEntity<GenericResponse> sessionAlive() {
        return ResponseEntity.ok(new GenericResponse("The session is alive"));
    }
}
