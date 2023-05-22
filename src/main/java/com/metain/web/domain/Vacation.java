package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacation {
    private Long vacId;
    private String vacType;
    private String vacTybeSub;
    private Long empId;
    private Date vacRegDate;
    private Date vacStartDate;
    private Date vacEndDate;
    private Long fileId;
    private Long amdId; //팀관리자 번호
    private String vacResn;
    private String vacStatus; //승인상태
    private Date vacUpdat;
}
