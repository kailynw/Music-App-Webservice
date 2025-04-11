package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Slf4j
@Service
public class S3Service {
    public String getPresignedUrl(String objectKey){
        if(StringUtils.isNotEmpty(objectKey)) {
            String bucket = Constants.MUSIC_S3_BUCKET;

            S3Presigner presigner = S3Presigner.builder()
                    .region(Region.US_EAST_1)
                    .build();

            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(120))
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(presignRequest);

            return presignedGetObjectRequest.url().toString();
        }else{
            log.warn("Invalid value for object key, presigned url not retrieved | object key: {}", objectKey);
            return null;
        }
    }
}
