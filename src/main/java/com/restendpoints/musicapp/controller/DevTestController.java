package com.restendpoints.musicapp.controller;

import com.restendpoints.musicapp.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
public class DevTestController {

    @Autowired
    S3Service s3Service;

    @RequestMapping(path = "/test", method = GET)
    public ResponseEntity<String> getPresignedUrl(){
        log.info(s3Service.getPresignedUrl("test_track.wav"));
        return ResponseEntity.ok("Success");
    }
}
