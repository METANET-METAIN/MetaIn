package com.metain.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Data
@Setter

public class VacationListDTO {
    private String empName;
    private String empDept;
    private String empGrade;
    private String vacType;
    private Date vacStartDate;
    private Date vacEndDate;
    private Date vacRegDate;
}
