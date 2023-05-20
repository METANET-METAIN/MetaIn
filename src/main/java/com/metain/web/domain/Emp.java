package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private Long empId;
    private String empName;
    private String empPwd;
    private String empIdnum;
    private String empPhone;
    private String empAddr;
    private String empEmail;
    private String empDept;
    private String empGrade;
    private String empStatus;
    private Date empFistDt;
    private Date empLastDt;
    private int empVac;

}
