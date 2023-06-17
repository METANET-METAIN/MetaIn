package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// TEST
@Component
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
    private String empGrade;
    private String empStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empFirstDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empLastDt;
    private int empVac;
    private String RoleName;
    private Long roleId;
    private List<GrantedAuthority> authorities;
    private String empProfile;
}
