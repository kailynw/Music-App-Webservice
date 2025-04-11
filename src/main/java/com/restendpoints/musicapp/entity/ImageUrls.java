package com.restendpoints.musicapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUrls {

    private String full;

    private String regular;

    private String small_s3;

    @Override
    public String toString() {
        return "ImageUrls{" +
                ", full='" + full + '\'' +
                ", regular='" + regular + '\'' +
                ", small_s3='" + small_s3 + '\'' +
                '}';
    }
}
