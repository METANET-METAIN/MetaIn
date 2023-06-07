package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonCert {
    private Long empCertId; //재직증명서번호
    private Long empId; //사원번호
    private Date certRegDate; //발급일자
    private String useOfCert; //사용용도
    private String certPath; //증명서경로
    private String certFileName; //증명서파일이름
    private Long issueStatus; //발급상태

    private String certSort; //증명서 종류

}