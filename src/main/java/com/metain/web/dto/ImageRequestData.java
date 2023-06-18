package com.metain.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestData {
    private String imageData;
    private float imageWidth;
    private float imageHeight;

    private String certSort;
    private Long certId;

    // Getter, Setter 생략S
}
