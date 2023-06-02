package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacation {
    private Long vacId;
    private String vacType; //일반(반차), 일반(연차), 병가 , 조사(배우자5) ,조사(부모님5),조사(형제1), 예비군
    private Long empId;
    private Date vacRegDate;
    private Date vacStartDate;
    private Date vacEndDate;
    private Long fileId;
    private Long admId; //팀관리자 번호
    private String vacResn;
    private String vacStatus; //승인상태
    private Date vacUpdat;
}
