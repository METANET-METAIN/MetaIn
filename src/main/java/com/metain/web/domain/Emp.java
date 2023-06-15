package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
// TEST
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp  {
    private Long empId;
    private String empSabun;
    private String empName;
    private String empPwd;
    private Date empBirth;
    private String empPhone;
    private String empAddr;
    private String empZipcode;
    private String empDetailAddr;
    private String empEmail;
    private String empDept;
    private Role empGrade;
    private String empStatus;
    private Date empFirstDt;
    private Date empLastDt;
    private int empVac;
    private String empProfile;
}
