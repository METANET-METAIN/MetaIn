package com.metain.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VacationWithoutFileDTO {
    private Long vacId;
    private Long empId;
    private String vacType;
    private Date vacRegDate;
    private Date vacStartDate;
    private Date vacEndDate;
    private Long admId;
    private String vacResn;
    private String vacStatus;
    private Date vacUpdat;
    private String fileName;
}
