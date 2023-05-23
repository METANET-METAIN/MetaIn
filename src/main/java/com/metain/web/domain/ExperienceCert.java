package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceCert {
    private Long experCertId;
    private Long empId;
    private Date certRegDate;
    private String submit;
}
