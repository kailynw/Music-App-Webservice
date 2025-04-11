package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.constants.Constants;
import com.restendpoints.musicapp.dto.image.ImageResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

@Service
public class ImageRetrievalService {

    @Value("${UNSPLASH_API_HOST}")
    private String unsplashAPIHost;

    @Value("${UNSPLASH_CLIENT_ID}")
    private String unsplashClientId;

    RestTemplate restTemplate = new RestTemplate();

    Logger logger = LoggerFactory.getLogger(getClass());

    public String retrieveRandomImageUrl(){
        String url = unsplashAPIHost + Constants.UNSPLASH_RANDOM_IMAGE_ENDPOINT +  "?client_id=" + unsplashClientId;
        ResponseEntity<ImageResponseDTO> response = restTemplate.exchange(url, GET, null, ImageResponseDTO.class);
        logger.info("Random Image payload: "+ Objects.requireNonNull(response.getBody()));
        return response.getBody().getUrls().getFull();
    }
}
