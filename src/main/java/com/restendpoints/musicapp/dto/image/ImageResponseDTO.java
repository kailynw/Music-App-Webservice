package com.restendpoints.musicapp.dto.image;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.restendpoints.musicapp.entity.ImageUrls;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    private ImageUrls urls;

    @Override
    public String toString() {
        return "ImageResponseDTO{" +
                ", description='" + description + '\'' +
                ", urls=" + urls +
                '}';
    }
}
