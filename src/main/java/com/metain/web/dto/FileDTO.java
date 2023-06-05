package com.metain.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class FileDTO {
    private Long fileId;
    private String fileName;
    private Long empId;
}
