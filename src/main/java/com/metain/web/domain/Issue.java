package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//등록일시 추가 필요
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    private Long issueId;
    private Long empId;
    private Long empCertId;
    private Long experCertId;
    private Long retireCertId;
}
