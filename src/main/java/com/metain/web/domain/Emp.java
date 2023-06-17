package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
// TEST
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private Long empId;
    private int empSabun;
    private String empName;
    private String empPwd;
    private Date empBirth;
    private String empPhone;
    private String empAddr;
    private String empZipcode;
    private String empDetailAddr;
    private String empEmail;
    private String empDept;
    private String empGrade;
    private String empStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empFirstDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empLastDt;
    private int empVac;

}
