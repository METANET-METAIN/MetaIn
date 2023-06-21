package com.metain.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Data
@Setter
@AllArgsConstructor
public class VacationListDTO {
    private Long vacId;
    private String empName;
    private String empDept;
    private String empGrade;
    private String vacType;
    private Date vacStartDate;
    private Date vacEndDate;
    private Date vacRegDate;
    private String vacStatus;

    public VacationListDTO(long l, String 김사원, String s, String employee, String s1, Date date, Date date1, Date date2, String 요청취소) {
    }
}
