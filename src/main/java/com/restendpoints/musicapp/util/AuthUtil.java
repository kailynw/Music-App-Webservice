package com.restendpoints.musicapp.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Slf4j
public class AuthUtil {

    public static String extractBearerTokenFromHttpHeaders(HttpHeaders httpHeaders){
        String rawBearerToken = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        boolean isBearerToken = StringUtils.isNotEmpty(rawBearerToken) && rawBearerToken.startsWith("Bearer");
        if(isBearerToken){
            return rawBearerToken.replace("Bearer ", "");
        }else{
            return null;
        }
    }

}
