package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//등록일시 추가 필요
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    private Long issueId; //발급내역번호
    private Long empId; //사원번호
    private Long empCertId; //재직증명서번호
    private Long experCertId; //경력증명서번호
    private Long retireCertId; //퇴직증명서번호
    private Long certSort; //증명서종류

}
