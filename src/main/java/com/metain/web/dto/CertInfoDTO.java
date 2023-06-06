package com.metain.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CertInfoDTO {

    private Long empId;

    //증명서에 적힐 사원 인적정보
    private String empName;
    private String empBirth;
    private String empAddr;
    private String empDetailAddr;
    private String empDept;
    private String empGrade;
    private Date empFirstDt;
    private Date empLastDt;
    private String empStatus;
    
    ///증명서에 적힐 해당 증명서정보
    private Long certSort;
    private Date certRegDate;
    private String useOfCert;
    

}
