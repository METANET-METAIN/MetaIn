package com.metain.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Getter
@Setter
public class CertInfoDTO {

    private Long empId; //사원번호

    //증명서에 적힐 사원 인적정보
    private String empName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empBirth;
    private String empAddr;
    private String empDetailAddr;
    private String empDept;
    private String empGrade;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empFirstDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date empLastDt;

    private String empStatus;
    
    ///증명서에 적힐 해당 증명서정보
    private String certSort; //증명서종류
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date certRegDate; //발급일자
    private String useOfCert; //사용용도
    private String certFileName; //증명서파일이름
    private Long issueStatus; //발급상태

    private Long empCertId;
    private Long experCertId;
    private Long retireCertId;

    private int adjustedId;



}
