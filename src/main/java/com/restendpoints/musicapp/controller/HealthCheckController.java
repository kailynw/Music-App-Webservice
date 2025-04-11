package com.restendpoints.musicapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
public class HealthCheckController {

    @RequestMapping(method = GET, value="/healthcheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Yooooo");
    }

}
